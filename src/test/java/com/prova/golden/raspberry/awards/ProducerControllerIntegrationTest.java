package com.prova.golden.raspberry.awards;

import com.prova.golden.raspberry.awards.dto.ProducerIntervalDTO;
import com.prova.golden.raspberry.awards.model.Movie;
import com.prova.golden.raspberry.awards.model.Producer;
import com.prova.golden.raspberry.awards.repository.MovieRepository;
import com.prova.golden.raspberry.awards.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProducerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;

    @Test
    public void shouldReturnProducerIntervals() throws Exception {
        List<Movie> movies = movieService.getMovies();

        // Aqui aplicamos a lógica para encontrar o produtor com o maior intervalo
        List<ProducerIntervalDTO> producerIntervals = findProducerIntervals(movies);

        // Filtra para encontrar o intervalo máximo e mínimo
        Optional<ProducerIntervalDTO> minInterval = producerIntervals.stream().min(Comparator.comparingInt(ProducerIntervalDTO::getInterval));
        Optional<ProducerIntervalDTO> maxInterval = producerIntervals.stream().max(Comparator.comparingInt(ProducerIntervalDTO::getInterval));

        mockMvc.perform(get("/producers/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.max").isArray())

                // Validação dos resultados de "min"
                .andExpect(jsonPath("$.min[0].producer").value(minInterval.map(ProducerIntervalDTO::getProducer).orElse("Unknown")))
                .andExpect(jsonPath("$.min[0].interval").value(minInterval.map(ProducerIntervalDTO::getInterval).orElse(0)))
                .andExpect(jsonPath("$.min[0].previousWin").value(minInterval.map(ProducerIntervalDTO::getPreviousWin).orElse(0)))
                .andExpect(jsonPath("$.min[0].followingWin").value(minInterval.map(ProducerIntervalDTO::getFollowingWin).orElse(0)))

                // Validação dos resultados de "max"
                .andExpect(jsonPath("$.max[0].producer").value(maxInterval.map(ProducerIntervalDTO::getProducer).orElse("Unknown")))
                .andExpect(jsonPath("$.max[0].interval").value(maxInterval.map(ProducerIntervalDTO::getInterval).orElse(0)))
                .andExpect(jsonPath("$.max[0].previousWin").value(maxInterval.map(ProducerIntervalDTO::getPreviousWin).orElse(0)))
                .andExpect(jsonPath("$.max[0].followingWin").value(maxInterval.map(ProducerIntervalDTO::getFollowingWin).orElse(0)));
    }

    // Lógica para encontrar os intervalos dos produtores
    private List<ProducerIntervalDTO> findProducerIntervals(List<Movie> movies) {
        return movies.stream()
                .filter(Movie::isWinner)
                .collect(Collectors.groupingBy(Movie::getProducer))
                .entrySet().stream()
                .flatMap(entry -> {
                    Producer producer = entry.getKey();
                    List<Movie> winnerMovies = entry.getValue();

                    List<Integer> winningYears = winnerMovies.stream()
                            .map(Movie::getYear)
                            .sorted()
                            .collect(Collectors.toList());

                    List<ProducerIntervalDTO> intervals = new ArrayList<>();
                    for (int i = 1; i < winningYears.size(); i++) {
                        int previousYear = winningYears.get(i - 1);
                        int currentYear = winningYears.get(i);
                        int interval = currentYear - previousYear;

                        intervals.add(new ProducerIntervalDTO(producer.getName(), interval, previousYear, currentYear));
                    }
                    return intervals.stream();
                })
                .collect(Collectors.toList());
    }
}
