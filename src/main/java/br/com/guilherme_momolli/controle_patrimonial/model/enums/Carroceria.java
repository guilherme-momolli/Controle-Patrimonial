package br.com.guilherme_momolli.controle_patrimonial.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Carroceria {
    CUPE("Cupê"),
    HATCH("Hatch"),
    JIPE("Jipe"),
    NAO_APLICAVEL("Não Aplicavel"),
    PERUA("Perua"),
    PICAPE("Picape"),
    ROADSTER("Roadster"),
    SEDAN("Sedan"),
    SUV("Veiculo Utilitário Esportivo - SUV"),
    TARGA("Targa"),
    VAN("Van");

    private final String descricao;

    Carroceria(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Carroceria fromString(String value) {
        for ( Carroceria c : Carroceria.values()) {
            if (c.descricao.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Carroceria: " + value);
    }
}
