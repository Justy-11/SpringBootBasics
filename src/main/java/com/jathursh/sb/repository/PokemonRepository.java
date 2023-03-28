package com.jathursh.sb.repository;

import com.jathursh.sb.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {


}
