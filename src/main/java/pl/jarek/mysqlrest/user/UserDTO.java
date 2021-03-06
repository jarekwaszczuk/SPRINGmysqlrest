package pl.jarek.mysqlrest.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {

    private Integer id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
