package com.example.URLShorten;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<URL> URLUser = new ArrayList<>();

    public void addURL(URL URLinfo) {
        URLUser.add(URLinfo);
        URLinfo.setUser(this);
    }

    public List<URL> GetURLUser() {
        return URLUser;
    }

}