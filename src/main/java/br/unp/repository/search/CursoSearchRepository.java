package br.unp.repository.search;

import br.unp.domain.Curso;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Curso entity.
 */
public interface CursoSearchRepository extends ElasticsearchRepository<Curso, Long> {
}
