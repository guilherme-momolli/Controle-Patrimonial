package br.com.guilherme_momolli.controle_patrimonial.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String senha;
    public LoginRequestDTO() {
    }
}
