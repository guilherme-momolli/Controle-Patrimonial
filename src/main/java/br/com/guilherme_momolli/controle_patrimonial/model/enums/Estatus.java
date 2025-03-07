package br.com.guilherme_momolli.controle_patrimonial.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Estatus {

    NOVO("Novo"),
    USADO("Usado"),
    MANUTENCAO("Em manutenção"),
    RESTAURADO("Restaurado"),
    DEFEITUSO("Defeituoso"),
    EMPRESTADO("Emprestado"),
    ROUBADO("Roubado"),
    DESAPARECIDO("Desaparecido"),
    INUTILIZADO("Inutilizado"),
    DESCARTADO("Descartado");

    private final String descricao;

    Estatus(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Estatus fromString(String value) {
        for (Estatus e : Estatus.values()) {
            if (e.descricao.equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Estatus: " + value);
    }
}
