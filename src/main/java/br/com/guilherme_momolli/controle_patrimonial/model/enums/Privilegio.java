package br.com.guilherme_momolli.controle_patrimonial.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Privilegio {

    USUARIO("Usuário"),
    ADMIN("Administrador"),
    ADMIN_MASTER("Administrador master"),
    CONTROLE_MESTRE("Controle Mester");

    private final String descricao;

    Privilegio(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Privilegio fromString(String value) {
        for (Privilegio p : Privilegio.values()) {
            if (p.descricao.equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Privilegio: " + value);
    }
}
