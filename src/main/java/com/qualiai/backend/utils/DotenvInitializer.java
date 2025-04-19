package com.qualiai.backend.utils;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DotenvInitializer {

    private static final Set<String> ALLOWED_KEYS = Set.of(
            "JWT_SECRET",
            "X-API-KEY",
            "DB_USERNAME",
            "DB_PASSWORD",
            "DB_URL",
            "DB_NAME",
            "DB_HOST",
            "DB_PORT"
    );

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        dotenv.entries().forEach(entry -> {
            if (ALLOWED_KEYS.contains(entry.getKey())) {
                System.setProperty(entry.getKey(), entry.getValue());
//                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        });
    }

}
