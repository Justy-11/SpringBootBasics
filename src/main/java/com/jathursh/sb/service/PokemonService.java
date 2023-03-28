package com.jathursh.sb.service;

import com.jathursh.sb.dto.PokemonDto;
import com.jathursh.sb.dto.PokemonResponse;

import java.util.List;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);
    List<PokemonDto> getAllPokemon();
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemonById(int id);
    PokemonResponse getAllPokemonWithPagination(int pageNo, int pageSize);
}
