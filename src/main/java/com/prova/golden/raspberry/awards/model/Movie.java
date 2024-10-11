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

    @Column(name = "release_year", nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String title;

    private String studios;

    private boolean winner;

    @ManyToOne
    @JoinColumn(name = "producer_id")
    private Producer producer;
}
