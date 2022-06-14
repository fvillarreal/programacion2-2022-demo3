package ar.edu.um.progranacion2.demo2.service;

import ar.edu.um.progranacion2.demo2.domain.JEmpleado;
import ar.edu.um.progranacion2.demo2.repository.JEmpleadoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JEmpleado}.
 */
@Service
@Transactional
public class JEmpleadoService {

    private final Logger log = LoggerFactory.getLogger(JEmpleadoService.class);

    private final JEmpleadoRepository jEmpleadoRepository;

    public JEmpleadoService(JEmpleadoRepository jEmpleadoRepository) {
        this.jEmpleadoRepository = jEmpleadoRepository;
    }

    /**
     * Save a jEmpleado.
     *
     * @param jEmpleado the entity to save.
     * @return the persisted entity.
     */
    public JEmpleado save(JEmpleado jEmpleado) {
        log.debug("Request to save JEmpleado : {}", jEmpleado);
        return jEmpleadoRepository.save(jEmpleado);
    }

    /**
     * Partially update a jEmpleado.
     *
     * @param jEmpleado the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JEmpleado> partialUpdate(JEmpleado jEmpleado) {
        log.debug("Request to partially update JEmpleado : {}", jEmpleado);

        return jEmpleadoRepository
            .findById(jEmpleado.getId())
            .map(existingJEmpleado -> {
                if (jEmpleado.getNombre() != null) {
                    existingJEmpleado.setNombre(jEmpleado.getNombre());
                }
                if (jEmpleado.getApellido() != null) {
                    existingJEmpleado.setApellido(jEmpleado.getApellido());
                }
                if (jEmpleado.getPuesto() != null) {
                    existingJEmpleado.setPuesto(jEmpleado.getPuesto());
                }

                return existingJEmpleado;
            })
            .map(jEmpleadoRepository::save);
    }

    /**
     * Get all the jEmpleados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JEmpleado> findAll(Pageable pageable) {
        log.debug("Request to get all JEmpleados");
        return jEmpleadoRepository.findAll(pageable);
    }

    /**
     * Get one jEmpleado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JEmpleado> findOne(Long id) {
        log.debug("Request to get JEmpleado : {}", id);
        return jEmpleadoRepository.findById(id);
    }

    /**
     * Delete the jEmpleado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete JEmpleado : {}", id);
        jEmpleadoRepository.deleteById(id);
    }
}
