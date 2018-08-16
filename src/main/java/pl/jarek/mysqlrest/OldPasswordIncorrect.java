package pl.jarek.mysqlrest;

public class OldPasswordIncorrect extends RuntimeException {

    public OldPasswordIncorrect(Integer id) {
        super("User id: " + id + " old password incorrect");
    }
}
