package br.com.guilherme_momolli.controle_patrimonial.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

}
