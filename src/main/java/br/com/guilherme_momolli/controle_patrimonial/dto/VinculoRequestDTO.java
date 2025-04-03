package br.com.guilherme_momolli.controle_patrimonial.dto;

import br.com.guilherme_momolli.controle_patrimonial.model.enums.Privilegio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VinculoRequestDTO {
    private Long usuarioId;
    private Long instituicaoId;
    private Privilegio permissao;
}
