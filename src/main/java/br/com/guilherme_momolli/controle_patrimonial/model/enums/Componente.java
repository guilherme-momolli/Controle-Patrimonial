package br.com.guilherme_momolli.controle_patrimonial.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Componente {
    PROCESSADOR("Processador"),
    MEMORIA_RAM("Memória RAM"),
    PLACA_MAE("Placa Mãe"),
    ARMAZENAMENTO("Armazenamento"),
    PLACA_VIDEO("Placa de vídeo"),
    FONTE_ALIMENTACAO("Fonte de alimentação"),
    PLACA_WIFI("Placa Wi-Fi"),
    COOLER("Cooler"),
    GABINETE("Gabinete"),
    NOTEBOOK("Notebook");

    private final String descricao;

    Componente(String descricao) {
        this.descricao = descricao;
    }

    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Componente fromString(String value) {
        for (Componente c : Componente.values()) {
            if (c.descricao.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Componente: " + value);
    }
}
