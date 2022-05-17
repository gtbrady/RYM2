package Utility;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.logging.*;
import java.io.File;

/**
 * Logger class that prints each
 */
public class LoginTracking {
    public Logger logger;
    FileHandler fh;

    /**
     * Writes UTC Timestamp, username and whether the login was successful or not to a text file.
     * @param fileName name of the text file
     * @throws SecurityException
     */
     public LoginTracking(String fileName) throws SecurityException {
         try {
             File f = new File(fileName);
             if (!f.exists()) {
                 f.createNewFile();
             }
             fh = new FileHandler(fileName, true);
             logger = Logger.getLogger("logger");
             logger.setUseParentHandlers(false);
             fh.setFormatter(new SimpleFormatter() {

                 private static final String format = "%3$s %n";
                 @Override
                 public String format(LogRecord lr) {
                     return String.format(format,
                             ZonedDateTime.of(LocalDateTime.now(), TimeManipulation.getUtcZone()),
                             lr.getLevel().getLocalizedName(),
                             lr.getMessage()
                     );
                 }
             });

             logger.addHandler(fh);
         } catch (IOException e) {
         }
    }
}
