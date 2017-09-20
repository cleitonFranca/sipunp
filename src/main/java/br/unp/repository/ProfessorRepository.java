package br.unp.repository;

import br.unp.domain.Professor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Professor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long> {
    
    @Query("select distinct professor from Professor professor left join fetch professor.turmas left join fetch professor.cursos")
    List<Professor> findAllWithEagerRelationships();

    @Query("select professor from Professor professor left join fetch professor.turmas left join fetch professor.cursos where professor.id =:id")
    Professor findOneWithEagerRelationships(@Param("id") Long id);
    
}
