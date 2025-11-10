package com.Project.User;

import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private ModelMapper mapper;
    private String cartConnection = "http://service-cart:8082/";

    public UserResponse getUserByName(String name) {
        Optional<User> currentUser = Optional.ofNullable(userRepository.findByName(name));
        UserResponse userResponse = (UserResponse)mapper.map(currentUser, UserResponse.class);
        return userResponse;
    }

    public ResponseEntity<?> addUser(User addUser) {
        String webConnection = cartConnection + "makeCart";
        UserResponse taken = getUserByName(addUser.getName());
        Map<String, String> errorMassage = Map.of("Status", "UserAlreadyTaken");
        if (taken == null) {
            userRepository.save(addUser);
            Map<String, Object> requestInfo = Map.of("ownerId", addUser.getId().longValue());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity(requestInfo, headers);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(webConnection, request, Map.class, new Object[0]);
            Map<String, Object> responseInfo = (Map)responseEntity.getBody();
            addUser.setCartId(((Number)responseInfo.get("cartId")).longValue());
            userRepository.save(addUser);
            return ResponseEntity.ok((Map)responseEntity.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMassage);
        }
    }

    public ResponseEntity<?> userLogin(User loginUser) {
        UserResponse Available = getUserByName(loginUser.getName());
        String Status = "";
        if (Available != null) {
            if (loginUser.getPassword().equals(Available.getPassword())) {
                Status = "Login Success";
            }

            Status = Status.equals("") ? "Wrong Password" : Status;
        }

        Status = Status.equals("") ? "No Username founded" : Status;
        Map<String, Object> response = Map.of("Status", Status);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public ResponseEntity<?> userCartRequest(Long id) {
        String var10000 = cartConnection;
        String webConnection = var10000 + "showCart" + String.format("/%d", id);
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(webConnection, Map.class, new Object[0]);
        Map<String, Object> responseCart = (Map)responseEntity.getBody();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseCart);
    }
}
