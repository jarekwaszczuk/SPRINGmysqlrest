package pl.jarek.mysqlrest;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordDTO {

    @NotEmpty
    String oldPassword;

    @NotEmpty
    String newPassword;
}
