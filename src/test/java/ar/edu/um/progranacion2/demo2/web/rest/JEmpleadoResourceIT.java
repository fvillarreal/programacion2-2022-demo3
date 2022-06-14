package ar.edu.um.progranacion2.demo2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.progranacion2.demo2.IntegrationTest;
import ar.edu.um.progranacion2.demo2.domain.JEmpleado;
import ar.edu.um.progranacion2.demo2.repository.JEmpleadoRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JEmpleadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JEmpleadoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_PUESTO = "AAAAAAAAAA";
    private static final String UPDATED_PUESTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/j-empleados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JEmpleadoRepository jEmpleadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJEmpleadoMockMvc;

    private JEmpleado jEmpleado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JEmpleado createEntity(EntityManager em) {
        JEmpleado jEmpleado = new JEmpleado().nombre(DEFAULT_NOMBRE).apellido(DEFAULT_APELLIDO).puesto(DEFAULT_PUESTO);
        return jEmpleado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JEmpleado createUpdatedEntity(EntityManager em) {
        JEmpleado jEmpleado = new JEmpleado().nombre(UPDATED_NOMBRE).apellido(UPDATED_APELLIDO).puesto(UPDATED_PUESTO);
        return jEmpleado;
    }

    @BeforeEach
    public void initTest() {
        jEmpleado = createEntity(em);
    }

    @Test
    @Transactional
    void createJEmpleado() throws Exception {
        int databaseSizeBeforeCreate = jEmpleadoRepository.findAll().size();
        // Create the JEmpleado
        restJEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jEmpleado)))
            .andExpect(status().isCreated());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeCreate + 1);
        JEmpleado testJEmpleado = jEmpleadoList.get(jEmpleadoList.size() - 1);
        assertThat(testJEmpleado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJEmpleado.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testJEmpleado.getPuesto()).isEqualTo(DEFAULT_PUESTO);
    }

    @Test
    @Transactional
    void createJEmpleadoWithExistingId() throws Exception {
        // Create the JEmpleado with an existing ID
        jEmpleado.setId(1L);

        int databaseSizeBeforeCreate = jEmpleadoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jEmpleado)))
            .andExpect(status().isBadRequest());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = jEmpleadoRepository.findAll().size();
        // set the field null
        jEmpleado.setNombre(null);

        // Create the JEmpleado, which fails.

        restJEmpleadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jEmpleado)))
            .andExpect(status().isBadRequest());

        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllJEmpleados() throws Exception {
        // Initialize the database
        jEmpleadoRepository.saveAndFlush(jEmpleado);

        // Get all the jEmpleadoList
        restJEmpleadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jEmpleado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].puesto").value(hasItem(DEFAULT_PUESTO)));
    }

    @Test
    @Transactional
    void getJEmpleado() throws Exception {
        // Initialize the database
        jEmpleadoRepository.saveAndFlush(jEmpleado);

        // Get the jEmpleado
        restJEmpleadoMockMvc
            .perform(get(ENTITY_API_URL_ID, jEmpleado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jEmpleado.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
            .andExpect(jsonPath("$.puesto").value(DEFAULT_PUESTO));
    }

    @Test
    @Transactional
    void getNonExistingJEmpleado() throws Exception {
        // Get the jEmpleado
        restJEmpleadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJEmpleado() throws Exception {
        // Initialize the database
        jEmpleadoRepository.saveAndFlush(jEmpleado);

        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();

        // Update the jEmpleado
        JEmpleado updatedJEmpleado = jEmpleadoRepository.findById(jEmpleado.getId()).get();
        // Disconnect from session so that the updates on updatedJEmpleado are not directly saved in db
        em.detach(updatedJEmpleado);
        updatedJEmpleado.nombre(UPDATED_NOMBRE).apellido(UPDATED_APELLIDO).puesto(UPDATED_PUESTO);

        restJEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJEmpleado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
        JEmpleado testJEmpleado = jEmpleadoList.get(jEmpleadoList.size() - 1);
        assertThat(testJEmpleado.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJEmpleado.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testJEmpleado.getPuesto()).isEqualTo(UPDATED_PUESTO);
    }

    @Test
    @Transactional
    void putNonExistingJEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();
        jEmpleado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jEmpleado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jEmpleado))
            )
            .andExpect(status().isBadRequest());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();
        jEmpleado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJEmpleadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jEmpleado))
            )
            .andExpect(status().isBadRequest());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();
        jEmpleado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJEmpleadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jEmpleado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJEmpleadoWithPatch() throws Exception {
        // Initialize the database
        jEmpleadoRepository.saveAndFlush(jEmpleado);

        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();

        // Update the jEmpleado using partial update
        JEmpleado partialUpdatedJEmpleado = new JEmpleado();
        partialUpdatedJEmpleado.setId(jEmpleado.getId());

        partialUpdatedJEmpleado.apellido(UPDATED_APELLIDO).puesto(UPDATED_PUESTO);

        restJEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
        JEmpleado testJEmpleado = jEmpleadoList.get(jEmpleadoList.size() - 1);
        assertThat(testJEmpleado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJEmpleado.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testJEmpleado.getPuesto()).isEqualTo(UPDATED_PUESTO);
    }

    @Test
    @Transactional
    void fullUpdateJEmpleadoWithPatch() throws Exception {
        // Initialize the database
        jEmpleadoRepository.saveAndFlush(jEmpleado);

        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();

        // Update the jEmpleado using partial update
        JEmpleado partialUpdatedJEmpleado = new JEmpleado();
        partialUpdatedJEmpleado.setId(jEmpleado.getId());

        partialUpdatedJEmpleado.nombre(UPDATED_NOMBRE).apellido(UPDATED_APELLIDO).puesto(UPDATED_PUESTO);

        restJEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJEmpleado))
            )
            .andExpect(status().isOk());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
        JEmpleado testJEmpleado = jEmpleadoList.get(jEmpleadoList.size() - 1);
        assertThat(testJEmpleado.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJEmpleado.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testJEmpleado.getPuesto()).isEqualTo(UPDATED_PUESTO);
    }

    @Test
    @Transactional
    void patchNonExistingJEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();
        jEmpleado.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jEmpleado.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jEmpleado))
            )
            .andExpect(status().isBadRequest());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();
        jEmpleado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jEmpleado))
            )
            .andExpect(status().isBadRequest());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = jEmpleadoRepository.findAll().size();
        jEmpleado.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJEmpleadoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jEmpleado))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JEmpleado in the database
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJEmpleado() throws Exception {
        // Initialize the database
        jEmpleadoRepository.saveAndFlush(jEmpleado);

        int databaseSizeBeforeDelete = jEmpleadoRepository.findAll().size();

        // Delete the jEmpleado
        restJEmpleadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, jEmpleado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JEmpleado> jEmpleadoList = jEmpleadoRepository.findAll();
        assertThat(jEmpleadoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
