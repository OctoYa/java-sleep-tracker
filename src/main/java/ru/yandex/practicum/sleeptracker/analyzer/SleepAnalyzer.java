package ru.yandex.practicum.sleeptracker.analyzer;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public interface SleepAnalyzer extends java.util.function.Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    SleepAnalysisResult<?> apply(List<SleepingSession> sessions);

}
