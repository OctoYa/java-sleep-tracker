package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analyzer.TotalSessionsAnalyzer;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalSessionsAnalyzerTest {

    private TotalSessionsAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new TotalSessionsAnalyzer();
    }

    @Test
    @DisplayName("Подсчет общего количества сессий для корректного списка")
    void apply_shouldReturnCorrectTotalCount() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 22, 0), LocalDateTime.of(2025, 10, 2, 7, 0), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 23, 0), LocalDateTime.of(2025, 10, 3, 7, 30), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 3, 14, 0), LocalDateTime.of(2025, 10, 3, 15, 0), SleepQuality.BAD)
        );

        SleepAnalysisResult<Integer> result = analyzer.apply(sessions);
        assertEquals("Общее количество сессий сна", result.getDescription());
        assertEquals(3, result.getValue());
    }

    @Test
    @DisplayName("Возврат 0 для пустого списка и null")
    void apply_shouldReturnZeroForEmptyOrNull() {
        assertEquals(0, analyzer.apply(Collections.emptyList()).getValue());
        assertEquals(0, analyzer.apply(null).getValue());
    }
}
