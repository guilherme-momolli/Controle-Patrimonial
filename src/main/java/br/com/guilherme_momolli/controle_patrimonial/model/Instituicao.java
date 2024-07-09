//package br.com.guilherme_momolli.controle_patrimonial.model;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name="instituicao")
//public class Instituicao {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String nome;
//
//    private String email;
//
//    private String senha;
//
//    private String cnpj;
//
//    @OneToOne
//    @JoinColumn(name = "endereco_id")
//    private Endereco endereco_id;
//
//}
