package fr.teyir.simpletokens.utils;

import fr.teyir.simpletokens.SimpleTokens;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LogsManager {
    final private SimpleTokens plugin;

    private File logFile;

    private String logFileName;

    private String path;

    public LogsManager(SimpleTokens plugin) {
        this.plugin = plugin;
        path = plugin.getDataFolder() + File.separator + "logs" + File.separator;
        createLogFile();
    }

    public void sendLog(String content) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime now = LocalTime.now();
        String prefix = "[ " + dtf.format(now) + " ] > ";

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path + logFileName, true));
            writer.write(prefix + content);
            writer.append("\n");
            writer.close();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private void createLogFile() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy");
        LocalDateTime now = LocalDateTime.now();
        logFileName = dtf.format(now) + ".log";

        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logFile = new File(path + logFileName);

        if (!logFile.exists())
            new File(path + logFileName);
    }

}
