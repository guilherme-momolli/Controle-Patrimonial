package br.com.guilherme_momolli.controle_patrimonial.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Modulos {
    HARDWARE("Hardware"),
    VEICULO("Veiculo");

    private final String descricao;

    Modulos(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Modulos fromString(String value) {
        for (Modulos md : Modulos.values()) {
            if (md.descricao.equalsIgnoreCase(value)) {
                return md;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Módulos: " + value);
    }
}
