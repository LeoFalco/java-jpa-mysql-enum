package br.com.sifat.model.enumeracao;

public enum Classificacao {
    NENHUM,
    VIP,
    ESTUDANTE,
    APOSENTADO;

    public static final String columnDefinition =
            "enum('NENHUM', 'VIP', 'ESTUDANTE', 'APOSENTADO') NOT NULL DEFAULT 'NENHUM'";
}
