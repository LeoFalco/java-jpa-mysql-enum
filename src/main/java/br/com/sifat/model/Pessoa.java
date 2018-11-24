package br.com.sifat.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    String apelido;
    String inscricaoFederal;
    String incricaoEstadual;
    @Enumerated
    @Column(name = "tipo_pessoa")
    TipoPessoa tipoPessoa = TipoPessoa.FISICA;
    @Enumerated
    @Column
    EstadoCivil estadoCivil = EstadoCivil.NAO_INFORMADO;
    @ElementCollection
    @CollectionTable
    List<String> telefones;
}
