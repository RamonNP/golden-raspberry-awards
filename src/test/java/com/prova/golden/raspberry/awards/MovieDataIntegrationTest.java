package com.prova.golden.raspberry.awards;

import com.prova.golden.raspberry.awards.model.Movie;
import com.prova.golden.raspberry.awards.repository.MovieRepository;
import com.prova.golden.raspberry.awards.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class MovieDataIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @Test
    void shouldLoadDataFromCSVOnStartup() {

        // Verificar se todos os filmes foram inseridos no repositório
        List<Movie> movieRecords = movieRepository.findAll();

        assertEquals(472, movieRecords.size(), "Deve haver 472 registros inseridos a partir do CSV");

        // Verificar cada filme
        for (Movie movie : movieRecords) {
            int year = movie.getYear();
            String title = movie.getTitle();
            assertTrue(
                    movieRepository.existsByYearAndTitle(movie.getYear(), movie.getTitle()),
                    () -> String.format("O filme '%s' de %d deveria estar presente no banco de dados.", movie.getTitle(), movie.getYear())
            );
        }
    }
}
