package pl.jarek.mysqlrest.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.jarek.mysqlrest.password.InvalidPasswordException;
import pl.jarek.mysqlrest.password.PasswordRepository;
import pl.jarek.mysqlrest.password.PasswordValidator;

import java.util.Optional;

public class UserServiceTest {

    private static final int USER_ID = 1;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordValidator passwordValidator;

    @Mock
    private PasswordRepository passwordRepository;

    private UserConverter userConverter = new UserConverter();

    private UserService userService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, userConverter, passwordValidator, passwordRepository);
    }

    @Test(expected = DuplicateEmailException.class)
    public void shouldNotCreateUserWithExistingEmail() {

    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldNotCreateUserWithWrongPassword() {

    }

    @Test
    public void shouldCreateUser() {

    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotFindNonExistingUser() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        userService.findById(USER_ID);
    }

    @Test
    public void shouldFindUser() {

    }

    @Test
    public void shouldFindAllUsers() {

    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotChangePasswordForNonExistingUser() {

    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldNotChangePasswordForWrongOldPassword() {

    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldNotChangePasswordForWrongNewPassword() {

    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldNotChangePasswordWithHistoricalPassword() {

    }

    @Test
    public void shouldChangePassword() {

    }
}
