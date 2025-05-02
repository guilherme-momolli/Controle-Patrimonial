package br.com.guilherme_momolli.controle_patrimonial.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String usuarioNome;
    List<InstituicaoDTO> instituicoes;
}
