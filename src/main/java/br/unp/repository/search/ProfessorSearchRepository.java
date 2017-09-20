package br.unp.repository.search;

import br.unp.domain.Professor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Professor entity.
 */
public interface ProfessorSearchRepository extends ElasticsearchRepository<Professor, Long> {
}
