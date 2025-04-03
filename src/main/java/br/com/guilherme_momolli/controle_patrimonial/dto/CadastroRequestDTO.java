package br.com.guilherme_momolli.controle_patrimonial.dto;

import br.com.guilherme_momolli.controle_patrimonial.model.enums.Privilegio;
import lombok.Data;

@Data
public class CadastroRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private Long instituicaoId;
    private Privilegio privilegio;

}