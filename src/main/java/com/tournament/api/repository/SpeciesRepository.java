package com.tournament.api.repository;

import com.tournament.api.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Species} entity.
 * <p>
 * Provides automated query execution for tournament data persistence using Spring Data JPA.
 * </p>
 *
 * @author Anthony Ortega
 * @version 1.0
 */
@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {

    /**
     * Retrieves all species from the database, sorted by their victory count.
     *
     * @return A {@link List} of {@link Species} in descending order of wins.
     */
    List<Species> findAllByOrderByVictoriesDesc();

    /**
     * Checks if a species with a specific name already exists, ignoring case sensitivity.
     *
     * @param name The name to search for.
     * @return {@code true} if a match is found, {@code false} otherwise.
     */
    boolean existsByNameIgnoreCase(String name);
}