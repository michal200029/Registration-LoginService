package pl.opalka.Registration.Login.Service.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;




}
