package br.com.sifat.model.enumeracao;

public enum EstadoCivil {
    NAO_INFORMADO,
    SOLTEIRO,
    CASADO,
    DIVORCIADO,
    VIUVO;

    public static final String columDefinition =
            "enum('NAO_INFORMADO', 'SOLTEIRO', 'CASADO', 'DIVORCIADO', 'VIUVO') NOT NULL DEFAULT 'NAO_INFORMADO'";
}
