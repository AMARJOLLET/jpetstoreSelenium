package utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class Logging {
    public String className ;
    public Logger LOGGER;
    protected String currentClassPath;
    protected String logsDirectory;


    public Logging() {
        this.className = this.getClass().getSimpleName();
        currentClassPath = getClass().getName().replace(".", "/");
        System.setProperty("logFileName", this.className);
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("src/main/resources/log4j2.properties");
        context.setConfigLocation(file.toURI());
        LOGGER = LoggerFactory.getLogger(className);
    }

    protected String selectTestClassPath() {
        String testClassPath = null;
        Path s = Paths.get(currentClassPath);
        int n = s.getNameCount();
        while (n > 1) {
            s = s.getParent();
            n -= 1;
            if (s.toString().endsWith("test")) {
                testClassPath = Paths.get(currentClassPath).getParent().toString().replace("\\", "/");
                break;
            } else {
                testClassPath = "notATestClass";
            }
        }
        return testClassPath;
    }

    /*
    public void saveAndCleanLogFiles() {
        // Récupération du log temporaire d'un test et sauvegarde
        if (!selectTestClassPath().equals("notATestClass")) {
            String testLogFilePath = logsDirectory + "/" + selectTestClassPath() + "/" + className + ".log";
            try {
                Files.copy(Paths.get(tempTestLogFilePath), Paths.get(testLogFilePath), REPLACE_EXISTING);
            } catch (IOException e) {
                LOGGER.info("A temporary log couldn't be saved.");
            }
        }
        // Suppression des logs temporaires
        try {
            FileUtils.deleteDirectory(new File(Paths.get(tempTestLogFilePath).getParent().toString()));
        } catch (IOException ex) {
//            LOGGER.info("A temporary log couldn't be deleted");
        }
    }

     */

}
