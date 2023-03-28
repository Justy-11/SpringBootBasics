package com.jathursh.sb.service;

import com.jathursh.sb.dto.PokemonDto;

import java.util.List;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);
    List<PokemonDto> getAllPokemon();
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemonById(int id);
    List<PokemonDto> getAllPokemonWithPagination(int pageNo, int pageSize);
}
