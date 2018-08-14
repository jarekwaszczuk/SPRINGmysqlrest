package pl.jarek.mysqlrest;

import javax.persistence.*;

@Entity
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String value;

    @OneToOne
    private User user;
}
