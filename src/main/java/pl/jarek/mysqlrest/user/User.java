package pl.jarek.mysqlrest.user;

import lombok.Data;
import pl.jarek.mysqlrest.password.Password;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean active;
    private String activationKey;

    @OneToMany(mappedBy = "user")
    private List<Password> oldPasswords = new ArrayList<>();

}
