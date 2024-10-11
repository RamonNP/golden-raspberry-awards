package com.prova.golden.raspberry.awards.controller;

import com.prova.golden.raspberry.awards.dto.ProducerStatsDTO;
import com.prova.golden.raspberry.awards.exception.ProducerNotFoundException;
import com.prova.golden.raspberry.awards.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producers")
public class ProducerController {

    private final ProducerService producerService;

    @Autowired
    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/intervals")
    public ResponseEntity<ProducerStatsDTO> getProducerIntervals() {
        try {
            ProducerStatsDTO stats = producerService.calculateProducerIntervals();
            return ResponseEntity.ok(stats);
        } catch (ProducerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
