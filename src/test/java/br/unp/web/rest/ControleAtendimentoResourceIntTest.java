package br.unp.web.rest;

import br.unp.UnpsipApp;

import br.unp.domain.ControleAtendimento;
import br.unp.domain.Cliente;
import br.unp.domain.Aluno;
import br.unp.repository.ControleAtendimentoRepository;
import br.unp.repository.search.ControleAtendimentoSearchRepository;
import br.unp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ControleAtendimentoResource REST controller.
 *
 * @see ControleAtendimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UnpsipApp.class)
public class ControleAtendimentoResourceIntTest {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_QUEIXA = "AAAAAAAAAA";
    private static final String UPDATED_QUEIXA = "BBBBBBBBBB";

    private static final String DEFAULT_ENCAMINHAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ENCAMINHAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_VINCULO = "AAAAAAAAAA";
    private static final String UPDATED_VINCULO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_CADASTRO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CADASTRO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_DATA_ALTERACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ALTERACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ControleAtendimentoRepository controleAtendimentoRepository;

    @Autowired
    private ControleAtendimentoSearchRepository controleAtendimentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restControleAtendimentoMockMvc;

    private ControleAtendimento controleAtendimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ControleAtendimentoResource controleAtendimentoResource = new ControleAtendimentoResource(controleAtendimentoRepository, controleAtendimentoSearchRepository);
        this.restControleAtendimentoMockMvc = MockMvcBuilders.standaloneSetup(controleAtendimentoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControleAtendimento createEntity(EntityManager em) {
        ControleAtendimento controleAtendimento = new ControleAtendimento()
            .numero(DEFAULT_NUMERO)
            .queixa(DEFAULT_QUEIXA)
            .encaminhamento(DEFAULT_ENCAMINHAMENTO)
            .vinculo(DEFAULT_VINCULO)
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .dataAlteracao(DEFAULT_DATA_ALTERACAO);
        // Add required entity
        Cliente cliente = ClienteResourceIntTest.createEntity(em);
        em.persist(cliente);
        em.flush();
        controleAtendimento.setCliente(cliente);
        // Add required entity
        Aluno aluno = AlunoResourceIntTest.createEntity(em);
        em.persist(aluno);
        em.flush();
        controleAtendimento.setAluno(aluno);
        return controleAtendimento;
    }

    @Before
    public void initTest() {
        controleAtendimentoSearchRepository.deleteAll();
        controleAtendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createControleAtendimento() throws Exception {
        int databaseSizeBeforeCreate = controleAtendimentoRepository.findAll().size();

        // Create the ControleAtendimento
        restControleAtendimentoMockMvc.perform(post("/api/controle-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleAtendimento)))
            .andExpect(status().isCreated());

        // Validate the ControleAtendimento in the database
        List<ControleAtendimento> controleAtendimentoList = controleAtendimentoRepository.findAll();
        assertThat(controleAtendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        ControleAtendimento testControleAtendimento = controleAtendimentoList.get(controleAtendimentoList.size() - 1);
        assertThat(testControleAtendimento.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testControleAtendimento.getQueixa()).isEqualTo(DEFAULT_QUEIXA);
        assertThat(testControleAtendimento.getEncaminhamento()).isEqualTo(DEFAULT_ENCAMINHAMENTO);
        assertThat(testControleAtendimento.getVinculo()).isEqualTo(DEFAULT_VINCULO);
        assertThat(testControleAtendimento.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testControleAtendimento.getDataAlteracao()).isEqualTo(DEFAULT_DATA_ALTERACAO);

        // Validate the ControleAtendimento in Elasticsearch
        ControleAtendimento controleAtendimentoEs = controleAtendimentoSearchRepository.findOne(testControleAtendimento.getId());
        assertThat(controleAtendimentoEs).isEqualToComparingFieldByField(testControleAtendimento);
    }

    @Test
    @Transactional
    public void createControleAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controleAtendimentoRepository.findAll().size();

        // Create the ControleAtendimento with an existing ID
        controleAtendimento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControleAtendimentoMockMvc.perform(post("/api/controle-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleAtendimento)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ControleAtendimento> controleAtendimentoList = controleAtendimentoRepository.findAll();
        assertThat(controleAtendimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllControleAtendimentos() throws Exception {
        // Initialize the database
        controleAtendimentoRepository.saveAndFlush(controleAtendimento);

        // Get all the controleAtendimentoList
        restControleAtendimentoMockMvc.perform(get("/api/controle-atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].queixa").value(hasItem(DEFAULT_QUEIXA.toString())))
            .andExpect(jsonPath("$.[*].encaminhamento").value(hasItem(DEFAULT_ENCAMINHAMENTO.toString())))
            .andExpect(jsonPath("$.[*].vinculo").value(hasItem(DEFAULT_VINCULO.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())));
    }

    @Test
    @Transactional
    public void getControleAtendimento() throws Exception {
        // Initialize the database
        controleAtendimentoRepository.saveAndFlush(controleAtendimento);

        // Get the controleAtendimento
        restControleAtendimentoMockMvc.perform(get("/api/controle-atendimentos/{id}", controleAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(controleAtendimento.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.queixa").value(DEFAULT_QUEIXA.toString()))
            .andExpect(jsonPath("$.encaminhamento").value(DEFAULT_ENCAMINHAMENTO.toString()))
            .andExpect(jsonPath("$.vinculo").value(DEFAULT_VINCULO.toString()))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.dataAlteracao").value(DEFAULT_DATA_ALTERACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingControleAtendimento() throws Exception {
        // Get the controleAtendimento
        restControleAtendimentoMockMvc.perform(get("/api/controle-atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControleAtendimento() throws Exception {
        // Initialize the database
        controleAtendimentoRepository.saveAndFlush(controleAtendimento);
        controleAtendimentoSearchRepository.save(controleAtendimento);
        int databaseSizeBeforeUpdate = controleAtendimentoRepository.findAll().size();

        // Update the controleAtendimento
        ControleAtendimento updatedControleAtendimento = controleAtendimentoRepository.findOne(controleAtendimento.getId());
        updatedControleAtendimento
            .numero(UPDATED_NUMERO)
            .queixa(UPDATED_QUEIXA)
            .encaminhamento(UPDATED_ENCAMINHAMENTO)
            .vinculo(UPDATED_VINCULO)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .dataAlteracao(UPDATED_DATA_ALTERACAO);

        restControleAtendimentoMockMvc.perform(put("/api/controle-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedControleAtendimento)))
            .andExpect(status().isOk());

        // Validate the ControleAtendimento in the database
        List<ControleAtendimento> controleAtendimentoList = controleAtendimentoRepository.findAll();
        assertThat(controleAtendimentoList).hasSize(databaseSizeBeforeUpdate);
        ControleAtendimento testControleAtendimento = controleAtendimentoList.get(controleAtendimentoList.size() - 1);
        assertThat(testControleAtendimento.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testControleAtendimento.getQueixa()).isEqualTo(UPDATED_QUEIXA);
        assertThat(testControleAtendimento.getEncaminhamento()).isEqualTo(UPDATED_ENCAMINHAMENTO);
        assertThat(testControleAtendimento.getVinculo()).isEqualTo(UPDATED_VINCULO);
        assertThat(testControleAtendimento.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testControleAtendimento.getDataAlteracao()).isEqualTo(UPDATED_DATA_ALTERACAO);

        // Validate the ControleAtendimento in Elasticsearch
        ControleAtendimento controleAtendimentoEs = controleAtendimentoSearchRepository.findOne(testControleAtendimento.getId());
        assertThat(controleAtendimentoEs).isEqualToComparingFieldByField(testControleAtendimento);
    }

    @Test
    @Transactional
    public void updateNonExistingControleAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = controleAtendimentoRepository.findAll().size();

        // Create the ControleAtendimento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restControleAtendimentoMockMvc.perform(put("/api/controle-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(controleAtendimento)))
            .andExpect(status().isCreated());

        // Validate the ControleAtendimento in the database
        List<ControleAtendimento> controleAtendimentoList = controleAtendimentoRepository.findAll();
        assertThat(controleAtendimentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteControleAtendimento() throws Exception {
        // Initialize the database
        controleAtendimentoRepository.saveAndFlush(controleAtendimento);
        controleAtendimentoSearchRepository.save(controleAtendimento);
        int databaseSizeBeforeDelete = controleAtendimentoRepository.findAll().size();

        // Get the controleAtendimento
        restControleAtendimentoMockMvc.perform(delete("/api/controle-atendimentos/{id}", controleAtendimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean controleAtendimentoExistsInEs = controleAtendimentoSearchRepository.exists(controleAtendimento.getId());
        assertThat(controleAtendimentoExistsInEs).isFalse();

        // Validate the database is empty
        List<ControleAtendimento> controleAtendimentoList = controleAtendimentoRepository.findAll();
        assertThat(controleAtendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchControleAtendimento() throws Exception {
        // Initialize the database
        controleAtendimentoRepository.saveAndFlush(controleAtendimento);
        controleAtendimentoSearchRepository.save(controleAtendimento);

        // Search the controleAtendimento
        restControleAtendimentoMockMvc.perform(get("/api/_search/controle-atendimentos?query=id:" + controleAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controleAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].queixa").value(hasItem(DEFAULT_QUEIXA.toString())))
            .andExpect(jsonPath("$.[*].encaminhamento").value(hasItem(DEFAULT_ENCAMINHAMENTO.toString())))
            .andExpect(jsonPath("$.[*].vinculo").value(hasItem(DEFAULT_VINCULO.toString())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].dataAlteracao").value(hasItem(DEFAULT_DATA_ALTERACAO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControleAtendimento.class);
        ControleAtendimento controleAtendimento1 = new ControleAtendimento();
        controleAtendimento1.setId(1L);
        ControleAtendimento controleAtendimento2 = new ControleAtendimento();
        controleAtendimento2.setId(controleAtendimento1.getId());
        assertThat(controleAtendimento1).isEqualTo(controleAtendimento2);
        controleAtendimento2.setId(2L);
        assertThat(controleAtendimento1).isNotEqualTo(controleAtendimento2);
        controleAtendimento1.setId(null);
        assertThat(controleAtendimento1).isNotEqualTo(controleAtendimento2);
    }
}
