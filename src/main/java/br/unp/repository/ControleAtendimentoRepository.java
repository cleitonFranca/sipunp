package br.unp.repository;

import br.unp.domain.ControleAtendimento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ControleAtendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControleAtendimentoRepository extends JpaRepository<ControleAtendimento,Long> {
    
}
