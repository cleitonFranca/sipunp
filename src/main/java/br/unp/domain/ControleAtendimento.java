package br.unp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ControleAtendimento.
 */
@Entity
@Table(name = "controle_atendimento")
@Document(indexName = "controleatendimento")
public class ControleAtendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "queixa")
    private String queixa;

    @Column(name = "encaminhamento")
    private String encaminhamento;

    @Column(name = "vinculo")
    private String vinculo;

    @Column(name = "data_cadastro")
    private Instant dataCadastro;

    @Column(name = "data_alteracao")
    private LocalDate dataAlteracao;

    @ManyToOne(optional = false)
    @NotNull
    private Cliente cliente;

    @ManyToOne(optional = false)
    @NotNull
    private Aluno aluno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public ControleAtendimento numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getQueixa() {
        return queixa;
    }

    public ControleAtendimento queixa(String queixa) {
        this.queixa = queixa;
        return this;
    }

    public void setQueixa(String queixa) {
        this.queixa = queixa;
    }

    public String getEncaminhamento() {
        return encaminhamento;
    }

    public ControleAtendimento encaminhamento(String encaminhamento) {
        this.encaminhamento = encaminhamento;
        return this;
    }

    public void setEncaminhamento(String encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public String getVinculo() {
        return vinculo;
    }

    public ControleAtendimento vinculo(String vinculo) {
        this.vinculo = vinculo;
        return this;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public Instant getDataCadastro() {
        return dataCadastro;
    }

    public ControleAtendimento dataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public void setDataCadastro(Instant dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public ControleAtendimento dataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
        return this;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ControleAtendimento cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public ControleAtendimento aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ControleAtendimento controleAtendimento = (ControleAtendimento) o;
        if (controleAtendimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), controleAtendimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ControleAtendimento{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", queixa='" + getQueixa() + "'" +
            ", encaminhamento='" + getEncaminhamento() + "'" +
            ", vinculo='" + getVinculo() + "'" +
            ", dataCadastro='" + getDataCadastro() + "'" +
            ", dataAlteracao='" + getDataAlteracao() + "'" +
            "}";
    }
}
