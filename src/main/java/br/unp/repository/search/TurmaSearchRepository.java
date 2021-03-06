package br.unp.repository.search;

import br.unp.domain.Turma;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Turma entity.
 */
public interface TurmaSearchRepository extends ElasticsearchRepository<Turma, Long> {
}
