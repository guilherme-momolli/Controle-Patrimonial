package br.com.guilherme_momolli.controle_patrimonial.model;

import br.com.guilherme_momolli.controle_patrimonial.model.enums.TipoInstituicao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "instituicao")
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="razao_social", nullable = false, length = 255)
    private String razaoSocial;

    @Column(name="nome_fantasia", nullable = false, length = 255)
    private String nomeFantasia;

    @Column(unique = true)
    private String email;

    @Column(name="telefone_fixo", unique = true, length = 20)
    private String telefoneFixo;

    @Column(name="telefone_celular", unique = true, length = 20)
    private String telefoneCelular;

    @Column(unique = true, length = 14)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_instituicao", nullable = false)
    private TipoInstituicao tipoInstituicao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "instituicao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("instituicao")
    private List<Hardware> hardwares;
}