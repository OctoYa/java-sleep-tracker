package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.analyzer.*;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;
import ru.yandex.practicum.sleeptracker.service.SleepLogParser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Ошибка: укажите путь к файлу с логом сна в аргументах командной строки.");
            return;
        }

        Path filePath = Paths.get(args[0]);
        SleepLogParser parser = new SleepLogParser();

        try {
            List<SleepingSession> sessions = parser.parseLogFile(filePath);
            List<SleepAnalyzer> analyzers = initAnalyzers();
            runAnalysis(sessions, analyzers);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка при анализе данных: " + e.getMessage());
        }
    }

    private static List<SleepAnalyzer> initAnalyzers() {
        return List.of(
                new TotalSessionsAnalyzer(),
                new MinDurationAnalyzer(),
                new MaxDurationAnalyzer(),
                new AvgDurationAnalyzer(),
                new BadQualitySessionsAnalyzer(),
                new SleeplessNightsAnalyzer(),
                new ChronotypeAnalyzer()
        );
    }

    private static void runAnalysis(List<SleepingSession> sessions, List<SleepAnalyzer> analyzers) {
        analyzers.forEach(analyzer -> {
            SleepAnalysisResult<?> result = analyzer.apply(sessions);
            System.out.println(result);
        });
    }
}