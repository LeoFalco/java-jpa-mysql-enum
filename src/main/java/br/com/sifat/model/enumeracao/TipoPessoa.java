package br.com.sifat.model.enumeracao;

public enum TipoPessoa {
    NAO_INFORMADO,
    FISICA,
    JURIDICA;

    public static final String columnDefinition =
            "enum('NAO_INFORMADO', 'FISICA', 'JURIDICA') NOT NULL DEFAULT 'NAO_INFORMADO'";
}