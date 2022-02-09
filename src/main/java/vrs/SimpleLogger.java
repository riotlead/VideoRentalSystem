package vrs;

import java.util.logging.Logger;

public class SimpleLogger {
    private SimpleLogger() {}

    public static void log(String log) {
        Logger.getLogger("vrs").info(log);
    }

    public static void logNoNewLine(String log) {
        Logger.getLogger("vrs").info(log);
    }
}
