package br.com.guilherme_momolli.controle_patrimonial.model.enums;

public enum Componente {

    ARMAZENAMENTO("Armazenamento"),
    FONTE_ALIMENTACAO("Fonte de Alimentação"),
    GABINETE("Gabinete"),
    MEMORIA_RAM("Memória RAM"),
    PLACA_MAE("Placa mãe"),
    PLACA_VIDEO("Placa de video"),
    PLACA_WIFI("Placa Wifi"),
    PROCESSADOR("Processador"),
    COOLER("Cooler");

    private String componente;

    Componente(String componente) {
        this.componente = componente;
    }
}
