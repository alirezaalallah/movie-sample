package com.example.moviesample.dao.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRateRepository extends CrudRepository<UserRate, Long> {
    Optional<UserRate> findByMovieTitleAndAndUser(String movieTitle, String user);

    @Query("SELECT ur.movieTitle,ur.boxOffice,avg (ur.value) FROM UserRate AS ur GROUP BY ur.movieTitle,ur.boxOffice HAVING avg (ur.value) = 10 ORDER BY avg (ur.value) DESC,ur.boxOffice DESC")
    List<Object[]> topTenMovieOrderedByBoxOffice(Pageable pageable);
}
