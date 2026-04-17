package com.tournament.api.controller;

import com.tournament.api.dto.SpeciesDTO;
import com.tournament.api.model.Species;
import com.tournament.api.service.SpeciesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller that handles the administrative and competitive operations of the Galactic Tournament.
 * <p>
 * This controller provides endpoints for species registration, data retrieval, and combat simulation logic.
 * </p>
 *
 * @author Anthony Ortega
 * @version 1.0
 */
@Tag(name = "Galactic Tournament", description = "Endpoints for managing species and battle simulations")
@RestController
@RequestMapping("/api/species")
@CrossOrigin(origins = "*")
public class SpeciesController {

    @Autowired
    private SpeciesService speciesService;

    /**
     * Registers a new species into the tournament database.
     *
     * @param speciesDTO Data Transfer Object containing the name, power level, and special ability.
     * @return A {@link ResponseEntity} containing the newly created {@link Species}.
     */
    @Operation(summary = "Register new species", description = "Creates a new species. Names must be unique.")
    @PostMapping
    public ResponseEntity<Species> create(@Valid @RequestBody SpeciesDTO speciesDTO) {
        return ResponseEntity.ok(speciesService.registerSpecies(speciesDTO));
    }

    /**
     * Retrieves all species currently registered in the tournament.
     *
     * @return A {@link ResponseEntity} with a {@link List} of all {@link Species}.
     */
    @Operation(summary = "Get all species", description = "Retrieves a list of all registered species in the tournament.")
    @GetMapping
    public ResponseEntity<List<Species>> list() {
        return ResponseEntity.ok(speciesService.getAll());
    }

    /**
     * Initiates a duel between two specific species based on their unique identifiers.
     *
     * @param id1 The ID of the first contender.
     * @param id2 The ID of the second contender.
     * @return A {@link ResponseEntity} containing the winning {@link Species}.
     */
    @Operation(summary = "Execute battle by IDs", description = "Starts a fight between two species. If power levels tie, alphabetical order decides the winner.")
    @PostMapping("/battle")
    public ResponseEntity<Species> battle(@RequestParam Long id1, @RequestParam Long id2) {
        return ResponseEntity.ok(speciesService.battle(id1, id2));
    }

    /**
     * Provides the current leaderboard of the tournament.
     *
     * @return A {@link ResponseEntity} with a {@link List} of {@link Species} ordered by victories in descending order.
     */
    @Operation(summary = "Get leaderboard", description = "Returns the species ranked by their total number of victories.")
    @GetMapping("/ranking")
    public ResponseEntity<List<Species>> ranking() {
        return ResponseEntity.ok(speciesService.getRanking());
    }

    /**
     * Automatically selects two random contenders and simulates a battle between them.
     *
     * @return A {@link ResponseEntity} containing the winner of the random encounter.
     * @throws RuntimeException if there are fewer than two species in the database.
     */
    @Operation(summary = "Execute random battle", description = "Randomly selects two species from the database to engage in combat.")
    @PostMapping("/battle/randomBattle")
    public ResponseEntity<Species> randomBattle() {
        return ResponseEntity.ok(speciesService.randomBattle());
    }

}
