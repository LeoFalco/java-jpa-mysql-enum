package br.com.sifat.model.enumeracao;

public enum CondicaoPagamento {
    A_VISTA,
    PARCELADO,
    A_FATURAR;

    public static final String columnDefinition =
            "enum('A_VISTA', 'PARCELADO', 'A_FATURAR') NOT NULL DEFAULT 'A_VISTA'";
}
