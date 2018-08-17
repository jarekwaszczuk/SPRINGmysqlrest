package pl.jarek.mysqlrest.password;

import org.junit.Assert;
import org.junit.Test;

public class PasswordValidatorTest {

    private final String correctPassword = "Zew!1ane";
    private final String wrongPassword = "niepoprawne";

    private PasswordValidator passwordValidator = new PasswordValidator();

    @Test
    public void ifWrongPasswordReturnFalse(){

        boolean result = passwordValidator.valid(wrongPassword);

        Assert.assertFalse(result);
    }

    @Test
    public void ifCorrectPasswordReturnTrue(){

        boolean result = passwordValidator.valid(correctPassword);

        Assert.assertTrue(result);
    }
}
