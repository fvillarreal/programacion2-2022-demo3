package ar.edu.um.progranacion2.demo2.web.rest;

import ar.edu.um.progranacion2.demo2.domain.JEmpleado;
import ar.edu.um.progranacion2.demo2.repository.JEmpleadoRepository;
import ar.edu.um.progranacion2.demo2.service.JEmpleadoService;
import ar.edu.um.progranacion2.demo2.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.edu.um.progranacion2.demo2.domain.JEmpleado}.
 */
@RestController
@RequestMapping("/api")
public class JEmpleadoResource {

    private final Logger log = LoggerFactory.getLogger(JEmpleadoResource.class);

    private static final String ENTITY_NAME = "jEmpleado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JEmpleadoService jEmpleadoService;

    private final JEmpleadoRepository jEmpleadoRepository;

    public JEmpleadoResource(JEmpleadoService jEmpleadoService, JEmpleadoRepository jEmpleadoRepository) {
        this.jEmpleadoService = jEmpleadoService;
        this.jEmpleadoRepository = jEmpleadoRepository;
    }

    /**
     * {@code POST  /j-empleados} : Create a new jEmpleado.
     *
     * @param jEmpleado the jEmpleado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jEmpleado, or with status {@code 400 (Bad Request)} if the jEmpleado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/j-empleados")
    public ResponseEntity<JEmpleado> createJEmpleado(@Valid @RequestBody JEmpleado jEmpleado) throws URISyntaxException {
        log.debug("REST request to save JEmpleado : {}", jEmpleado);
        if (jEmpleado.getId() != null) {
            throw new BadRequestAlertException("A new jEmpleado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JEmpleado result = jEmpleadoService.save(jEmpleado);
        return ResponseEntity
            .created(new URI("/api/j-empleados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /j-empleados/:id} : Updates an existing jEmpleado.
     *
     * @param id the id of the jEmpleado to save.
     * @param jEmpleado the jEmpleado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jEmpleado,
     * or with status {@code 400 (Bad Request)} if the jEmpleado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jEmpleado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/j-empleados/{id}")
    public ResponseEntity<JEmpleado> updateJEmpleado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody JEmpleado jEmpleado
    ) throws URISyntaxException {
        log.debug("REST request to update JEmpleado : {}, {}", id, jEmpleado);
        if (jEmpleado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jEmpleado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jEmpleadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JEmpleado result = jEmpleadoService.save(jEmpleado);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jEmpleado.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /j-empleados/:id} : Partial updates given fields of an existing jEmpleado, field will ignore if it is null
     *
     * @param id the id of the jEmpleado to save.
     * @param jEmpleado the jEmpleado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jEmpleado,
     * or with status {@code 400 (Bad Request)} if the jEmpleado is not valid,
     * or with status {@code 404 (Not Found)} if the jEmpleado is not found,
     * or with status {@code 500 (Internal Server Error)} if the jEmpleado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/j-empleados/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JEmpleado> partialUpdateJEmpleado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody JEmpleado jEmpleado
    ) throws URISyntaxException {
        log.debug("REST request to partial update JEmpleado partially : {}, {}", id, jEmpleado);
        if (jEmpleado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jEmpleado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jEmpleadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JEmpleado> result = jEmpleadoService.partialUpdate(jEmpleado);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jEmpleado.getId().toString())
        );
    }

    /**
     * {@code GET  /j-empleados} : get all the jEmpleados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jEmpleados in body.
     */
    @Transactional
    @GetMapping("/j-empleados")
    public ResponseEntity<List<JEmpleado>> getAllJEmpleados(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JEmpleados");
        Page<JEmpleado> page = jEmpleadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /j-empleados/:id} : get the "id" jEmpleado.
     *
     * @param id the id of the jEmpleado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jEmpleado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/j-empleados/{id}")
    public ResponseEntity<JEmpleado> getJEmpleado(@PathVariable Long id) {
        log.debug("REST request to get JEmpleado : {}", id);
        Optional<JEmpleado> jEmpleado = jEmpleadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jEmpleado);
    }

    /**
     * {@code DELETE  /j-empleados/:id} : delete the "id" jEmpleado.
     *
     * @param id the id of the jEmpleado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/j-empleados/{id}")
    public ResponseEntity<Void> deleteJEmpleado(@PathVariable Long id) {
        log.debug("REST request to delete JEmpleado : {}", id);
        jEmpleadoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
