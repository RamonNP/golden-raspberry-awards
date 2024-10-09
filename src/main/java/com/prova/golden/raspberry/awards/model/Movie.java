package com.prova.golden.raspberry.awards.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "release_year", nullable = false) // Renomeando a coluna e definindo como não nula
    private Integer year;

    @Column(nullable = false) // Definindo título como não nulo
    private String title;

    private String studios;

    private boolean winner;

    @ManyToOne // Indica que muitos filmes podem ter um único produtor
    @JoinColumn(name = "producer_id") // Nome da coluna na tabela movies
    private Producer producer;
}
