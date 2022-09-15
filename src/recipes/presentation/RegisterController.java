package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.business.User;
import recipes.business.UserService;

@RestController
@RequestMapping("/api/")
public class RegisterController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public void register(@Validated @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!userService.save(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists in our system!");
        }
    }
}
