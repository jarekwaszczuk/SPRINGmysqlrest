package pl.jarek.mysqlrest.password;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public boolean valid(String password){
        //TODO dodać logikę sprawdzania hasła
        //TODO małe i duże litery, cyfry i znaki spejalne
        return true;
    }
}
