package br.com.guilherme_momolli.controle_patrimonial.dto;

public class LoginRequestDTO {
    private String email;
    private String senha;
    public LoginRequestDTO() {
    }
    public LoginRequestDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String login) {
        this.email = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
