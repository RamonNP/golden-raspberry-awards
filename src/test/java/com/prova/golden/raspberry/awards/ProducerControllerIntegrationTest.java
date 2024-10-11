package com.prova.golden.raspberry.awards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProducerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnProducerIntervals() throws Exception {
        mockMvc.perform(get("/producers/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.max").isArray())
                // Validação dos resultados de "min"
                .andExpect(jsonPath("$.min[0].producer").value("Joel Silver"))
                .andExpect(jsonPath("$.min[0].interval").value(1))
                .andExpect(jsonPath("$.min[0].previousWin").value(1990))
                .andExpect(jsonPath("$.min[0].followingWin").value(1991))
                .andExpect(jsonPath("$.min[1].producer").value("Bo Derek"))
                .andExpect(jsonPath("$.min[1].interval").value(6))
                .andExpect(jsonPath("$.min[1].previousWin").value(1984))
                .andExpect(jsonPath("$.min[1].followingWin").value(1990))
                .andExpect(jsonPath("$.min[2].producer").value("Buzz Feitshans"))
                .andExpect(jsonPath("$.min[2].interval").value(9))
                .andExpect(jsonPath("$.min[2].previousWin").value(1985))
                .andExpect(jsonPath("$.min[2].followingWin").value(1994))
                .andExpect(jsonPath("$.min[3].producer").value("Matthew Vaughn"))
                .andExpect(jsonPath("$.min[3].interval").value(13))
                .andExpect(jsonPath("$.min[3].previousWin").value(2002))
                .andExpect(jsonPath("$.min[3].followingWin").value(2015))
                // Validação dos resultados de "max"
                .andExpect(jsonPath("$.max[0].producer").value("Matthew Vaughn"))
                .andExpect(jsonPath("$.max[0].interval").value(13))
                .andExpect(jsonPath("$.max[0].previousWin").value(2002))
                .andExpect(jsonPath("$.max[0].followingWin").value(2015))
                .andExpect(jsonPath("$.max[1].producer").value("Buzz Feitshans"))
                .andExpect(jsonPath("$.max[1].interval").value(9))
                .andExpect(jsonPath("$.max[1].previousWin").value(1985))
                .andExpect(jsonPath("$.max[1].followingWin").value(1994))
                .andExpect(jsonPath("$.max[2].producer").value("Bo Derek"))
                .andExpect(jsonPath("$.max[2].interval").value(6))
                .andExpect(jsonPath("$.max[2].previousWin").value(1984))
                .andExpect(jsonPath("$.max[2].followingWin").value(1990))
                .andExpect(jsonPath("$.max[3].producer").value("Joel Silver"))
                .andExpect(jsonPath("$.max[3].interval").value(1))
                .andExpect(jsonPath("$.max[3].previousWin").value(1990))
                .andExpect(jsonPath("$.max[3].followingWin").value(1991));
    }
}
