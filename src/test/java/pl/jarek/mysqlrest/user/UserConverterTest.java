package pl.jarek.mysqlrest.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class UserConverterTest {

    private static final int USER_ID = 1;
    private static final String USER_FIRST_NAME = "firstName";
    private static final String USER_LAST_NAME = "lastName";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final User USER_WITH_ID = new User();
    private static final UserDTO USER_DTO = new UserDTO();

    private UserConverter userConverter = new UserConverter();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        USER_DTO.setFirstName(USER_FIRST_NAME);
        USER_DTO.setLastName(USER_LAST_NAME);
        USER_DTO.setEmail(USER_EMAIL);
        USER_DTO.setPassword(USER_PASSWORD);

        USER_WITH_ID.setId(USER_ID);
        USER_WITH_ID.setFirstName(USER_FIRST_NAME);
        USER_WITH_ID.setLastName(USER_LAST_NAME);
        USER_WITH_ID.setEmail(USER_EMAIL);
        USER_WITH_ID.setPassword(USER_PASSWORD);
    }

    @Test
    public void shouldConvertEntityToDTO() {

        UserDTO userDTO = userConverter.toDTO(USER_WITH_ID);

        Assert.assertTrue(USER_WITH_ID.getId().equals(userDTO.getId()));
        Assert.assertTrue(USER_WITH_ID.getFirstName().equals(userDTO.getFirstName()));

    }

    @Test
    public void shouldConvertDTOToEntity() {

        User user = userConverter.toEntity(USER_DTO);

        Assert.assertTrue(USER_DTO.getFirstName().equals(user.getFirstName()));

    }

}