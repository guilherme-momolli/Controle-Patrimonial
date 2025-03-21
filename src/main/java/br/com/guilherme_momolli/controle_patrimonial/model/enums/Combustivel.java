package br.com.guilherme_momolli.controle_patrimonial.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Combustivel {

    DIESEL("Diesel"),
    ELETRICO("Elétrico"),
    FLEX("Flex"),
    ETANOL("Etanol"),
    GASOLINA("Gasolina"),
    HIBRIDO("Hibrido");

    private String descricao;

    Combustivel(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Combustivel fromString(String value) {
        for ( Combustivel c : Combustivel.values()) {
            if (c.descricao.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Combustivel: " + value);
    }
}
