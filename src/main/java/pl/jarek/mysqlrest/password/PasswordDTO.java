package pl.jarek.mysqlrest.password;

import lombok.Data;

@Data
public class PasswordDTO {

    String oldPassword;
    String newPassword;
}
