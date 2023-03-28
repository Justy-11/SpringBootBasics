package com.jathursh.sb.service;

import com.jathursh.sb.dto.PokemonDto;
import com.jathursh.sb.exception.PokemonNotFoundException;
import com.jathursh.sb.model.Pokemon;
import com.jathursh.sb.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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

    @Override
    public PokemonDto getPokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon could not be found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("pokemon could not be found"));
        pokemon.setType(pokemonDto.getType());
        pokemon.setName(pokemonDto.getName());

        Pokemon updatePokemon = pokemonRepository.save(pokemon);
        return mapToDto(updatePokemon);
    }

    @Override
    public void deletePokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("pokemon could not be deleted"));
        pokemonRepository.delete(pokemon);
    }

    @Override
    public List<PokemonDto> getAllPokemonWithPagination(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemonPage = pokemonRepository.findAll(pageRequest);
        List<Pokemon> pokemon = pokemonPage.getContent();
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
