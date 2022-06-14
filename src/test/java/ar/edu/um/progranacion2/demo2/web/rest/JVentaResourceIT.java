package ar.edu.um.progranacion2.demo2.web.rest;

import static ar.edu.um.progranacion2.demo2.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.edu.um.progranacion2.demo2.IntegrationTest;
import ar.edu.um.progranacion2.demo2.domain.JVenta;
import ar.edu.um.progranacion2.demo2.repository.JVentaRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link JVentaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JVentaResourceIT {

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_PRECIO_VENTA = 1D;
    private static final Double UPDATED_PRECIO_VENTA = 2D;

    private static final String ENTITY_API_URL = "/api/j-ventas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JVentaRepository jVentaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJVentaMockMvc;

    private JVenta jVenta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JVenta createEntity(EntityManager em) {
        JVenta jVenta = new JVenta().fecha(DEFAULT_FECHA).precioVenta(DEFAULT_PRECIO_VENTA);
        return jVenta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JVenta createUpdatedEntity(EntityManager em) {
        JVenta jVenta = new JVenta().fecha(UPDATED_FECHA).precioVenta(UPDATED_PRECIO_VENTA);
        return jVenta;
    }

    @BeforeEach
    public void initTest() {
        jVenta = createEntity(em);
    }

    @Test
    @Transactional
    void createJVenta() throws Exception {
        int databaseSizeBeforeCreate = jVentaRepository.findAll().size();
        // Create the JVenta
        restJVentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jVenta)))
            .andExpect(status().isCreated());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeCreate + 1);
        JVenta testJVenta = jVentaList.get(jVentaList.size() - 1);
        assertThat(testJVenta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testJVenta.getPrecioVenta()).isEqualTo(DEFAULT_PRECIO_VENTA);
    }

    @Test
    @Transactional
    void createJVentaWithExistingId() throws Exception {
        // Create the JVenta with an existing ID
        jVenta.setId(1L);

        int databaseSizeBeforeCreate = jVentaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJVentaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jVenta)))
            .andExpect(status().isBadRequest());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJVentas() throws Exception {
        // Initialize the database
        jVentaRepository.saveAndFlush(jVenta);

        // Get all the jVentaList
        restJVentaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].precioVenta").value(hasItem(DEFAULT_PRECIO_VENTA.doubleValue())));
    }

    @Test
    @Transactional
    void getJVenta() throws Exception {
        // Initialize the database
        jVentaRepository.saveAndFlush(jVenta);

        // Get the jVenta
        restJVentaMockMvc
            .perform(get(ENTITY_API_URL_ID, jVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jVenta.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.precioVenta").value(DEFAULT_PRECIO_VENTA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingJVenta() throws Exception {
        // Get the jVenta
        restJVentaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJVenta() throws Exception {
        // Initialize the database
        jVentaRepository.saveAndFlush(jVenta);

        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();

        // Update the jVenta
        JVenta updatedJVenta = jVentaRepository.findById(jVenta.getId()).get();
        // Disconnect from session so that the updates on updatedJVenta are not directly saved in db
        em.detach(updatedJVenta);
        updatedJVenta.fecha(UPDATED_FECHA).precioVenta(UPDATED_PRECIO_VENTA);

        restJVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedJVenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedJVenta))
            )
            .andExpect(status().isOk());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
        JVenta testJVenta = jVentaList.get(jVentaList.size() - 1);
        assertThat(testJVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testJVenta.getPrecioVenta()).isEqualTo(UPDATED_PRECIO_VENTA);
    }

    @Test
    @Transactional
    void putNonExistingJVenta() throws Exception {
        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();
        jVenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jVenta.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJVenta() throws Exception {
        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();
        jVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJVentaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJVenta() throws Exception {
        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();
        jVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJVentaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(jVenta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJVentaWithPatch() throws Exception {
        // Initialize the database
        jVentaRepository.saveAndFlush(jVenta);

        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();

        // Update the jVenta using partial update
        JVenta partialUpdatedJVenta = new JVenta();
        partialUpdatedJVenta.setId(jVenta.getId());

        partialUpdatedJVenta.precioVenta(UPDATED_PRECIO_VENTA);

        restJVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJVenta))
            )
            .andExpect(status().isOk());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
        JVenta testJVenta = jVentaList.get(jVentaList.size() - 1);
        assertThat(testJVenta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testJVenta.getPrecioVenta()).isEqualTo(UPDATED_PRECIO_VENTA);
    }

    @Test
    @Transactional
    void fullUpdateJVentaWithPatch() throws Exception {
        // Initialize the database
        jVentaRepository.saveAndFlush(jVenta);

        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();

        // Update the jVenta using partial update
        JVenta partialUpdatedJVenta = new JVenta();
        partialUpdatedJVenta.setId(jVenta.getId());

        partialUpdatedJVenta.fecha(UPDATED_FECHA).precioVenta(UPDATED_PRECIO_VENTA);

        restJVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJVenta))
            )
            .andExpect(status().isOk());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
        JVenta testJVenta = jVentaList.get(jVentaList.size() - 1);
        assertThat(testJVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testJVenta.getPrecioVenta()).isEqualTo(UPDATED_PRECIO_VENTA);
    }

    @Test
    @Transactional
    void patchNonExistingJVenta() throws Exception {
        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();
        jVenta.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jVenta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJVenta() throws Exception {
        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();
        jVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJVentaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jVenta))
            )
            .andExpect(status().isBadRequest());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJVenta() throws Exception {
        int databaseSizeBeforeUpdate = jVentaRepository.findAll().size();
        jVenta.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJVentaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(jVenta)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JVenta in the database
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJVenta() throws Exception {
        // Initialize the database
        jVentaRepository.saveAndFlush(jVenta);

        int databaseSizeBeforeDelete = jVentaRepository.findAll().size();

        // Delete the jVenta
        restJVentaMockMvc
            .perform(delete(ENTITY_API_URL_ID, jVenta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JVenta> jVentaList = jVentaRepository.findAll();
        assertThat(jVentaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
