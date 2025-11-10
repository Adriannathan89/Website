package com.Project.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping({"/createUser"})
    public ResponseEntity<?> createUser(@RequestBody User addUser) {
        return userService.addUser(addUser);
    }

    @GetMapping({"/showCart/{userId}"})
    public ResponseEntity<?> showUserCart(@PathVariable Long userId) {
        return userService.userCartRequest(userId);
    }

    @PostMapping({"/userLogin"})
    public ResponseEntity<?> userLogin(@RequestBody User loginUser) {
        return userService.userLogin(loginUser);
    }
}
