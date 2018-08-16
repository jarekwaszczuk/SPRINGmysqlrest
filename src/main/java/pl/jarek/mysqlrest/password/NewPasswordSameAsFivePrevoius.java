package pl.jarek.mysqlrest.password;

public class NewPasswordSameAsFivePrevoius extends RuntimeException {

    public NewPasswordSameAsFivePrevoius(Integer id) {
        super("Password for user id: " + id + " is same that 5 prevoius passwords");
    }
}
