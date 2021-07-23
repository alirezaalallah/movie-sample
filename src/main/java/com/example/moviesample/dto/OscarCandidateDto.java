package com.example.moviesample.dto;

import org.apache.commons.csv.CSVRecord;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Objects;

public class OscarCandidateDto implements Serializable {
    private static final long serialVersionUID = 1234567L;

    private LocalDate date;
    private String category;
    private String nominee;
    private String additionalInfo;
    private Boolean won;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Boolean getWon() {
        return won;
    }

    public void setWon(Boolean won) {
        this.won = won;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OscarCandidateDto that = (OscarCandidateDto) o;
        return Objects.equals(date, that.date) && Objects.equals(category, that.category) && Objects.equals(nominee, that.nominee) && Objects.equals(additionalInfo, that.additionalInfo) && Objects.equals(won, that.won);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, category, nominee, additionalInfo, won);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private OscarCandidateDto oscarCandidateDto = new OscarCandidateDto();

        public Builder from(CSVRecord csvRecord) {
            date(csvRecord);
            oscarCandidateDto.category = csvRecord.get("Category");
            oscarCandidateDto.nominee = csvRecord.get("Nominee");
            oscarCandidateDto.additionalInfo = csvRecord.get("Additional Info");
            oscarCandidateDto.won = csvRecord.get("Won").equals("YES");
            return this;
        }

        public Builder date(CSVRecord record) {
            String date = record.get("Year");
            DateTimeFormatter format = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy")
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                    .toFormatter();
            oscarCandidateDto.date = LocalDate.parse(date.substring(0, 4), format);
            return this;
        }

        public OscarCandidateDto build() {
            return oscarCandidateDto;
        }

    }
}
