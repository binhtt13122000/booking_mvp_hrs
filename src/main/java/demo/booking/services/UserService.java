package demo.booking.services;

import demo.booking.entities.User;
import demo.booking.exception.BaseException;
import demo.booking.projector.dto.UserDto;
import demo.booking.projector.response.AuthResponse;

public interface UserService {
    AuthResponse login(String email, String password) throws BaseException;

    UserDto register(String email, String password) throws BaseException;

    User findByEmail(String email) throws BaseException;
}
