package com.jathursh.sb.service;

import com.jathursh.sb.dto.PokemonDto;

import java.util.List;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);
    List<PokemonDto> getAllPokemon();
}
