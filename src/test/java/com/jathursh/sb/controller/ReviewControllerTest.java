package com.jathursh.sb.controller;

import com.jathursh.sb.dto.PokemonDto;
import com.jathursh.sb.dto.ReviewDto;
import com.jathursh.sb.model.Pokemon;
import com.jathursh.sb.model.Review;
import com.jathursh.sb.service.PokemonService;
import com.jathursh.sb.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

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
    void createReview() {
        Mockito.when(reviewService.createReview(pokemon.getId(), reviewDto)).thenReturn(reviewDto);

        ResponseEntity<ReviewDto> responseEntity = reviewController.createReview(pokemon.getId(), reviewDto);

        assertNotEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity);
        assertEquals(pokemon.getId(), responseEntity.getBody().getId());
    }

    @Test
    void getReviews() {
        Mockito.when(reviewService.getReviewByPokemonId(pokemon.getId())).thenReturn(List.of(reviewDto));

        List<ReviewDto> reviewDtoList = reviewController.getReviews(pokemon.getId());

        assertNotNull(reviewDtoList);
    }

    @Test
    void getReviewById() {
        Mockito.when(reviewService.getReviewById(pokemon.getId(), review.getId())).thenReturn(reviewDto);
        ResponseEntity<ReviewDto> responseEntity = reviewController.getReviewById(pokemon.getId(), review.getId());
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void updateReview() {

        Mockito.when(reviewService.updateReview(pokemon.getId(), review.getId(), reviewDto)).thenReturn(reviewDto);
        ResponseEntity<ReviewDto> responseEntity = reviewController.updateReview(pokemon.getId(), review.getId(), reviewDto);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void deleteReview() {
        Mockito.doNothing().when(reviewService).deleteReview(pokemon.getId(), review.getId());
        ResponseEntity<String> responseEntity = reviewController.deleteReview(pokemon.getId(), review.getId());
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("review deleted!!", responseEntity.getBody());
    }
}