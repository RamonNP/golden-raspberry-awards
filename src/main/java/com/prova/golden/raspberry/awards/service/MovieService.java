package com.prova.golden.raspberry.awards.service;

import com.prova.golden.raspberry.awards.model.Movie;
import com.prova.golden.raspberry.awards.model.Producer;
import com.prova.golden.raspberry.awards.repository.MovieRepository;
import com.prova.golden.raspberry.awards.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;

    @Value("${csv.file.path}")
    private String csvFilePath;

    @EventListener(ContextRefreshedEvent.class)
    public void loadMoviesFromCSV() {
        List<Movie> movies = getMovies();
        for (Movie movie : movies) {
            movieRepository.save(movie);
        }
    }

    public List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream(csvFilePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo não encontrado: classpath:" + csvFilePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withDelimiter(';')
                        .withFirstRecordAsHeader()
                        .parse(reader);

                for (CSVRecord record : csvParser) {
                    movies.addAll(getMovieSplitByProducers(record));
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao ler o arquivo CSV: {}", e.getMessage(), e);
        }
        return movies;
    }

    private List<Movie> getMovieSplitByProducers(CSVRecord record) {
        List<Producer> producers = findOrCreateProducers(record.get("producers"));
        List<Movie> movies = new ArrayList<>();
        for (Producer producer : producers) {
            boolean isWinner = "yes".equalsIgnoreCase(record.get("winner"));
            try {
                int year = Integer.parseInt(record.get("year"));
                String title = record.get("title");
                String studios = record.get("studios");

                Movie movie = new Movie(
                        null,
                        year,
                        title,
                        studios,
                        isWinner,
                        producer
                );
                movies.add(movie);
            } catch (NumberFormatException e) {
                logger.warn("Formato de ano inválido para o registro: {} - ignorando filme", record);
            }
        }

        return movies;
    }

    private List<Producer> findOrCreateProducers(String producersString) {
        if (producersString == null || producersString.isEmpty()) {
            return new ArrayList<>();
        }

        String[] producerNames = producersString.split(",|\\band\\b");

        List<Producer> producers = new ArrayList<>();
        for (String name : producerNames) {
            String trimmedName = name.trim();
            if (!trimmedName.isEmpty()) {
                Producer producer = producerRepository.findByName(trimmedName)
                        .orElseGet(() -> {
                            Producer newProducer = new Producer();
                            newProducer.setName(trimmedName);
                            return producerRepository.save(newProducer);
                        });
                producers.add(producer);
            }
        }
        return producers;
    }
}
