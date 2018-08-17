package pl.jarek.mysqlrest.password;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator {

    public boolean valid(String password){
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
