package com.example.moviesample.service;

import com.example.moviesample.dto.OscarCandidateDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class OscarCandidateService {

    @Resource
    private File oscarWinnerFile;

    private final List<OscarCandidateDto> oscarWinners = new ArrayList<>();

    @PostConstruct
    public void init() throws IOException {
        loadOscarWinners();
    }

    private void loadOscarWinners() throws IOException {
        Reader in = new FileReader(oscarWinnerFile);
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            oscarWinners.add(
                    OscarCandidateDto.builder().from(record).build()
            );
        }
    }

    public Boolean isCandidate(String movieTitle) {
        return oscarWinners.stream().anyMatch(r -> r.getNominee().contains(movieTitle)
                || r.getAdditionalInfo().contains(movieTitle));
    }

    public Boolean isWon(String movieTitle) {
        return oscarWinners.stream().anyMatch(r -> r.getWon() &&
                (r.getNominee().contains(movieTitle)
                        || r.getAdditionalInfo().contains(movieTitle)));
    }
}
