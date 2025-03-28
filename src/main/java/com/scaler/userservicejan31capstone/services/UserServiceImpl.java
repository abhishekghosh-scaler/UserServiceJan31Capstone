package com.scaler.userservicejan31capstone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.userservicejan31capstone.dtos.SendEmailDto;
import com.scaler.userservicejan31capstone.models.Token;
import com.scaler.userservicejan31capstone.models.User;
import com.scaler.userservicejan31capstone.repositories.TokenRepository;
import com.scaler.userservicejan31capstone.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService
{

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    TokenRepository tokenRepository;
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bcryptPasswordEncoder,
                           TokenRepository tokenRepository,
                           KafkaTemplate<String, String> kafkaTemplate,
                           ObjectMapper objectMapper)
    {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bcryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public User signup(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setFrom("ghosh.abhishek18459@gmail.com");
        sendEmailDto.setSubject("User Registration");
        sendEmailDto.setBody("Hello, " + name + "!");
        sendEmailDto.setTo(email);

        String sendEmailDtoString;

        try {
            sendEmailDtoString = objectMapper.writeValueAsString(sendEmailDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send("sendEmail", sendEmailDtoString);

        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
        {
            //throw exception or redirect to login or both
            return null;
        }

        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
        {
            return null;
        }

        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 30);
        Date date = calendar.getTime();

        token.setExpiryAt(date);

        return tokenRepository.save(token);
    }

    @Override
    public void logout(String tokenValue) {

    }

    @Override
    public User validateToken(String tokenValue) {
        /*
        * 1. Should exist in DB
        * 2. Should not be deleted
        * 3. Should not have expired
        * */

        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue,
                false, new Date());

        if(optionalToken.isEmpty())
        {
            return null;
        }

        return optionalToken.get().getUser();
    }
}
