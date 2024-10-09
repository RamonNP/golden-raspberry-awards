package com.prova.golden.raspberry.awards.service;

import com.prova.golden.raspberry.awards.model.Movie;
import com.prova.golden.raspberry.awards.model.Producer;
import com.prova.golden.raspberry.awards.repository.MovieRepository;
import com.prova.golden.raspberry.awards.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void loadMoviesFromCSV() {
        try (InputStream inputStream = getClass().getResourceAsStream("/static/movielist.csv")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Arquivo não encontrado: classpath:static/movielist.csv");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                CSVParser csvParser = CSVFormat.DEFAULT
                        .withDelimiter(';') // Defina o delimitador usado no CSV (por exemplo, ';')
                        .withFirstRecordAsHeader() // Ignora a primeira linha, considerando-a como cabeçalho
                        .parse(reader);

                List<Movie> movies = new ArrayList<>();
                for (CSVRecord record : csvParser) {
                    saveMovie(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Aqui você pode usar um logger para logar a exceção
        }
    }

    private void saveMovie(CSVRecord record) {

        // Criar e associar produtores ao Movie
        List<Producer> producers = findOrCreateProducers(record.get("producers"));
        for (Producer producer : producers) {
            // Criar o Movie
            Movie movie = new Movie(
                    null, // ID será gerado automaticamente (assumindo que seja auto incrementado)
                    Integer.parseInt(record.get("year")),
                    record.get("title"),
                    record.get("studios"),
                    "yes".equalsIgnoreCase(record.get("winner")),
                    producer

            );
            Movie savedMovie = movieRepository.save(movie);
        }

    }

    private List<Producer> findOrCreateProducers(String producersString) {
        if (producersString == null || producersString.isEmpty()) {
            return new ArrayList<>(); // Retorna uma lista vazia se não houver produtores
        }

        // Divide a string por vírgulas e pela palavra "and"
        String[] producerNames = producersString.split(",|\\band\\b");

        List<Producer> producers = new ArrayList<>();
        for (String name : producerNames) {
            String trimmedName = name.trim(); // Remove espaços em branco
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
