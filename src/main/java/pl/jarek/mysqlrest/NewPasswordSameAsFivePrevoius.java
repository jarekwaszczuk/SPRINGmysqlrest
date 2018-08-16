package pl.jarek.mysqlrest;

public class NewPasswordSameAsFivePrevoius extends RuntimeException {

    public NewPasswordSameAsFivePrevoius(Integer id) {
        super("Password for user id: " + id + " is same that 5 prevoius passwords");
    }
}
