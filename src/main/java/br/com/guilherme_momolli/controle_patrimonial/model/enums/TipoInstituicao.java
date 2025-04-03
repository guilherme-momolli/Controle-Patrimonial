package br.com.guilherme_momolli.controle_patrimonial.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoInstituicao {

    PUBLICA("Pública"),
    PRIVADA("Privada");

    private final String descricao;

    TipoInstituicao(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static TipoInstituicao fromString(String value) {
        for (TipoInstituicao tp : TipoInstituicao.values()) {
            if (tp.descricao.equalsIgnoreCase(value)) {
                return tp;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Componente: " + value);
    }
}
