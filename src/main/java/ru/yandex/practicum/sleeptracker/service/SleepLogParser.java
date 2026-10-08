package ru.yandex.practicum.sleeptracker.service;

import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SleepLogParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<SleepingSession> parseLogFile(Path filePath) throws IOException {
        try (var lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            return lines
                    .filter(line -> !line.trim().isEmpty())
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        }
    }

    private SleepingSession parseLine(String line) {
        String[] parts = line.split(";");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Некорректный формат строки: " + line);
        }

        LocalDateTime startTime = LocalDateTime.parse(parts[0].trim(), FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        SleepQuality quality = SleepQuality.fromString(parts[2].trim());

        return new SleepingSession(startTime, endTime, quality);
    }
}
