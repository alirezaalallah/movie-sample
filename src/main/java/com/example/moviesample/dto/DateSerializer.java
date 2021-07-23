package com.example.moviesample.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateSerializer extends StdDeserializer<LocalDate> {

    public DateSerializer() {
        this(null);
    }

    public DateSerializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(
            JsonParser jsonparser, DeserializationContext context)
            throws IOException {

        String date = jsonparser.getText();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM u", Locale.ENGLISH);
        return LocalDate.parse(date, dateFormatter);
    }
}