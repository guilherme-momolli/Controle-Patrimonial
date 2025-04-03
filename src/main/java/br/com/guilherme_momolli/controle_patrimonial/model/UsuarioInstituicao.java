package br.com.guilherme_momolli.controle_patrimonial.model;

import br.com.guilherme_momolli.controle_patrimonial.model.enums.Privilegio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario_instituicao")
public class UsuarioInstituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties("instituicoes")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    @JsonIgnoreProperties("usuarios")
    private Instituicao instituicao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Privilegio permissao;
}
