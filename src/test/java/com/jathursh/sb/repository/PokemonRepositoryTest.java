package com.jathursh.sb.repository;

import com.jathursh.sb.model.Pokemon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  //used to test Spring Data JPA repositories. When this annotation is used, it sets up an in-memory database and configures the necessary Spring components to test the data layer of a Spring application.
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_saveAll_ReturnSavedPokemon(){

        // Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pokemonTest")
                .type("typeTest").build();  //used builder given by mentioning Pokemon class with @Builder annotation

        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Assert
        assertNotNull(savedPokemon);
        assertTrue(savedPokemon.getId() > 0, "saved pokemon id is greater than 0");
    }
}