package br.com.guilherme_momolli.controle_patrimonial.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name="nota_fiscal")
public class NotaFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nota_fiscal_eletronica")
    private String notaFiscalEletronica;

    private String fornecedor;

    private String cnpj;

    private Timestamp dataCompra;

    @Column(name="preco_compra")
    private double precoCompra;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco_id;
}
