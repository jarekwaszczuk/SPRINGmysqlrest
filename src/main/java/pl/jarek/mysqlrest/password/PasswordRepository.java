package pl.jarek.mysqlrest.password;

import org.springframework.data.repository.CrudRepository;
import pl.jarek.mysqlrest.user.User;

import java.util.List;

public interface PasswordRepository extends CrudRepository<Password, Integer> {

    List<Password> findByUserOrderByIdAsc(User user);

}
