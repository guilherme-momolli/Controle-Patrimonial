package br.com.guilherme_momolli.controle_patrimonial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalizarLoginDTO {
    private String email;
    private Long instituicaoId;
}
