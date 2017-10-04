package br.unp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Curso.
 */
@Entity
@Table(name = "curso")
@Document(indexName = "curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "coordenado", nullable = false)
    private String coordenado;

    @ManyToMany(mappedBy = "cursos")
    @JsonIgnore
    private Set<Professor> professors = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private Set<Turma> turmas = new HashSet<>();

    @OneToMany(mappedBy = "curso")
    @JsonIgnore
    private Set<Aluno> alunos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Curso nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCoordenado() {
        return coordenado;
    }

    public Curso coordenado(String coordenado) {
        this.coordenado = coordenado;
        return this;
    }

    public void setCoordenado(String coordenado) {
        this.coordenado = coordenado;
    }

    public Set<Professor> getProfessors() {
        return professors;
    }

    public Curso professors(Set<Professor> professors) {
        this.professors = professors;
        return this;
    }

    public Curso addProfessor(Professor professor) {
        this.professors.add(professor);
        professor.getCursos().add(this);
        return this;
    }

    public Curso removeProfessor(Professor professor) {
        this.professors.remove(professor);
        professor.getCursos().remove(this);
        return this;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public Curso turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public Curso addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.setCurso(this);
        return this;
    }

    public Curso removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.setCurso(null);
        return this;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public Curso alunos(Set<Aluno> alunos) {
        this.alunos = alunos;
        return this;
    }

    public Curso addAluno(Aluno aluno) {
        this.alunos.add(aluno);
        aluno.setCurso(this);
        return this;
    }

    public Curso removeAluno(Aluno aluno) {
        this.alunos.remove(aluno);
        aluno.setCurso(null);
        return this;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Curso curso = (Curso) o;
        if (curso.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curso.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Curso{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", coordenado='" + getCoordenado() + "'" +
            "}";
    }
}
