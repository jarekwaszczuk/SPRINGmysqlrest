package pl.jarek.mysqlrest.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.jarek.mysqlrest.password.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {

    private static final int USER_ID = 1;
    private static final String USER_FIRST_NAME = "firstName";
    private static final String USER_LAST_NAME = "lastName";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final User USER = new User();
    private static final User USER_WITH_ID = new User();
    private static final UserDTO USER_DTO = new UserDTO();
    private static List<Password> passList = new ArrayList<>();

//  TODO poprawne przypisywanie wartości początkowych do STATIC
//    static {
//        USER = new User();
//        USER.setId(USER_ID);
//        USER.setFirstName("firstName");
//        USER.setLastName("lastName");
//        USER.setEmail("email");
//        USER.setPassword("password");
//
//        USER_DTO = new UserDTO();
//        USER_DTO.setFirstName("firstName");
//        USER_DTO.setLastName("lastName");
//        USER_DTO.setEmail("email");
//        USER_DTO.setPassword("password");
//    }
//
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordValidator passwordValidator;

    @Mock
    private PasswordRepository passwordRepository;

    @Mock
    private UserConverter userConverter;

    private UserService userService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository, userConverter, passwordValidator, passwordRepository);

        USER_DTO.setFirstName(USER_FIRST_NAME);
        USER_DTO.setLastName(USER_LAST_NAME);
        USER_DTO.setEmail(USER_EMAIL);
        USER_DTO.setPassword(USER_PASSWORD);

        USER.setFirstName(USER_FIRST_NAME);
        USER.setLastName(USER_LAST_NAME);
        USER.setEmail(USER_EMAIL);
        USER.setPassword(USER_PASSWORD);

        Password pass1 = new Password();
        pass1.setUser(USER);
        pass1.setValue("newPassword");
        passList.add(pass1);
        USER.setOldPasswords(passList);

        USER_WITH_ID.setId(USER_ID);
        USER_WITH_ID.setFirstName(USER_FIRST_NAME);
        USER_WITH_ID.setLastName(USER_LAST_NAME);
        USER_WITH_ID.setEmail(USER_EMAIL);
        USER_WITH_ID.setPassword(USER_PASSWORD);
    }

    @Test(expected = DuplicateEmailException.class)
    public void shouldNotCreateUserWithExistingEmail() {
        USER_DTO.setEmail(USER_EMAIL);
        Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(USER));

        userService.createUser(USER_DTO);
    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldNotCreateUserWithWrongPassword() {
        Mockito.when(userRepository.save(USER)).thenReturn(USER);

        userService.createUser(USER_DTO);
    }

    @Test
    public void shouldCreateUser() {
        Mockito.when(userRepository.save(USER)).thenReturn(USER_WITH_ID);
        Mockito.when(passwordValidator.valid(USER_DTO.getPassword())).thenReturn(true);

        userService.createUser(USER_DTO);

        Assert.assertTrue(USER_WITH_ID.getFirstName().equals(USER_DTO.getFirstName()));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotFindNonExistingUser() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        userService.findById(USER_ID);
    }

    @Test
    public void shouldFindUser() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        Mockito.when(userConverter.toDTO(USER)).thenReturn(USER_DTO);

        UserDTO userDTO = userService.findById(USER_ID);

        Assert.assertTrue(userDTO.equals(userConverter.toDTO(USER)));
    }

    @Test
    public void shouldFindAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(USER_WITH_ID);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userConverter.toDTO(USER_WITH_ID)).thenReturn(USER_DTO);

        List<UserDTO> listUsers = userService.findAll();

        Assert.assertTrue(listUsers.size()==1);
        Assert.assertTrue(users.get(0).getFirstName().equals(listUsers.get(0).getFirstName()));
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotChangePasswordForNonExistingUser() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        userService.changePassword(USER_ID, USER_PASSWORD, USER_PASSWORD);
    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldNotChangePasswordForWrongOldPassword() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        USER.setPassword(USER_PASSWORD);
        Mockito.when(passwordValidator.valid("oldPassword")).thenReturn(false);
        Mockito.when(passwordValidator.valid("newPassword")).thenReturn(true);

        userService.changePassword(USER_ID, "oldPassword", "newPassword");
    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldNotChangePasswordForWrongNewPassword() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        USER.setPassword(USER_PASSWORD);
        //    Mockito.when(passwordValidator.valid("oldPassword")).thenReturn(true);
        Mockito.when(passwordValidator.valid("newPassword")).thenReturn(false);

        userService.changePassword(USER_ID, USER_PASSWORD, "newPassword");
    }

    @Test(expected = InvalidPasswordException.class)
    public void shouldNotChangePasswordWithHistoricalPassword() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        Mockito.when(passwordRepository.findByUserOrderByIdAsc(USER)).thenReturn(passList);
        Mockito.when(passwordValidator.valid("newPassword")).thenReturn(true);

        userService.changePassword(USER_ID, USER_PASSWORD, "newPassword");
    }

    @Test
    public void shouldChangePassword() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(USER));
        Mockito.when(passwordValidator.valid("newPassword")).thenReturn(true);
        USER.setPassword(USER_PASSWORD);

        userService.changePassword(USER_ID, USER_PASSWORD, "newPassword");

        Assert.assertTrue(USER.getPassword().equals("newPassword"));
    }

    @Test
    public void shouldChangePasswordAndDeleteOldestPassword() {
        //TODO zrobić test, gdy jest 5 historycznych haseł i jedno jest usuwane
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldNotActivateNonExistingUser() {
        //TODO uzupełnić test
    }

    @Test(expected = ActivationKeyNotValid.class)
    public void shouldNotActivateWithWrongActivationKey() {
        //TODO uzupełnić test
    }

    @Test
    public void shouldActivateUser() {
        //TODO uzupełnić test
    }
}
