package com.qualiai.backend.utils.data;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class StaticDateFormat {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String formatDate(Date date) {
        return FORMATTER.format(date);
    }

    public static String dataAtual() {
        Date now = Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant());
        return formatDate(now);
    }

}
