package com.qualiai.backend.utils.data;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DataAtual {
    public static Date dataSemFormatacao() {
        return Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant());
    }
}
