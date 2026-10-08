package ru.yandex.practicum.sleeptracker.analyzer;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class BadQualitySessionsAnalyzer implements  SleepAnalyzer {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        long count = (sessions == null) ? 0L : sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult<>("Количество сессий с плохим качеством сна", count);
    }
}
