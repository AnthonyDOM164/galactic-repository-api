package com.tournament.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for species registration and updates.
 * <p>
 * Used to decouple the API's external interface from the internal database structure, ensuring only required fields are exposed.
 * </p>
 *
 * @author Anthony Ortega
 * @version 1.0
 */
@Data
public class SpeciesDTO {

    @Schema(example = "Human", description = "Unique name of the species")
    @NotBlank
    private String name;

    @Schema(example = "85", description = "Base power level (minimum 1)")
    @Min(1)
    private Integer powerLevel;

    @Schema(example = "Super strength", description = "The species' unique characteristic")
    private String specialAbility;
}
