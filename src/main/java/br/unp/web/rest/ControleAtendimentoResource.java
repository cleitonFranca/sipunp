package br.unp.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.unp.domain.ControleAtendimento;

import br.unp.repository.ControleAtendimentoRepository;
import br.unp.repository.search.ControleAtendimentoSearchRepository;
import br.unp.web.rest.util.HeaderUtil;
import br.unp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ControleAtendimento.
 */
@RestController
@RequestMapping("/api")
public class ControleAtendimentoResource {

    private final Logger log = LoggerFactory.getLogger(ControleAtendimentoResource.class);

    private static final String ENTITY_NAME = "controleAtendimento";

    private final ControleAtendimentoRepository controleAtendimentoRepository;

    private final ControleAtendimentoSearchRepository controleAtendimentoSearchRepository;

    public ControleAtendimentoResource(ControleAtendimentoRepository controleAtendimentoRepository, ControleAtendimentoSearchRepository controleAtendimentoSearchRepository) {
        this.controleAtendimentoRepository = controleAtendimentoRepository;
        this.controleAtendimentoSearchRepository = controleAtendimentoSearchRepository;
    }

    /**
     * POST  /controle-atendimentos : Create a new controleAtendimento.
     *
     * @param controleAtendimento the controleAtendimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new controleAtendimento, or with status 400 (Bad Request) if the controleAtendimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/controle-atendimentos")
    @Timed
    public ResponseEntity<ControleAtendimento> createControleAtendimento(@Valid @RequestBody ControleAtendimento controleAtendimento) throws URISyntaxException {
        log.debug("REST request to save ControleAtendimento : {}", controleAtendimento);
        if (controleAtendimento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new controleAtendimento cannot already have an ID")).body(null);
        }
        ControleAtendimento result = controleAtendimentoRepository.save(controleAtendimento);
        controleAtendimentoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/controle-atendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /controle-atendimentos : Updates an existing controleAtendimento.
     *
     * @param controleAtendimento the controleAtendimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated controleAtendimento,
     * or with status 400 (Bad Request) if the controleAtendimento is not valid,
     * or with status 500 (Internal Server Error) if the controleAtendimento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/controle-atendimentos")
    @Timed
    public ResponseEntity<ControleAtendimento> updateControleAtendimento(@Valid @RequestBody ControleAtendimento controleAtendimento) throws URISyntaxException {
        log.debug("REST request to update ControleAtendimento : {}", controleAtendimento);
        if (controleAtendimento.getId() == null) {
            return createControleAtendimento(controleAtendimento);
        }
        ControleAtendimento result = controleAtendimentoRepository.save(controleAtendimento);
        controleAtendimentoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, controleAtendimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /controle-atendimentos : get all the controleAtendimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of controleAtendimentos in body
     */
    @GetMapping("/controle-atendimentos")
    @Timed
    public ResponseEntity<List<ControleAtendimento>> getAllControleAtendimentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ControleAtendimentos");
        Page<ControleAtendimento> page = controleAtendimentoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/controle-atendimentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /controle-atendimentos/:id : get the "id" controleAtendimento.
     *
     * @param id the id of the controleAtendimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the controleAtendimento, or with status 404 (Not Found)
     */
    @GetMapping("/controle-atendimentos/{id}")
    @Timed
    public ResponseEntity<ControleAtendimento> getControleAtendimento(@PathVariable Long id) {
        log.debug("REST request to get ControleAtendimento : {}", id);
        ControleAtendimento controleAtendimento = controleAtendimentoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(controleAtendimento));
    }

    /**
     * DELETE  /controle-atendimentos/:id : delete the "id" controleAtendimento.
     *
     * @param id the id of the controleAtendimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/controle-atendimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteControleAtendimento(@PathVariable Long id) {
        log.debug("REST request to delete ControleAtendimento : {}", id);
        controleAtendimentoRepository.delete(id);
        controleAtendimentoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/controle-atendimentos?query=:query : search for the controleAtendimento corresponding
     * to the query.
     *
     * @param query the query of the controleAtendimento search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/controle-atendimentos")
    @Timed
    public ResponseEntity<List<ControleAtendimento>> searchControleAtendimentos(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ControleAtendimentos for query {}", query);
        Page<ControleAtendimento> page = controleAtendimentoSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/controle-atendimentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
