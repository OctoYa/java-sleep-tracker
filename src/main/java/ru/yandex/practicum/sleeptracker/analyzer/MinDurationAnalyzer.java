package ru.yandex.practicum.sleeptracker.analyzer;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class MinDurationAnalyzer implements  SleepAnalyzer {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long minDuration = (sessions == null) ? 0L : sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min()
                .orElse(0L);

        return new SleepAnalysisResult<>("Минимальная продолжительность сессии (мин)", minDuration);
    }
}
