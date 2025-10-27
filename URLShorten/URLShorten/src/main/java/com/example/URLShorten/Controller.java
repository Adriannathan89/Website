package com.example.URLShorten;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class Controller {
    List<URL> URLDatabase = new ArrayList<>();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private URLRepository URLrepo;
    @Autowired
    private URLService URLservice;
    @Autowired
    private UserService userService;

    @GetMapping("/{name}")
    public RedirectView home(@PathVariable String name){
        URL currURL = URLservice.findURLbyShroten(name);
        if (currURL != null) {
            return new RedirectView(currURL.getURL());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Short link not defined yet");
        }
    }

    @PostMapping("/Register")
    public int RegisUser(@RequestBody User newUser){
        User Avalilable = userService.findURLbyUsername(newUser.getUsername());
        if(Avalilable == null) {
            userRepository.save(newUser);
            return 1;
        }
        return -1;
    }

    @GetMapping("/User")
    public Iterable<User> ShowUserData(){
        return userRepository.findAll();
    }

    @GetMapping("/URLs")
    public Iterable<URL> ShowURLData(){
        return URLrepo.findAll();
    }

    @GetMapping("/{name}/URLs")
    public ResponseEntity<List<URL>> ShowUrlData(@PathVariable String name) {
        User currentUser = userRepository.findByusername(name);
        if(currentUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no User exist");
        }
        List<URL> urls = currentUser.GetURLUser();
        if(currentUser != null) {
            return ResponseEntity.ok(urls);
        }
        return null;
    }

    @PostMapping("/{name}/Shorten")
    public int AddUserURL(@PathVariable String name, @RequestBody URL URLData) {
        User Available = userRepository.findByusername(name);
        URL URLAvailablity = URLrepo.findByname(URLData.getName());
        if(Available != null) {
            if(URLAvailablity == null) {
                URLrepo.save(URLData);
                userService.AddUserURL(name, URLData);
                return 1;
            }
        }
        return -1;
    }

    @PutMapping("/Clean")
    public void Clean() {
        URLrepo.deleteAll();
        userRepository.deleteAll();
    }


    @PostMapping("")
    public int UpdateLink(@RequestBody URL updateURL){
        URL Available = URLservice.findURLbyShroten(updateURL.getName());
        if(Available == null) {
            URLrepo.save(updateURL);
            return 1;
        }
        return -1;
    }
}
