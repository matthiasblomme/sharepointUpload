package com.devmatthias.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ApplicationProperties {

    private InputStream inputStream;

    public Properties getPropValues() throws IOException {
        Properties properties;
        try {
            properties = new Properties();
            String propFileName = "application.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if(inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file: " + propFileName + " not found in the classpath.");
            }
        } catch (IOException e) {
            System.out.println("io exception: " + e);
            throw new IOException("error");
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
        return properties;
    }

    public static String getClientId() throws IOException{
        try {
            Properties propValues = new ApplicationProperties().getPropValues();
            return propValues.getProperty("client.id");
        } catch (IOException ex) {
            throw new IOException("client id not found");
        }
    }

    public static String getClientSecret() throws IOException {
        try {
            Properties propValues = new ApplicationProperties().getPropValues();
            return propValues.getProperty("client.secret");
        } catch (IOException ex) {
            throw new IOException("client secret value not found");
        }
    }

    public static String getTenantId() throws IOException {
        try {
            Properties propValues = new ApplicationProperties().getPropValues();
            return propValues.getProperty("tenant.id");
        } catch (IOException ex) {
            throw new IOException("tenant id value not found");
        }
    }


    public static List<String> getScopeList() throws IOException {
        try {
            Properties propValues = new ApplicationProperties().getPropValues();
            String[] scopeList = propValues.getProperty("scope").split(",");
            return Arrays.asList(scopeList);
        } catch (IOException ex) {
            throw new IOException("scopes value not found");
        }
    }

    public static String getSiteId() throws IOException {
        try {
            Properties propValues = new ApplicationProperties().getPropValues();
            return propValues.getProperty("site.id");
        } catch (IOException ex) {
            throw new IOException("site id value not found");
        }
    }

    public static String getUploadPath() throws IOException {
        try {
            Properties propValues = new ApplicationProperties().getPropValues();
            return propValues.getProperty("site.uploadpath");
        } catch (IOException ex) {
            throw new IOException("upload path value not found");
        }
    }

    public static String getUploadFile() throws IOException {
        try {
            Properties propValues = new ApplicationProperties().getPropValues();
            return propValues.getProperty("site.uploadFile");
        } catch (IOException ex) {
            throw new IOException("upload file value not found");
        }
    }

    public static String getSiteDrive() throws IOException {
        try {
            Properties propValues = new ApplicationProperties().getPropValues();
            return propValues.getProperty("site.drive");
        } catch (IOException ex) {
            throw new IOException("Site drive value not found");
        }
    }
}
