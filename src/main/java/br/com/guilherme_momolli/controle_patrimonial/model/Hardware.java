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

    @Enumerated
    private Componente componente;

    @Column(name="numero_serial")
    private String numeroSerial;

    private String modelo;

    private String fabricante;

    private String velocidade;

    ///colocar o numero de série
    @Column(name="capacidade_armazenamento")
    private String capacidadeArmazenamento;

    @Column(name="data_fabricacao")
    private Timestamp dataFabricacao;

   // private Timestamp data_compra;

    //private Timestamp data_lancamento;
    @Column(name="preco_total")
    private Double precoTotal;

    @Enumerated
    private Estatus estatus;
}