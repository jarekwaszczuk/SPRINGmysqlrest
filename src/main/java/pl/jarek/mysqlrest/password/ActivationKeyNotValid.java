package pl.jarek.mysqlrest.password;

public class ActivationKeyNotValid extends RuntimeException {
    public ActivationKeyNotValid(Integer id) {
        super("User id: " + id + " activation key is invalid");
    }
}
