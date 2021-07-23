package com.example.moviesample.dto;

import com.example.moviesample.exception.BoxOfficeParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class BoxOfficeSerializer extends StdDeserializer<BigDecimal> {

    public BoxOfficeSerializer() {
        this(null);
    }

    public BoxOfficeSerializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public BigDecimal deserialize(
            JsonParser jsonparser, DeserializationContext context)
            throws IOException {

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);

        try {
            return (BigDecimal) decimalFormat.parse(jsonparser.getText().replace("$", ""));
        } catch (ParseException e) {
            throw new BoxOfficeParseException();
        }
    }
}
