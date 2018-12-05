package br.com.sifat.model;

import br.com.sifat.model.enumeracao.CondicaoPagamento;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "forma_pagamento")
public class FormaPagamento {

    private Long id;
    private Set<CondicaoPagamento> condicoesPagamento;
    private String descricao;

    public FormaPagamento() {
    }

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(
            name = "forma_pagamento_condicao_pagamento",
            joinColumns = @JoinColumn(name = "forma_pagamento_id"))
    @Column(name = "condicao_pagamento", columnDefinition = CondicaoPagamento.columnDefinition)
    public Set<CondicaoPagamento> getCondicoesPagamento() {
        return condicoesPagamento;
    }

    public void setCondicoesPagamento(Set<CondicaoPagamento> condicaoPagamento) {
        this.condicoesPagamento = condicaoPagamento;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FormaPagamento)) return false;
        final FormaPagamento other = (FormaPagamento) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$condicoesPagamento = this.getCondicoesPagamento();
        final Object other$condicoesPagamento = other.getCondicoesPagamento();
        if (this$condicoesPagamento == null
                ? other$condicoesPagamento != null
                : !this$condicoesPagamento.equals(other$condicoesPagamento)) return false;
        final Object this$descricao = this.getDescricao();
        final Object other$descricao = other.getDescricao();
        return this$descricao == null ? other$descricao == null : this$descricao.equals(other$descricao);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FormaPagamento;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $condicoesPagamento = this.getCondicoesPagamento();
        result = result * PRIME + ($condicoesPagamento == null ? 43 : $condicoesPagamento.hashCode());
        final Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        return result;
    }

    public String toString() {
        return "FormaPagamento(id="
                + this.getId()
                + ", condicoesPagamento="
                + this.getCondicoesPagamento()
                + ", descricao="
                + this.getDescricao()
                + ")";
    }
}
