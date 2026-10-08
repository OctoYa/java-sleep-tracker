package ru.yandex.practicum.sleeptracker.analyzer;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class MaxDurationAnalyzer implements  SleepAnalyzer {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long maxDuration = (sessions == null) ? 0L : sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .max()
                .orElse(0L);

        return new SleepAnalysisResult<>("Максимальная продолжительность сессии (мин)", maxDuration);
    }
}
