package ru.yandex.practicum.sleeptracker.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SleepingSession {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final SleepQuality quality;

    public SleepingSession(LocalDateTime startTime, LocalDateTime endTime, SleepQuality quality) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.quality = quality;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public long getDurationInMinutes() {
        return ChronoUnit.MINUTES.between(startTime, endTime);
    }

    public boolean isNightSession() {
        LocalDateTime startNight = startTime.toLocalDate().atStartOfDay(); // 00:00 текущего дня
        LocalDateTime endNight = startTime.toLocalDate().atTime(6, 0);     // 06:00 текущего дня

        LocalDateTime nextStartNight = startTime.toLocalDate().plusDays(1).atStartOfDay(); // 00:00 следующего дня
        LocalDateTime nextEndNight = startTime.toLocalDate().plusDays(1).atTime(6, 0);     // 06:00 следующего дня

        boolean crossesFirstNight = startTime.isBefore(endNight) && endTime.isAfter(startNight);
        boolean crossesSecondNight = startTime.isBefore(nextEndNight) && endTime.isAfter(nextStartNight);

        return crossesFirstNight || crossesSecondNight;
    }
}
