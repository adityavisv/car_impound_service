package com.teksecure.service.impoundsrv.model.entity;

import com.teksecure.service.impoundsrv.model.type.Erole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Erole name;


    public Role(Erole name) {
        this.name = name;
    }
}
