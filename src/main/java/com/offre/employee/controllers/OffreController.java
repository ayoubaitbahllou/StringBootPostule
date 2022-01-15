package com.offre.employee.controllers;


import com.offre.employee.models.Offre;
import com.offre.employee.models.User;
import com.offre.employee.repositories.OffreRepository;
import com.offre.employee.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OffreController {

    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/offre")
    public ResponseEntity<HashMap> saveOffre(@RequestParam Map<String, String> inputs, HttpSession session) {
        Offre offre = new Offre();
        offre.setTitle(inputs.get("title"));
        offre.setDescription(inputs.get("description"));
        offre.setEtat(false);
        String session_id = inputs.get("session_id");
        Long user_id = (Long) session.getAttribute(session_id);
        Optional<User> user = userRepository.findById(user_id);
        if (user != null) {
            User u = user.get();
            offre.setUser(u);
        }
        offreRepository.save(offre);

        HashMap<String, String> map = new HashMap<>();
        map.put("message", "Votre offre est bien enregistrer");

        return new ResponseEntity<HashMap>(map, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200/")
    @GetMapping("/offres")
    public ResponseEntity<HashMap> getOffres(@RequestParam Map<String, String> inputs) {
        List<Offre> offres= (List<Offre>) offreRepository.findAll();
        HashMap<String, List<Offre>> map = new HashMap<>();
        map.put("message", offres);

        return new ResponseEntity<HashMap>(map, HttpStatus.OK);
    }

    @Transactional
    @CrossOrigin(origins = "http://localhost:4200/")
    @PostMapping("/postuler")
    public ResponseEntity postuler(@RequestParam Map<String, String> inputs, HttpSession session) {
        String session_id = inputs.get("session_id");

        Long offre_id=Long.parseLong(inputs.get("offre_id"));
        Long user_id = (Long) session.getAttribute(session_id);

        Integer isPostuled =offreRepository.hasPostuled(user_id,offre_id);
        HashMap<String, String> map = new HashMap<>();

        if (isPostuled > 0){
            map.put("message","vous avez deja postuler a cette offre");
            map.put("status","400");
        }else{
            offreRepository.postule(user_id,offre_id);
            map.put("message","vous avez bien postuler a cette offre");
            map.put("status","201");
        }

        return new ResponseEntity(map,HttpStatus.OK);

    }
}
