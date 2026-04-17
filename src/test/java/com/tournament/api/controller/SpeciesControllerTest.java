package com.tournament.api.controller;

import com.tournament.api.dto.SpeciesDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpeciesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSpeciesShouldReturn200() throws Exception {
        SpeciesDTO speciesDTO = new SpeciesDTO();
        speciesDTO.setName("Robot");
        speciesDTO.setPowerLevel(60);
        speciesDTO.setSpecialAbility("Super strength");

        mockMvc.perform(post("/api/species")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(speciesDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Robot"));
    }

    @Test
    void createSpeciesShouldReturn400WhenNameIsEmpty() throws Exception {
        SpeciesDTO speciesDTO = new SpeciesDTO();
        speciesDTO.setPowerLevel(60);

        mockMvc.perform(post("/api/species")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(speciesDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createSpeciesShouldReturn400WhenNameIsDuplicate() throws Exception {
        SpeciesDTO firstSpecies = new SpeciesDTO();
        firstSpecies.setName("Human");
        firstSpecies.setPowerLevel(90);

        mockMvc.perform(post("/api/species")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(firstSpecies)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/species")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(firstSpecies)))
                .andExpect(status().isBadRequest());
    }

}
