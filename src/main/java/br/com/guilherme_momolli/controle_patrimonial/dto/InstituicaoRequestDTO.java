package br.com.guilherme_momolli.controle_patrimonial.dto;


import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituicaoRequestDTO {
    private Instituicao instituicao;
    private String senha;
}
