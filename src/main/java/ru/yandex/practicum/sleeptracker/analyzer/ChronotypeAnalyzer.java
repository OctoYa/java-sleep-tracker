package ru.yandex.practicum.sleeptracker.analyzer;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeAnalyzer implements  SleepAnalyzer {

    @Override
    public SleepAnalysisResult<String> apply(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Определенный хронотип", Chronotype.DOVE.getDescription());
        }

        Map<Chronotype, Long> counts = sessions.stream()
                .filter(SleepingSession::isNightSession)
                .collect(Collectors.groupingBy(
                        this::classifySession,
                        Collectors.counting()
                ));

        long owlCount = counts.getOrDefault(Chronotype.OWL, 0L);
        long larkCount = counts.getOrDefault(Chronotype.LARK, 0L);
        long doveCount = counts.getOrDefault(Chronotype.DOVE, 0L);

        Chronotype result;

        if (owlCount > larkCount && owlCount > doveCount) {
            result = Chronotype.OWL;
        } else if (larkCount > owlCount && larkCount > doveCount) {
            result = Chronotype.LARK;
        } else {
            result = Chronotype.DOVE;
        }

        return new SleepAnalysisResult<>("Определенный хронотип", result.getDescription());
    }

    private Chronotype classifySession(SleepingSession session) {
        LocalTime startTime = session.getStartTime().toLocalTime();
        LocalTime endTime = session.getEndTime().toLocalTime();

        boolean isOwlStart = startTime.getHour() >= 23 || startTime.getHour() < 6;
        boolean isOwlEnd = endTime.isAfter(LocalTime.of(9, 0));
        boolean isOwl = isOwlStart && isOwlEnd;

        boolean isLarkStart = startTime.getHour() >= 18 && startTime.getHour() < 22;
        boolean isLarkEnd = endTime.isBefore(LocalTime.of(7, 0));
        boolean isLark = isLarkStart && isLarkEnd;

        if (isOwl) {
            return Chronotype.OWL;
        } else if (isLark) {
            return Chronotype.LARK;
        } else {
            return Chronotype.DOVE;
        }
    }
}
