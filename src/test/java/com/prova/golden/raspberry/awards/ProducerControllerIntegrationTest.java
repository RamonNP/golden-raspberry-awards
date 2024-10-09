package com.prova.golden.raspberry.awards;

//import com.prova.golden.raspberry.awards.model.ProducerStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProducerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
/*
    @Test
    public void testGetProducerIntervals() {
        // Realiza a chamada ao endpoint
        ResponseEntity<ProducerStats> response = restTemplate.getForEntity("/producers/intervals", ProducerStats.class);

        // Verifica se a resposta é 200 OK
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verifica se o corpo da resposta não é nulo
        assertThat(response.getBody()).isNotNull();

        // Verifica as informações retornadas (valores fictícios de exemplo)
        assertThat(response.getBody().getMaxIntervalProducer()).isEqualTo("Allan Carr");
        assertThat(response.getBody().getMaxInterval()).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody().getMinIntervalProducer()).isEqualTo("Steve Shagan");
        assertThat(response.getBody().getMinInterval()).isGreaterThanOrEqualTo(1);
    } */
}
