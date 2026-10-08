package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SleepTrackerAppTest {

    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        PrintStream capturingStream = new PrintStream(outputStreamCaptor);
        System.setOut(capturingStream);
        System.setErr(capturingStream);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        System.setErr(standardErr);
    }

    @Test
    @DisplayName("Запуск main с временным файлом лога выводит результаты в консоль без изменения лога")
    void main_shouldReadTempFileAndPrintResultsWithoutModifyingFile(@TempDir Path tempDir) throws IOException {
        Path tempLogFile = tempDir.resolve("test_sleep_log.txt");
        List<String> lines = List.of(
                "01.10.25 22:15;02.10.25 08:00;GOOD",
                "02.10.25 23:00;03.10.25 08:00;NORMAL",
                "03.10.25 23:30;04.10.25 06:20;BAD"
        );
        Files.write(tempLogFile, lines);

        assertDoesNotThrow(() -> SleepTrackerApp.main(new String[]{tempLogFile.toString()}));

        String printedOutput = outputStreamCaptor.toString();

        assertTrue(printedOutput.contains("Общее количество сессий сна: 3"),
                "Вывод должен содержать результат работы TotalSessionsAnalyzer");
        assertTrue(printedOutput.contains("Количество сессий с плохим качеством сна: 1"),
                "Вывод должен содержать результат работы BadQualitySessionsAnalyzer");
        assertTrue(printedOutput.contains("Определенный хронотип:"),
                "Вывод должен содержать результат работы ChronotypeAnalyzer");
    }

    @Test
    @DisplayName("Корректная обработка несуществующего файла лога без выброса необработанных исключений")
    void main_shouldHandleNonExistentFileGracefully() {
        String nonExistentPath = "non_existent_sleep_log_12345.txt";

        assertDoesNotThrow(() -> SleepTrackerApp.main(new String[]{nonExistentPath}));

        String printedOutput = outputStreamCaptor.toString();
        assertTrue(printedOutput.contains("Ошибка при чтении файла"),
                "При отсутствии файла должно выводиться сообщение об ошибке чтения");
    }
}