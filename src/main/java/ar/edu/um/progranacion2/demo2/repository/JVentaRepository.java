package ar.edu.um.progranacion2.demo2.repository;

import ar.edu.um.progranacion2.demo2.domain.JVenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the JVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JVentaRepository extends JpaRepository<JVenta, Long> {

}
