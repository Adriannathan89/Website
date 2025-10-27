package com.example.URLShorten;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLService {
    @Autowired URLRepository URLrepo;

    public URL findURLbyShroten(String Shorten) {
        return URLrepo.findByname(Shorten);
    }
}
