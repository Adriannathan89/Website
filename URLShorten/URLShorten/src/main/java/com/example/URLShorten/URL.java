package com.example.URLShorten;

import jakarta.persistence.*;
import lombok.*;

import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String URL;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;
}
