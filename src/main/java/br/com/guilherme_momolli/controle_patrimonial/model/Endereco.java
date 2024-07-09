package br.com.guilherme_momolli.controle_patrimonial.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private int numero;

    private String bairro;

    private String cep;

    private String cidade;

    private String uf;

    private String pais;
}
