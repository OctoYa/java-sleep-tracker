package ru.yandex.practicum.sleeptracker.analyzer;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.LongStream;

public class SleeplessNightsAnalyzer implements  SleepAnalyzer {

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Количество бессонных ночей", 0L);
        }

        LocalDateTime firstStart = sessions.get(0).getStartTime();
        LocalDate startDate = firstStart.getHour() >= 12
                ? firstStart.toLocalDate()
                : firstStart.toLocalDate().minusDays(1);

        LocalDateTime lastEnd = sessions.get(sessions.size() - 1).getEndTime();
        LocalDate endDate = lastEnd.toLocalDate();

        long totalNights = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);

        if (totalNights <= 0) {
            return new SleepAnalysisResult<>("Количество бессонных ночей", 0L);
        }

        long sleeplessNightsCount = LongStream.range(0, totalNights)
                .mapToObj(startDate::plusDays)
                .filter(nightDate -> isSleeplessNight(nightDate, sessions))
                .count();

        return new SleepAnalysisResult<>("Количество бессонных ночей", sleeplessNightsCount);
    }

    private boolean isSleeplessNight(LocalDate nightDate, List<SleepingSession> sessions) {
        LocalDateTime intervalStart = nightDate.plusDays(1).atTime(LocalTime.MIDNIGHT); // 00:00 следующего дня
        LocalDateTime intervalEnd = nightDate.plusDays(1).atTime(6, 0);                 // 06:00 следующего дня

        return sessions.stream().noneMatch(session ->
                session.getStartTime().isBefore(intervalEnd) && session.getEndTime().isAfter(intervalStart)
        );
    }
}
