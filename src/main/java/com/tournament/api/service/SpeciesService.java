package com.tournament.api.service;

import com.tournament.api.dto.SpeciesDTO;
import com.tournament.api.exception.SpeciesAlreadyExistsException;
import com.tournament.api.model.Species;
import com.tournament.api.repository.SpeciesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Service class that manages the core business logic for the Galactic Tournament.
 * <p>
 * This class handles species registration, tournament rankings, and the implementation of combat rules, including tie-breaking logic.
 * </p>
 *
 * @author Anthony Ortega
 * @version 1.0
 */
@Service
public class SpeciesService {

    @Autowired
    private SpeciesRepository speciesRepository;

    /**
     * Registers a new species in the tournament system.
     *
     * @param speciesDTO The data transfer object containing species details.
     * @return The persisted {@link Species} entity.
     * @throws SpeciesAlreadyExistsException If a species with the same name already exists (case-insensitive).
     */
    public Species registerSpecies(SpeciesDTO speciesDTO) {
        if (speciesRepository.existsByNameIgnoreCase(speciesDTO.getName())) {
            throw new SpeciesAlreadyExistsException("The species name '" + speciesDTO.getName() + "' is already taken.");
        }

        Species species = new Species();
        species.setName(speciesDTO.getName());
        species.setPowerLevel(speciesDTO.getPowerLevel());
        species.setSpecialAbility(speciesDTO.getSpecialAbility());
        return speciesRepository.save(species);
    }

    /**
     * Retrieves all registered species.
     *
     * @return A list of all {@link Species}.
     */
    public List<Species> getAll() {
        return speciesRepository.findAll();
    }

    /**
     * Executes a battle between two species and determines a winner.
     * <p>
     * <b>Rules:</b>
     * <ul>
     * <li>The species with the higher power level wins.</li>
     * <li>In case of a tie, the species whose name comes first alphabetically wins.</li>
     * </ul>
     * The winner's victory count is incremented by one.
     * </p>
     *
     * @param id1 ID of the first contender.
     * @param id2 ID of the second contender.
     * @return The winning {@link Species}.
     * @throws java.util.NoSuchElementException If any of the IDs do not exist in the database.
     */
    @Transactional
    public Species battle(Long id1, Long id2) {
        Species species1 = speciesRepository.findById(id1).orElseThrow();
        Species species2 = speciesRepository.findById(id2).orElseThrow();

        Species winner;
        if (species1.getPowerLevel() > species2.getPowerLevel()) {
            winner = species1;
        } else if (species2.getPowerLevel() > species1.getPowerLevel()) {
            winner = species2;
        } else {
            winner = (species1.getName().compareToIgnoreCase(species2.getName()) > 0 ? species2 : species1);
        }

        winner.setVictories(winner.getVictories() + 1);
        speciesRepository.save(winner);
        return winner;
    }

    /**
     * Retrieves the tournament leaderboard.
     *
     * @return A list of {@link Species} ordered by total victories in descending order.
     */
    public List<Species> getRanking(){
        return speciesRepository.findAllByOrderByVictoriesDesc();
    }

    /**
     * Selects two distinct species at random and simulates a battle between them.
     *
     * @return The winner of the random battle.
     * @throws RuntimeException If there are fewer than 2 species registered in the tournament.
     */
    public Species randomBattle(){
        List<Species> allSpecies = getAll();
        if(allSpecies.size() < 2){
            throw new RuntimeException("Not enough species to start a battle. At least 2 are required.");
        }

        Random random = new Random();
        int index1 = random.nextInt(allSpecies.size());
        int index2;

        do{
            index2 = random.nextInt(allSpecies.size());
        } while(index1 == index2);

        Species species1 = allSpecies.get(index1);
        Species species2 = allSpecies.get(index2);

        return battle(species1.getId(), species2.getId());
    }

}
