package ar.edu.um.progranacion2.demo2.repository;

import ar.edu.um.progranacion2.demo2.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
