package com.jathursh.sb.repository;

import com.jathursh.sb.model.Pokemon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  //used to test Spring Data JPA repositories. When this annotation is used, it sets up an in-memory database and configures the necessary Spring components to test the data layer of a Spring application.
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon(){

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

    @Test
    public void PokemonRepository_GetAll_ReturnMoreThanOnePokemon(){

        // Arrange
        Pokemon pokemon1 = Pokemon.builder()
                .name("pokemonTest1")
                .type("typeTest1").build();

        Pokemon pokemon2 = Pokemon.builder()
                .name("pokemonTest2")
                .type("typeTest2").build();

        pokemonRepository.save(pokemon1);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        // Assert
        assertNotNull(pokemonList);
        assertEquals(pokemonList.size(), 2);

    }

    @Test
    public void PokemonRepository_FindById_ReturnPokemon(){

        // Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pokemonTest")
                .type("typeTest").build();

        pokemonRepository.save(pokemon);

        Pokemon pokemon1 = pokemonRepository.findById(pokemon.getId()).get();

        // Assert
        assertNotNull(pokemon1);

    }
}