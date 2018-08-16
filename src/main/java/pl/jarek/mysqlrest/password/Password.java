package pl.jarek.mysqlrest.password;

import lombok.Getter;
import lombok.Setter;
import pl.jarek.mysqlrest.user.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String value;

    @OneToOne
    private User user;
}
