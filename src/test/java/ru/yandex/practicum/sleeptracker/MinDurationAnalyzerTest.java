package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analyzer.MinDurationAnalyzer;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinDurationAnalyzerTest {

    private MinDurationAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new MinDurationAnalyzer();
    }

    @Test
    @DisplayName("Определение минимальной продолжительности сессии сна в минутах")
    void apply_shouldReturnMinimumDurationInMinutes() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0), LocalDateTime.of(2025, 10, 2, 7, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 14, 0), LocalDateTime.of(2025, 10, 2, 15, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0), LocalDateTime.of(2025, 10, 3, 7, 0), SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals("Минимальная продолжительность сессии (мин)", result.getDescription());
        assertEquals(60L, result.getValue());
    }

    @Test
    @DisplayName("Возврат 0L для пустого списка и null")
    void apply_shouldReturnZeroForEmptyOrNull() {
        assertEquals(0L, analyzer.apply(Collections.emptyList()).getValue());
        assertEquals(0L, analyzer.apply(null).getValue());
    }
}