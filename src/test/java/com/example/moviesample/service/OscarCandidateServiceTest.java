package com.example.moviesample.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class OscarCandidateServiceTest {

    @Autowired
    private OscarCandidateService oscarCandidateService;

    @Test
    public void givenTitleWhichIsWon_thenReturnTrue() {
        String title = "Batman";
        assertTrue(oscarCandidateService.isWon(title));
        assertTrue(oscarCandidateService.isCandidate(title));
    }

    @Test
    public void givenTitleWhichIsNotWon_thenReturnFalse() {
        String title = "The Illusionist";
        assertFalse(oscarCandidateService.isWon(title));
        assertTrue(oscarCandidateService.isCandidate(title));
    }

    @Test
    public void givenTitleWhichIsCandidate_thenReturnTrue() {
        String title = "Toy Story 3";
        assertTrue(oscarCandidateService.isCandidate(title));
    }

    @Test
    public void givenTitleWhichIsNotCandidate_thenReturnFalse() {
        String title = "Toy Story 4";
        assertFalse(oscarCandidateService.isCandidate(title));
        assertFalse(oscarCandidateService.isWon(title));
    }
}