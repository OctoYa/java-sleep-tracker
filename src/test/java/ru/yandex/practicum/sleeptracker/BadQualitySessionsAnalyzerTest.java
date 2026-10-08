package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analyzer.BadQualitySessionsAnalyzer;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadQualitySessionsAnalyzerTest {

    private BadQualitySessionsAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new BadQualitySessionsAnalyzer();
    }

    @Test
    @DisplayName("Подсчет количества сессий с плохим качеством сна (BAD)")
    void apply_shouldReturnCountOfBadQualitySessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0), LocalDateTime.of(2025, 10, 2, 7, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0), LocalDateTime.of(2025, 10, 3, 7, 0), SleepQuality.BAD),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 14, 0), LocalDateTime.of(2025, 10, 3, 15, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 23, 0), LocalDateTime.of(2025, 10, 4, 6, 0), SleepQuality.BAD)
        );

        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals("Количество сессий с плохим качеством сна", result.getDescription());
        assertEquals(2L, result.getValue());
    }

    @Test
    @DisplayName("Возврат 0L для пустого списка и null")
    void apply_shouldReturnZeroForEmptyOrNull() {
        assertEquals(0L, analyzer.apply(Collections.emptyList()).getValue());
        assertEquals(0L, analyzer.apply(null).getValue());
    }
}