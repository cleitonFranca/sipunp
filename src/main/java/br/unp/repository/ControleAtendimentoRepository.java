package br.unp.repository;

import br.unp.domain.ControleAtendimento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the ControleAtendimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControleAtendimentoRepository extends JpaRepository<ControleAtendimento,Long> {

    @Query("select controle_atendimento from ControleAtendimento controle_atendimento where controle_atendimento.user.login = ?#{principal.username}")
    List<ControleAtendimento> findByUserIsCurrentUser();
    
}
