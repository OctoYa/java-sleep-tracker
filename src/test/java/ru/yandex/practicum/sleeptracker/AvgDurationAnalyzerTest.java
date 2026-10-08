package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analyzer.AvgDurationAnalyzer;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvgDurationAnalyzerTest {

    private AvgDurationAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new AvgDurationAnalyzer();
    }

    @Test
    @DisplayName("Расчет средней продолжительности сессий сна в минутах")
    void apply_shouldReturnAverageDurationInMinutes() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 1, 0), LocalDateTime.of(2025, 10, 1, 6, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0), LocalDateTime.of(2025, 10, 2, 8, 0), SleepQuality.GOOD)
        );

        SleepAnalysisResult<Double> result = analyzer.apply(sessions);
        assertEquals("Средняя продолжительность сессии (мин)", result.getDescription());
        assertEquals(450.0, result.getValue(), 0.001);
    }

    @Test
    @DisplayName("Возврат 0.0 для пустого списка и null")
    void apply_shouldReturnZeroForEmptyOrNull() {
        assertEquals(0.0, analyzer.apply(Collections.emptyList()).getValue(), 0.001);
        assertEquals(0.0, analyzer.apply(null).getValue(), 0.001);
    }
}