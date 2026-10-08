package ru.yandex.practicum.sleeptracker.analyzer;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class TotalSessionsAnalyzer implements  SleepAnalyzer {

    @Override
    public SleepAnalysisResult<Integer> apply(List<SleepingSession> sessions) {
        int total = (sessions == null) ? 0 : sessions.size();
        return new SleepAnalysisResult<>("Общее количество сессий сна", total);
    }
}
