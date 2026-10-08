package ru.yandex.practicum.sleeptracker.analyzer;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class AvgDurationAnalyzer implements  SleepAnalyzer {

    @Override
    public SleepAnalysisResult<Double> apply(List<SleepingSession> sessions) {
        double avgDuration = (sessions == null) ? 0.0 : sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0.0);

        return new SleepAnalysisResult<>("Средняя продолжительность сессии (мин)", avgDuration);
    }
}
