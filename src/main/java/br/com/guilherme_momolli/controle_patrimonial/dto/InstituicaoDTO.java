package br.com.guilherme_momolli.controle_patrimonial.dto;

import br.com.guilherme_momolli.controle_patrimonial.model.Instituicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstituicaoDTO {
    private Long id;
    private String nomeFantasia;

    public InstituicaoDTO(Instituicao entidade) {
        this.id = entidade.getId();
        this.nomeFantasia = entidade.getNomeFantasia();
    }
}
