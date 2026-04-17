package com.tournament.api.service;

import com.tournament.api.dto.SpeciesDTO;
import com.tournament.api.exception.SpeciesAlreadyExistsException;
import com.tournament.api.model.Species;
import com.tournament.api.repository.SpeciesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpeciesServiceTest {

    @Mock
    private SpeciesRepository speciesRepository;

    @InjectMocks
    private SpeciesService speciesService;

    @Test
    void executeBattleShouldReturnHigherPowerWinner() {
        Species species1 = new Species(1L, "Human", 50, "Super strength", 0);
        Species species2 = new Species(2L, "Saiyan", 100, "Super Saiyan", 0);

        when(speciesRepository.findById(1L)).thenReturn(Optional.of(species1));
        when(speciesRepository.findById(2L)).thenReturn(Optional.of(species2));
        when(speciesRepository.save(any(Species.class))).thenAnswer(i -> i.getArguments()[0]);

        Species winner = speciesService.battle(1L, 2L);

        assertEquals("Saiyan", winner.getName());
        assertEquals(1, winner.getVictories());
    }

    @Test
    void executeBattleShouldReturnAlphabeticalWinner() {
        Species species1 = new Species(1L, "Saiyan", 80, "Super Saiyan", 0);
        Species species2 = new Species(2L, "Human", 80, "Super strength", 0);

        when(speciesRepository.findById(1L)).thenReturn(Optional.of(species1));
        when(speciesRepository.findById(2L)).thenReturn(Optional.of(species2));
        when(speciesRepository.save(any(Species.class))).thenAnswer(i -> i.getArguments()[0]);

        Species winner = speciesService.battle(1L, 2L);

        assertEquals("Human", winner.getName());
    }

    @Test
    void registerSpeciesShouldThrowExceptionWhenNameAlreadyExists() {
        SpeciesDTO speciesDTO = new SpeciesDTO();
        speciesDTO.setName("Human");
        speciesDTO.setPowerLevel(70);

        when(speciesRepository.existsByNameIgnoreCase("Human")).thenReturn(true);

        SpeciesAlreadyExistsException exception = assertThrows(SpeciesAlreadyExistsException.class, () -> {
            speciesService.registerSpecies(speciesDTO);
        });

        assertTrue(exception.getMessage().contains("The species name '" + speciesDTO.getName() + "' is already taken."));

        verify(speciesRepository, never()).save(any(Species.class));
    }

}
