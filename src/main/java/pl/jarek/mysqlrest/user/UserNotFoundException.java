package pl.jarek.mysqlrest.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer id) {
        super("User id: " + id + " not exist");
    }
}
