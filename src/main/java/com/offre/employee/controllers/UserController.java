package com.offre.employee.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.offre.employee.models.User;
import com.offre.employee.repositories.UserRepository;
import com.offre.employee.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/register")
    public ResponseEntity<HashMap> register(@RequestParam("cv") MultipartFile file, @RequestParam Map<String, String> inputs) {
        User user = new User();
        user.setEmail(inputs.get("email"));
        user.setName(inputs.get("name"));
        user.setPassword(inputs.get("password"));
        user.setCv(this.uploadCv(file));
        userRepository.save(user);
        HashMap<String, String> map = new HashMap<>();
        map.put("message","Vous etes bien enregistrer");
        return new ResponseEntity<HashMap>(map, HttpStatus.OK);
    }

    public String uploadCv(MultipartFile file) {
        String location = "C:\\Users\\Meriem\\projects\\masterCours\\StringBootPostule\\src\\main\\uploads";
        UploadFileService uploadFileService = new UploadFileService(location);
        uploadFileService.saveFile(file);
        //
        return uploadFileService.resPath;
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/login")
    public ResponseEntity<HashMap> login(@RequestParam Map<String, String> inputs, HttpSession session) {
        User user = userRepository.connexion(inputs.get("email"), inputs.get("password"));
        System.out.println(inputs.get("email"));
        if (user != null) {

            session.setAttribute(user.getId().toString(), user.getId());
            HashMap<String, String> map = new HashMap<>();
            map.put("session_id",user.getId().toString());
            return new ResponseEntity<HashMap>(map, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/user")
    public ResponseEntity<User> user(HttpSession session,@RequestParam Map<String, String> inputs) {
        Long user_id=Long.parseLong(inputs.get("session_id"));

        Optional<User> user = userRepository.findById(user_id);
        if (user != null) {
            return new ResponseEntity( user, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/logout")
    public String disconnect(HttpSession session) {
        session.removeAttribute("user");
        return "disconnected succefully";
    }


}
