package br.unp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Aluno.
 */
@Entity
@Table(name = "aluno")
@Document(indexName = "aluno")
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "matricula", nullable = false)
    private Integer matricula;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Endereco endereco;

    @ManyToMany
    @NotNull
    @JoinTable(name = "aluno_turma",
               joinColumns = @JoinColumn(name="alunos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="turmas_id", referencedColumnName="id"))
    private Set<Turma> turmas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Curso curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public Aluno matricula(Integer matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public Aluno nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Aluno endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public Aluno turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public Aluno addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.getAlunos().add(this);
        return this;
    }

    public Aluno removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.getAlunos().remove(this);
        return this;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    public Curso getCurso() {
        return curso;
    }

    public Aluno curso(Curso curso) {
        this.curso = curso;
        return this;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Aluno aluno = (Aluno) o;
        if (aluno.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aluno.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Aluno{" +
            "id=" + getId() +
            ", matricula='" + getMatricula() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
