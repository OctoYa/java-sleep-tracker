package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analyzer.MaxDurationAnalyzer;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxDurationAnalyzerTest {

    private MaxDurationAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new MaxDurationAnalyzer();
    }

    @Test
    @DisplayName("Определение максимальной продолжительности сессии сна в минутах")
    void apply_shouldReturnMaximumDurationInMinutes() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 0), LocalDateTime.of(2025, 10, 2, 7, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 22, 0), LocalDateTime.of(2025, 10, 3, 8, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 14, 0), LocalDateTime.of(2025, 10, 3, 16, 0), SleepQuality.NORMAL)
        );

        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals("Максимальная продолжительность сессии (мин)", result.getDescription());
        assertEquals(600L, result.getValue());
    }

    @Test
    @DisplayName("Возврат 0L для пустого списка и null")
    void apply_shouldReturnZeroForEmptyOrNull() {
        assertEquals(0L, analyzer.apply(Collections.emptyList()).getValue());
        assertEquals(0L, analyzer.apply(null).getValue());
    }
}
