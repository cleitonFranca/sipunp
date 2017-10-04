package br.unp.repository.search;

import br.unp.domain.Aluno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Aluno entity.
 */
public interface AlunoSearchRepository extends ElasticsearchRepository<Aluno, Long> {
}
