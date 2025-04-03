package br.com.guilherme_momolli.controle_patrimonial.model;

import br.com.guilherme_momolli.controle_patrimonial.model.enums.Componente;
import br.com.guilherme_momolli.controle_patrimonial.model.enums.Estatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "hardware")
public class Hardware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_patrimonial", nullable = false, unique = true, length = 50)
    private String codigoPatrimonial;

    @Enumerated(EnumType.STRING)
    @Column(name = "componente", nullable = false)
    private Componente componente;

    @Column(name = "numero_serial", unique = true, length = 100)
    private String numeroSerial;

    @Column(name = "modelo", length = 100)
    private String modelo;

    @Column(name = "fabricante", length = 100)
    private String fabricante;

    @Column(name = "velocidade", length = 20)
    private String velocidade;

    @Column(name = "capacidade_armazenamento", length = 50)
    private String capacidadeArmazenamento;

    @Column(name = "data_fabricacao")
    private LocalDateTime dataFabricacao;

    @Column(name = "preco_total", precision = 10, scale = 2)
    private BigDecimal precoTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", nullable = false)
    private Estatus estatus;

    @Column(name = "voltagem", precision = 5, scale = 2)
    private BigDecimal voltagem;

    @Column(name = "imagem_url", length = 255)
    private String imagemUrl;

    @ManyToOne
    @JoinColumn(name = "instituicao_id", nullable = false)
    private Instituicao instituicao;
}
