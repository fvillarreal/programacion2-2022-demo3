package ar.edu.um.progranacion2.demo2.repository;

import ar.edu.um.progranacion2.demo2.domain.JEmpleado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the JEmpleado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JEmpleadoRepository extends JpaRepository<JEmpleado, Long> {}
