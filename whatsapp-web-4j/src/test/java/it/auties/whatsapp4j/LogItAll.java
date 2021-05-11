package it.auties.whatsapp4j;

import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogItAll extends Handler {
   /**** This is where the log messages are written. */
    private PrintStream out = System.out;
    /** For formatting the log statements. */
    private SimpleFormatter formatter = new SimpleFormatter();

    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().intValue() < getLevel().intValue()) {
            return;
        }
        out.print(formatter.format(record));
    }

    @Override
    public void flush() {
//system.out handles itself
    }

    @Override
    public void close() throws SecurityException {
//system.out handles itself
    }
}
