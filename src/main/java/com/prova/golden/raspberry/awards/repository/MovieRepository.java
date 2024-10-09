package com.prova.golden.raspberry.awards.repository;

import com.prova.golden.raspberry.awards.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = """
        SELECT
            MAX(year_diff) AS max_interval,
            previous_year AS previousWin,
            release_year AS followingWin,
            p.name AS producer_name
        FROM (
            SELECT
                producer_id,
                release_year,
                LAG(release_year) OVER (PARTITION BY producer_id ORDER BY release_year) AS previous_year,
                release_year - LAG(release_year) OVER (PARTITION BY producer_id ORDER BY release_year) AS year_diff
            FROM movie
            WHERE winner = true
        ) AS award_intervals
        JOIN producer p ON award_intervals.producer_id = p.id
        GROUP BY previous_year, release_year, p.name
        HAVING MAX(year_diff) IS NOT NULL
        ORDER BY max_interval DESC, previous_year ASC;
    """, nativeQuery = true)
    List<Object[]> findMoviesWithMaxInterval();

    @Query(value = """
        SELECT
            MAX(year_diff) AS max_interval,
            previous_year AS previousWin,
            release_year AS followingWin,
            p.name AS producer_name
        FROM (
            SELECT
                producer_id,
                release_year,
                LAG(release_year) OVER (PARTITION BY producer_id ORDER BY release_year) AS previous_year,
                release_year - LAG(release_year) OVER (PARTITION BY producer_id ORDER BY release_year) AS year_diff
            FROM movie
            WHERE winner = true
        ) AS award_intervals
        JOIN producer p ON award_intervals.producer_id = p.id
        GROUP BY previous_year, release_year, p.name
        HAVING MAX(year_diff) IS NOT NULL
        ORDER BY max_interval ASC, previous_year ASC;
    """, nativeQuery = true)
    List<Object[]> findMoviesWithMinInterval();
}
