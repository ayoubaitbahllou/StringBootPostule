package com.offre.employee.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class User {

    public User() {
    }

    public User(String name, String email, String password, String cv) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cv = cv;
    }

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

	@Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(nullable = true)
    private String cv;

    @OneToMany(mappedBy = "user")
    private Set<Offre> offre;

    @ManyToMany
    Set<Offre> offres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public Set<Offre> getOffre() {
        return offre;
    }

    public void setOffre(Set<Offre> offre) {
        this.offre = offre;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }
}
