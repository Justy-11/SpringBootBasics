package com.jathursh.sb.service;

import com.jathursh.sb.dto.PokemonDto;
import com.jathursh.sb.dto.PokemonResponse;
import com.jathursh.sb.model.Pokemon;
import com.jathursh.sb.repository.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PokemonServiceImplTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;


    @Test
    void createPokemon() {
        PokemonDto pokemonDto = PokemonDto.builder()
                .name("testPokemon")
                .type("testType")
                .build();

        Pokemon pokemon = Pokemon.builder()
                .name("testPokemon")
                .type("testType")
                .build();

        // Pokemon newPokemon = pokemonRepository.save(pokemon); mock this ->
        // for any Pokemon object need to return the created pokemon object
        Mockito.when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemon = pokemonService.createPokemon(pokemonDto);

        assertNotNull(savedPokemon);
        assertEquals("testPokemon",savedPokemon.getName());  // not necessary
        assertEquals( "testType",savedPokemon.getType());  // not necessary
    }

    @Test
    void getAllPokemon() {

        Pokemon pokemon1 = Pokemon.builder()
                .name("testPokemon")
                .type("testType")
                .build();
        Pokemon pokemon2 = Pokemon.builder()
                .name("testPokemon2")
                .type("testType2")
                .build();

        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.add(pokemon1);
        pokemonList.add(pokemon2);

        //List<Pokemon> pokemon = pokemonRepository.findAll(); mock this
        Mockito.when(pokemonRepository.findAll()).thenReturn(pokemonList);

        List<PokemonDto> pokemonDtoList = pokemonService.getAllPokemon();

        assertNotNull(pokemonDtoList);
    }

    @Test
    void getPokemonById() {

        Optional<Pokemon> optionalPokemon = Optional.ofNullable(Pokemon.builder()
                .name("testPokemon")
                .type("testType")
                .build());

        Mockito.when(pokemonRepository.findById(1)).thenReturn(optionalPokemon);

        PokemonDto pokemonDto = pokemonService.getPokemonById(1);

        assertNotNull(pokemonDto);

    }

    @Test
    void updatePokemon() {
        Pokemon pokemon = Pokemon.builder()
                .name("testPokemon")
                .type("testType")
                .build();

        PokemonDto pokemonDto = PokemonDto.builder()
                .name("testPokemon")
                .type("testType")
                .build();

        Mockito.when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));
        Mockito.when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto pokemonDto1 = pokemonService.updatePokemon(pokemonDto, 1);

        assertNotNull(pokemonDto1);

    }

    @Test
    void deletePokemonById() {
        Pokemon pokemon = Pokemon.builder()
                .name("testPokemon")
                .type("testType")
                .build();

        Mockito.when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));

        //In this code snippet, it is expected that the pokemonService.deletePokemonById()
        // method will delete the Pokemon with an ID of 1. The assertAll() method is used to wrap the assertion,
        // which allows multiple assertions to be made and will collect and report all failures instead of stopping
        // at the first failure. In this case, since there is only one assertion, it may not be necessary to use assertAll(),
        // but it can be useful when multiple assertions need to be made.
        assertAll(()-> pokemonService.deletePokemonById(1));
    }

    @Test
    void getAllPokemonWithPagination() {

        Page<Pokemon> pokemonPage = Mockito.mock(Page.class);

        // Mock Page<Pokemon> pokemonPage = pokemonRepository.findAll(pageRequest);
        Mockito.when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemonPage);

        PokemonResponse pokemonResponse = pokemonService.getAllPokemonWithPagination(1,10);

        assertNotNull(pokemonResponse);

    }
}