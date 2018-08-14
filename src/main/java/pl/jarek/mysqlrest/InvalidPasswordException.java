package pl.jarek.mysqlrest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password too week")
public class InvalidPasswordException extends RuntimeException {

//    public InvalidPasswordException() {
//        super("Wiadomość błędu");
//    }
}
