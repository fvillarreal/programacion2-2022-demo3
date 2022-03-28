package ar.edu.um.progranacion2.demo2.repository;

import ar.edu.um.progranacion2.demo2.domain.Persona;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Persona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {}
