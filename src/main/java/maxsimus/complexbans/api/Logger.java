package maxsimus.complexbans.api;

public class Logger {
    static java.util.logging.Logger logger;

    public static void setLogger(java.util.logging.Logger l) {
        logger = l;
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void warn(String msg) {
        logger.warning(msg);
    }

    public static void severe(String msg) {
        logger.severe(msg);
    }
}
