package br.com.sifat.model;

import br.com.sifat.model.enumeracao.Classificacao;
import br.com.sifat.model.enumeracao.EstadoCivil;
import br.com.sifat.model.enumeracao.TipoPessoa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static br.com.sifat.processor.CustomEnumeratedProcessor.createColumnDefinitionStamment;

@Entity
@Table(name = "pessoa")
public class Pessoa {
    private static final String definition = createColumnDefinitionStamment(TipoPessoa.values());
    private final String definitions = "var";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String apelido;

    @Column(name = "inscricao_federal")
    private String inscricaoFederal;

    @Column(name = "inscricao_estadual")
    private String incricaoEstadual;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", columnDefinition = TipoPessoa.columnDefinition)
    private TipoPessoa tipoPessoa = TipoPessoa.FISICA;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil", columnDefinition = EstadoCivil.columDefinition)
    private EstadoCivil estadoCivil = EstadoCivil.NAO_INFORMADO;

    @ElementCollection
    @Column(name = "telefone")
    @CollectionTable(name = "pessoa_telefone",
            joinColumns = @JoinColumn(name = "pessoa_id"))
    private List<String> telefones;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao", columnDefinition = Classificacao.columnDefinition)
    @CollectionTable(name = "pessoa_classificacao",
            joinColumns = @JoinColumn(name = "pessoa_id"))
    private List<Classificacao> classificacoes = new ArrayList<>();

    public Pessoa() {
    }

    public Long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getApelido() {
        return this.apelido;
    }

    public String getInscricaoFederal() {
        return this.inscricaoFederal;
    }

    public String getIncricaoEstadual() {
        return this.incricaoEstadual;
    }

    public TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    public EstadoCivil getEstadoCivil() {
        return this.estadoCivil;
    }

    public List<String> getTelefones() {
        return this.telefones;
    }

    public List<Classificacao> getClassificacoes() {
        return this.classificacoes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setInscricaoFederal(String inscricaoFederal) {
        this.inscricaoFederal = inscricaoFederal;
    }

    public void setIncricaoEstadual(String incricaoEstadual) {
        this.incricaoEstadual = incricaoEstadual;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

    public void setClassificacoes(List<Classificacao> classificacoes) {
        this.classificacoes = classificacoes;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Pessoa)) return false;
        final Pessoa other = (Pessoa) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$nome = this.getNome();
        final Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final Object this$apelido = this.getApelido();
        final Object other$apelido = other.getApelido();
        if (this$apelido == null ? other$apelido != null : !this$apelido.equals(other$apelido)) return false;
        final Object this$inscricaoFederal = this.getInscricaoFederal();
        final Object other$inscricaoFederal = other.getInscricaoFederal();
        if (this$inscricaoFederal == null ? other$inscricaoFederal != null : !this$inscricaoFederal.equals(other$inscricaoFederal))
            return false;
        final Object this$incricaoEstadual = this.getIncricaoEstadual();
        final Object other$incricaoEstadual = other.getIncricaoEstadual();
        if (this$incricaoEstadual == null ? other$incricaoEstadual != null : !this$incricaoEstadual.equals(other$incricaoEstadual))
            return false;
        final Object this$tipoPessoa = this.getTipoPessoa();
        final Object other$tipoPessoa = other.getTipoPessoa();
        if (this$tipoPessoa == null ? other$tipoPessoa != null : !this$tipoPessoa.equals(other$tipoPessoa))
            return false;
        final Object this$estadoCivil = this.getEstadoCivil();
        final Object other$estadoCivil = other.getEstadoCivil();
        if (this$estadoCivil == null ? other$estadoCivil != null : !this$estadoCivil.equals(other$estadoCivil))
            return false;
        final Object this$telefones = this.getTelefones();
        final Object other$telefones = other.getTelefones();
        if (this$telefones == null ? other$telefones != null : !this$telefones.equals(other$telefones)) return false;
        final Object this$classificacoes = this.getClassificacoes();
        final Object other$classificacoes = other.getClassificacoes();
        return this$classificacoes == null ? other$classificacoes == null : this$classificacoes.equals(other$classificacoes);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Pessoa;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final Object $apelido = this.getApelido();
        result = result * PRIME + ($apelido == null ? 43 : $apelido.hashCode());
        final Object $inscricaoFederal = this.getInscricaoFederal();
        result = result * PRIME + ($inscricaoFederal == null ? 43 : $inscricaoFederal.hashCode());
        final Object $incricaoEstadual = this.getIncricaoEstadual();
        result = result * PRIME + ($incricaoEstadual == null ? 43 : $incricaoEstadual.hashCode());
        final Object $tipoPessoa = this.getTipoPessoa();
        result = result * PRIME + ($tipoPessoa == null ? 43 : $tipoPessoa.hashCode());
        final Object $estadoCivil = this.getEstadoCivil();
        result = result * PRIME + ($estadoCivil == null ? 43 : $estadoCivil.hashCode());
        final Object $telefones = this.getTelefones();
        result = result * PRIME + ($telefones == null ? 43 : $telefones.hashCode());
        final Object $classificacoes = this.getClassificacoes();
        result = result * PRIME + ($classificacoes == null ? 43 : $classificacoes.hashCode());
        return result;
    }

    public String toString() {
        return "Pessoa(id=" + this.getId() + ", nome=" + this.getNome() + ", apelido=" + this.getApelido() + ", inscricaoFederal=" + this.getInscricaoFederal() + ", incricaoEstadual=" + this.getIncricaoEstadual() + ", tipoPessoa=" + this.getTipoPessoa() + ", estadoCivil=" + this.getEstadoCivil() + ", telefones=" + this.getTelefones() + ", classificacoes=" + this.getClassificacoes() + ")";
    }
}
