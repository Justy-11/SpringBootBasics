package com.jathursh.sb.service;

import com.jathursh.sb.dto.PokemonDto;
import com.jathursh.sb.model.Pokemon;
import com.jathursh.sb.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService{

    @Autowired
    private PokemonRepository pokemonRepository;

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto){
        // map from dto to entity (model here)
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        // id is auto generated for Pokemon

        Pokemon newPokemon = pokemonRepository.save(pokemon);

        PokemonDto pokemonResponse = new PokemonDto();
        pokemonResponse.setId(newPokemon.getId());
        pokemonResponse.setName(newPokemon.getName());
        pokemonResponse.setType(newPokemon.getType());
        return pokemonResponse;
    }

    @Override
    public List<PokemonDto> getAllPokemon() {
        List<Pokemon> pokemon = pokemonRepository.findAll();

        // using mapper method - don't want the "type" from model to dto
        return pokemon.stream().map(p-> mapToDto(p)).collect(Collectors.toList());
    }

    private PokemonDto mapToDto(Pokemon pokemon) {
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
}
