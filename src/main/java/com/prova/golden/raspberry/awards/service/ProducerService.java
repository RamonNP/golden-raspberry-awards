package com.prova.golden.raspberry.awards.service;

import com.prova.golden.raspberry.awards.dto.ProducerIntervalDTO;
import com.prova.golden.raspberry.awards.dto.ProducerStatsDTO;
import com.prova.golden.raspberry.awards.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final MovieRepository movieRepository;

    public ProducerStatsDTO calculateProducerIntervals() {
        List<Object[]> moviesMax = movieRepository.findMoviesWithMaxInterval();
        List<Object[]> moviesMin = movieRepository.findMoviesWithMinInterval();

        List<ProducerIntervalDTO> maxIntervals = mapToProducerIntervalDTO(moviesMax);
        List<ProducerIntervalDTO> minIntervals = mapToProducerIntervalDTO(moviesMin);

        return new ProducerStatsDTO(minIntervals, maxIntervals);
    }

    private List<ProducerIntervalDTO> mapToProducerIntervalDTO(List<Object[]> movieRecords) {
        return movieRecords.stream()
                .map(record -> new ProducerIntervalDTO(
                        (String) record[3], // Nome do produtor
                        ((Number) record[0]).intValue(), // Intervalo
                        ((Number) record[1]).intValue(), // Vitória anterior
                        ((Number) record[2]).intValue() // Vitória seguinte
                ))
                .collect(Collectors.toList());
    }
}
