package ru.yandex.practicum.sleeptracker.model;

public enum SleepQuality {
    GOOD,
    NORMAL,
    BAD;

    public static SleepQuality fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Значение качества сна не может быть null");
        }
        return SleepQuality.valueOf(value.trim().toUpperCase());
    }
}
