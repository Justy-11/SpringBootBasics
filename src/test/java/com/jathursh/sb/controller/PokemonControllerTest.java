package com.jathursh.sb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jathursh.sb.dto.PokemonDto;
import com.jathursh.sb.dto.PokemonResponse;
import com.jathursh.sb.dto.ReviewDto;
import com.jathursh.sb.model.Pokemon;
import com.jathursh.sb.model.Review;
import com.jathursh.sb.service.PokemonService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;

@ExtendWith(MockitoExtension.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private PokemonController pokemonController;

    @Autowired
    private ObjectMapper objectMapper;

    private Pokemon pokemon;
    private Review review;
    private ReviewDto reviewDto;
    private PokemonDto pokemonDto;

    @BeforeEach
    public void init(){
        pokemon = Pokemon.builder()
                .name("testPokemon")
                .type("testType")
                .build();

        pokemonDto = PokemonDto.builder()
                .name("testPokemon")
                .type("testType")
                .build();

        review = Review.builder()
                .stars(4)
                .title("testTitle")
                .content("testContent")
                .build();

        reviewDto = ReviewDto.builder()
                .stars(3)
                .content("testContent")
                .title("testTitle")
                .build();
    }

    @Test
    void getPokemons() {
        PokemonResponse pokemonResponse = PokemonResponse.builder()
                        .content(List.of(pokemonDto))
                        .pageNo(1)
                        .pageSize(10)
                        .build();
        Mockito.when(pokemonService.getAllPokemonWithPagination(1, 10)).thenReturn(pokemonResponse);

        ResponseEntity<PokemonResponse> pokemonResponseResponseEntity = pokemonController.getPokemons(1,10);

        assertNotNull(pokemonResponseResponseEntity);
        assertEquals(HttpStatus.OK, pokemonResponseResponseEntity.getStatusCode());
    }

    @Test
    void getPokemon() {
        Mockito.when(pokemonService.getAllPokemon()).thenReturn(List.of(pokemonDto));
        ResponseEntity<List<PokemonDto>> listResponseEntity = pokemonController.getPokemon();

        assertNotNull(listResponseEntity);
        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
    }

    @Test
    void pokemonDetail() {
        Mockito.when(pokemonService.getPokemonById(pokemon.getId())).thenReturn(pokemonDto);
        ResponseEntity<PokemonDto> responseEntity = pokemonController.pokemonDetail(pokemon.getId());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createPokemon() {
        Mockito.when(pokemonService.createPokemon(pokemonDto)).thenReturn(pokemonDto);
        ResponseEntity<PokemonDto> pokemonDtoResponseEntity =  pokemonController.createPokemon(pokemonDto);

        assertNotNull(pokemonDtoResponseEntity);
        assertEquals(HttpStatus.CREATED ,pokemonDtoResponseEntity.getStatusCode());
    }

    @Test
    void updatePokemon() {
        Mockito.when(pokemonService.updatePokemon(pokemonDto, pokemon.getId())).thenReturn(pokemonDto);
        ResponseEntity<PokemonDto> responseEntity = pokemonController.updatePokemon(pokemonDto, pokemon.getId());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK ,responseEntity.getStatusCode());
    }

    @Test
    void deletePokemon() {

        Mockito.doNothing().when(pokemonService).deletePokemonById(pokemon.getId());

        ResponseEntity<String> responseEntity = pokemonController.deletePokemon(pokemon.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Pokemon deleted", responseEntity.getBody());
        assertNotNull(responseEntity);
    }
}