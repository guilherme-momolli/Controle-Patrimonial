package br.com.guilherme_momolli.controle_patrimonial.model;

import br.com.guilherme_momolli.controle_patrimonial.model.enums.Componente;
import br.com.guilherme_momolli.controle_patrimonial.model.enums.Estatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="hardware")
public class Hardware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="codigo_patrimonial")
    private String codigoPatrimonial;

    @Enumerated(EnumType.STRING)
    private Componente componente;

    @Column(name="numero_serial")
    private String numeroSerial;

    private String modelo;

    private String fabricante;

    private String velocidade;

    @Column(name="capacidade_armazenamento")
    private String capacidadeArmazenamento;

    @Column(name="data_fabricacao")
    private Timestamp dataFabricacao;

    @Column(name="preco_total")
    private Double precoTotal;

    @Enumerated(EnumType.STRING)
    private Estatus estatus;

    private Double voltagem;

    @OneToOne
    @JoinColumn(name = "nota_fiscal_id", referencedColumnName = "id")
    private NotaFiscal notaFiscal;

    @Column(name = "imagem_url")
    private String imagemUrl;
}
