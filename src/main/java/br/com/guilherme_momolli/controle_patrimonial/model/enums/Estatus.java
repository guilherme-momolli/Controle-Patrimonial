package br.com.guilherme_momolli.controle_patrimonial.model.enums;

public enum Estatus {

    NOVO("Novo"),
    USADO("Usado"),
    RESTAURADO("Restaurado"),
    DEFEITUSO("Defeituoso"),
    INUTILIZADO("Inutilizado");
    private String estatus;

    Estatus(String estatus) {
        this.estatus = estatus;
    }
}
