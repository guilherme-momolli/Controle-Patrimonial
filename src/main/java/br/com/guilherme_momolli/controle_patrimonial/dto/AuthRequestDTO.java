package br.com.guilherme_momolli.controle_patrimonial.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {
    private String email;
    private String senha;
}
