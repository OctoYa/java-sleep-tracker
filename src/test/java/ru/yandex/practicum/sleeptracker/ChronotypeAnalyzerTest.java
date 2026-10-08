package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.analyzer.ChronotypeAnalyzer;
import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeAnalyzerTest {

    private ChronotypeAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new ChronotypeAnalyzer();
    }

    @Test
    @DisplayName("Определение хронотипа 'Сова' (засыпание после 23:00, подъем после 09:00)")
    void apply_shouldClassifyAsOwl() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 9, 30),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals("Определенный хронотип", result.getDescription());
        assertEquals(Chronotype.OWL.getDescription(), result.getValue());
    }

    @Test
    @DisplayName("Определение хронотипа 'Жаворонок' (засыпание до 22:00, подъем до 07:00)")
    void apply_shouldClassifyAsLark() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 30),
                        LocalDateTime.of(2025, 10, 2, 6, 30),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals(Chronotype.LARK.getDescription(), result.getValue());
    }

    @Test
    @DisplayName("Определение хронотипа 'Голубь' для промежуточных интервалов")
    void apply_shouldClassifyAsDove() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 30),
                        LocalDateTime.of(2025, 10, 2, 7, 30),
                        SleepQuality.GOOD
                )
        );

        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals(Chronotype.DOVE.getDescription(), result.getValue());
    }

    @Test
    @DisplayName("При равенстве ночей разных типов выбирается 'Голубь'")
    void apply_shouldDefaultToDoveOnTie() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 23, 30), LocalDateTime.of(2025, 10, 2, 9, 30), SleepQuality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 10, 2, 21, 30), LocalDateTime.of(2025, 10, 3, 6, 30), SleepQuality.GOOD)
        );

        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals(Chronotype.DOVE.getDescription(), result.getValue());
    }

    @Test
    @DisplayName("Игнорирование дневных сессий сна при определении хронотипа")
    void apply_shouldIgnoreDaySessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 14, 0), LocalDateTime.of(2025, 10, 1, 15, 0), SleepQuality.NORMAL),
                new SleepingSession(LocalDateTime.of(2025, 10, 1, 21, 0), LocalDateTime.of(2025, 10, 2, 6, 0), SleepQuality.GOOD)
        );

        SleepAnalysisResult<String> result = analyzer.apply(sessions);
        assertEquals(Chronotype.LARK.getDescription(), result.getValue());
    }

    @Test
    @DisplayName("Возврат 'Голубь' для пустого или null списка")
    void apply_shouldReturnDoveForEmptyOrNullList() {
        assertEquals(Chronotype.DOVE.getDescription(), analyzer.apply(Collections.emptyList()).getValue());
        assertEquals(Chronotype.DOVE.getDescription(), analyzer.apply(null).getValue());
    }
}
