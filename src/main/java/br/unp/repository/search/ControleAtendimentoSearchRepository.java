package br.unp.repository.search;

import br.unp.domain.ControleAtendimento;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ControleAtendimento entity.
 */
public interface ControleAtendimentoSearchRepository extends ElasticsearchRepository<ControleAtendimento, Long> {
}
