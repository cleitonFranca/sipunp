package br.unp.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Professor.
 */
@Entity
@Table(name = "professor")
@Document(indexName = "professor")
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(name = "professor_turma",
               joinColumns = @JoinColumn(name="professors_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="turmas_id", referencedColumnName="id"))
    private Set<Turma> turmas = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "professor_curso",
               joinColumns = @JoinColumn(name="professors_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="cursos_id", referencedColumnName="id"))
    private Set<Curso> cursos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Professor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Turma> getTurmas() {
        return turmas;
    }

    public Professor turmas(Set<Turma> turmas) {
        this.turmas = turmas;
        return this;
    }

    public Professor addTurma(Turma turma) {
        this.turmas.add(turma);
        turma.getProfessors().add(this);
        return this;
    }

    public Professor removeTurma(Turma turma) {
        this.turmas.remove(turma);
        turma.getProfessors().remove(this);
        return this;
    }

    public void setTurmas(Set<Turma> turmas) {
        this.turmas = turmas;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public Professor cursos(Set<Curso> cursos) {
        this.cursos = cursos;
        return this;
    }

    public Professor addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.getProfessors().add(this);
        return this;
    }

    public Professor removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.getProfessors().remove(this);
        return this;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Professor professor = (Professor) o;
        if (professor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Professor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
