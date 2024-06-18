package demo.booking.services.impl;

import demo.booking.entities.User;
import demo.booking.exception.BaseException;
import demo.booking.exception.EntityNotFoundException;
import demo.booking.exception.WrongPasswordException;
import demo.booking.projector.dto.UserDto;
import demo.booking.projector.response.AuthResponse;
import demo.booking.repositories.UserRepository;
import demo.booking.services.JWTService;
import demo.booking.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;

    @Override
    @Transactional
    public AuthResponse login(String email, String password) throws BaseException {
        User user = findByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("Password is wrong!");
            throw new WrongPasswordException();
        }

        log.error("Email {} logins successfully!", email);
        return AuthResponse.builder().token(jwtService.generateToken(email)).build();
    }

    @Override
    public UserDto register(String email, String password) throws BaseException {
        User user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        user = userRepository.save(user);
        log.error("Register with email = {} successfully!", email);
        return UserDto.fromDomain(user);

    }

    @Override
    public User findByEmail(String email) throws BaseException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("Email {} is not found!", email);
            throw new EntityNotFoundException(String.format("Email %s is not found!", email));
        }

        return user;
    }
}
