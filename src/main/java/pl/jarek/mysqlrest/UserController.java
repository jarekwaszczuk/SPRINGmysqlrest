package pl.jarek.mysqlrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired //opcjonalny
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findByID(@PathVariable Integer id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Validated UserDTO userDTO) {
        userDTO = userService.createUser(userDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDTO.getId())
                .toUri();

        return ResponseEntity.created(location).body(userDTO);
    }

    @PostMapping("/{id}/passwords")
    public ResponseEntity<UserDTO> changePassword(@PathVariable Integer id, @RequestBody PasswordDTO passwordDTO) {
        return ResponseEntity.accepted().body(userService.resetPassword(id, passwordDTO));
    }

    @PutMapping("/{id}/activate/{activationKey}")
    public ResponseEntity<UserDTO> activation(@PathVariable Integer id, @PathVariable String activationKey) {
        return ResponseEntity.accepted().body(userService.activate(id, activationKey));
    }
}
