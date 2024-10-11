package com.prova.golden.raspberry.awards;


import com.prova.golden.raspberry.awards.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MovieDataIntegrationTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void shouldLoadDataFromCSVOnStartup() {
        // Verificar se a quantidade de registros corresponde ao esperado
        long count = movieRepository.count();
        assertTrue(count == 472, "Deve haver registros inseridos a partir do CSV");
    }
}
