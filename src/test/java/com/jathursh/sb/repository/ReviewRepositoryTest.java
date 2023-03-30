package com.jathursh.sb.repository;

import com.jathursh.sb.model.Pokemon;
import com.jathursh.sb.model.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview(){

        // Arrange
        Review review = Review.builder()
                .content("testContent")
                .title("testTitle")
                .stars(5)
                .build();

        // Act
        Review savedReview = reviewRepository.save(review);

        // Assert
        assertNotNull(savedReview);
        assertTrue(savedReview.getId() > 0, "saved review id is greater than 0");
    }

    @Test
    public void ReviewRepository_GetAll_ReturnMoreThanOneReview(){

        Review review1 = Review.builder()
                .content("testContent")
                .title("testTitle")
                .stars(5)
                .build();
        Review review2 = Review.builder()
                .content("testContent2")
                .title("testTitle2")
                .stars(4)
                .build();


        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();

        // Assert
        assertNotNull(reviewList);
        assertEquals(reviewList.size(), 2);

    }

    @Test
    public void ReviewRepository_FindById_ReturnReview(){

        Review review = Review.builder()
                .content("testContent")
                .title("testTitle")
                .stars(5)
                .build();

        reviewRepository.save(review);

        Review reviewIdentified = reviewRepository.findById(review.getId()).get();

        // Assert
        assertNotNull(reviewIdentified);
    }


    @Test
    public void ReviewRepository_UpdateReview_ReturnReview(){

        Review review = Review.builder()
                .content("testContent")
                .title("testTitle")
                .stars(5)
                .build();

        reviewRepository.save(review);

        Review reviewIdentified = reviewRepository.findById(review.getId()).get();
        reviewIdentified.setContent("testContent_updated");
        reviewIdentified.setTitle("testTitle_updated");
        reviewIdentified.setStars(3);

        Review reviewUpdated = reviewRepository.save(reviewIdentified);

        // Assert
        assertNotNull(reviewUpdated.getTitle());
        assertNotNull(reviewUpdated.getContent());
        assertNotNull(reviewUpdated.getContent());
    }

    @Test
    public void PokemonRepository_DeletePokemon_ReturnPokemonIsEmpty() {

        Review review = Review.builder()
                .content("testContent")
                .title("testTitle")
                .stars(5)
                .build();

        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());

        Optional<Review> reviewResponse = reviewRepository.findById(review.getId());

        assertTrue(reviewResponse.isEmpty());

    }
}