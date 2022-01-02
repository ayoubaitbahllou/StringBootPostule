package com.offre.employee.controllers;

import com.offre.employee.models.User;
import com.offre.employee.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam Map<String, String> inputs) {
        User user = new User();
        user.setEmail(inputs.get("email"));
        user.setName(inputs.get("name"));
        user.setPassword(inputs.get("password"));
        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam Map<String, String> inputs, HttpSession session) {
        User user = userRepository.connexion(inputs.get("email"), inputs.get("password"));
        if (user != null) {
            session.setAttribute("user", user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> user(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user != null) {
            return new ResponseEntity<User>((User) user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/logout")
    public String disconnect(HttpSession session) {
        session.removeAttribute("user");
        return "disconnected succefully";
    }
}
