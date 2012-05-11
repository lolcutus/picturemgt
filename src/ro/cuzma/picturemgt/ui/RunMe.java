package ro.cuzma.picturemgt.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class RunMe {

    static Logger logger = Logger.getLogger(PictureManagement.class);
    private String logFile = "log.cfg";
    private static String initFile = "init.cfg";
    private static Properties props = new Properties();
    public static String DB_FILE = "dbFile";
    private String dbFile = "";
    public static String PICTURE_ROOT = "pictureRoot";
    private String pictureRoot = "";

    /**
     * @param args
     */
    public static void main(String[] args) {
        RunMe app = new RunMe(args[0]);
    }

    public RunMe(String initFileName) {
        if (initFileName != null && !initFileName.isEmpty()) {
            initFile = initFileName;
        }
        initApp();
        logger.debug("App initialized");
        logger.info("Start UI");
        PictureManagement pm = new PictureManagement();
        File dbF = new File(dbFile);
        if (dbF.exists() && dbF.isFile()) {
            pm.load(dbF, pictureRoot);
        }
        pm.setVisible(true);
    }

    private void initApp() {
        File logF = new File(logFile);
        if (!logF.exists()) {
            try {
                RandomAccessFile rw = new RandomAccessFile(logFile, "rw");
                String toWrite = "log4j.rootLogger=ERROR, Console\r\n";
                rw.write(toWrite.getBytes());
                toWrite = "log4j.appender.Console=org.apache.log4j.ConsoleAppender\r\n";
                rw.write(toWrite.getBytes());
                toWrite = "log4j.appender.Console.layout=org.apache.log4j.PatternLayout\r\n";
                rw.write(toWrite.getBytes());
                toWrite = "log4j.appender.Console.layout.ConversionPattern=%d|%-5.5p|%-30.30C|%-30.30M|%5.5L|%m%n\r\n";
                rw.write(toWrite.getBytes());
                rw.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        PropertyConfigurator.configure(logFile);
        logger.debug("log init done");
        logger.debug("init app");
        File initF = new File(initFile);
        if (!initF.exists()) {
            try {
                // RunMe.props.load(new FileInputStream(initFile));
                props.put(DB_FILE, "");
                props.put(PICTURE_ROOT, "");
                props.store(new FileOutputStream(initFile), "init app");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            props.load(new FileInputStream(initFile));
            dbFile = props.getProperty(DB_FILE);
            pictureRoot = props.getProperty(PICTURE_ROOT);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setMessage() {

        // create an instance of properties class

        Properties props = new Properties();

        // try retrieve data from file

        try {

            props.load(new FileInputStream("message.properties"));

            String message = props.getProperty("message");

            System.out.println(message);
        }

        // catch exception in case properties file does not exist

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setInitFile(File dbFile, String pictureRoot) {
        props.put(DB_FILE, dbFile.getAbsolutePath());
        props.put(PICTURE_ROOT, pictureRoot);
        try {
            props.store(new FileOutputStream(initFile), "init app");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
