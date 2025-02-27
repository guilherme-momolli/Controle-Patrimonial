package br.com.guilherme_momolli.controle_patrimonial.dto;

import lombok.Data;

@Data
public class CadastroRequestDTO {

    private String nome;
    private String email;
    private String senha;

}