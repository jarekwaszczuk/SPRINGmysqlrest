package pl.jarek.mysqlrest.password;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordDTO {

    String oldPassword;
    String newPassword;
}
