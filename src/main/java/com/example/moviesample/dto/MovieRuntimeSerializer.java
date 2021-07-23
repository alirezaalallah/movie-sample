package com.example.moviesample.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MovieRuntimeSerializer extends StdDeserializer<Integer> {

    public MovieRuntimeSerializer() {
        this(null);
    }

    public MovieRuntimeSerializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Integer deserialize(
            JsonParser jsonparser, DeserializationContext context)
            throws IOException {

        String runtime = jsonparser.getText();

        return Integer.parseInt(runtime.replace("min","").trim());
    }
}
