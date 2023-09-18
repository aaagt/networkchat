package aaagt.networkchat.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    private int serverPort;

    private Settings() {
        loadSettings();
    }

    public static Settings getInstance() {
        return SettingsWrapper.INSTANCE;
    }

    public int getServerPort() {
        return serverPort;
    }

    private void loadSettings() {
        Properties prop = new Properties();
        String fileName = "settings.properties";
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream(fileName)) {
            prop.load(fis);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        serverPort = Integer.parseInt(prop.getProperty("server.port", "8090"));
    }

    private static class SettingsWrapper {
        static Settings INSTANCE = new Settings();
    }
}
