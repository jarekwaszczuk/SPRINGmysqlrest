package pl.jarek.mysqlrest.password;

import lombok.Data;
import lombok.Getter;
import pl.jarek.mysqlrest.user.User;

import javax.persistence.*;

@Entity
@Data
@Getter
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String value;

    @OneToOne
    private User user;
}
