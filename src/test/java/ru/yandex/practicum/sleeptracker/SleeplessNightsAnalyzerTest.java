package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analyzer.SleeplessNightsAnalyzer;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightsAnalyzerTest {

    private SleeplessNightsAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new SleeplessNightsAnalyzer();
    }

    @Test
    @DisplayName("Крайний случай 1: Пустой список или null возвращает 0")
    void apply_shouldReturnZeroForEmptyOrNull() {
        assertEquals(0L, analyzer.apply(Collections.emptyList()).getValue());
        assertEquals(0L, analyzer.apply(null).getValue());
    }

    @Test
    @DisplayName("Крайний случай 2: Отсутствие бессонных ночей (пользователь спал каждую ночь в интервале 00:00-06:00)")
    void apply_shouldReturnZeroWhenAllNightsHaveSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals("Количество бессонных ночей", result.getDescription());
        assertEquals(0L, result.getValue());
    }

    @Test
    @DisplayName("Обнаружение бессонной ночи (сон только днем или пропущен полностью)")
    void apply_shouldDetectSleeplessNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 14, 0),
                        LocalDateTime.of(2025, 10, 2, 16, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    @DisplayName("Крайний случай 3: Корректный выбор даты начала, если первая сессия начинается до 12:00")
    void apply_shouldAdjustStartDateWhenFirstSessionBeforeNoon() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 8, 0),
                        LocalDateTime.of(2025, 10, 1, 10, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(1L, result.getValue());
    }

    @Test
    @DisplayName("Крайний случай 4: Расчет бессонных ночей при переходе через границы месяцев")
    void apply_shouldHandleMonthBoundariesCorrectly() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 31, 23, 0),
                        LocalDateTime.of(2025, 11, 1, 7, 0),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 11, 2, 23, 0),
                        LocalDateTime.of(2025, 11, 3, 7, 0),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult<Long> result = analyzer.apply(sessions);
        assertEquals(1L, result.getValue());
    }
}
