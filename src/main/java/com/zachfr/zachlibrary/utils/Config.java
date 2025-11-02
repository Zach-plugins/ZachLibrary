package com.zachfr.zachlibrary.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Config extends YamlConfiguration {

    private File file;
    private final Map<String, String> configComments = new HashMap<>();

    public void load(File file, InputStream inputStream, String... ignoredSections){
        try {
            this.file = file;
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setComment(String path, String comment){
        if(comment == null)
            configComments.remove(path);
        else
            configComments.put(path, comment);
    }

    public static Config loadConfiguration(File file) {
        try {
            FileInputStream stream = new FileInputStream(file);
            return loadConfiguration(new InputStreamReader(stream, StandardCharsets.UTF_8));
        }catch(FileNotFoundException ex){
            Bukkit.getLogger().warning("File " + file.getName() + " doesn't exist.");
            return null;
        }
    }

    public static Config loadConfiguration(InputStream inputStream) {
        return loadConfiguration(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public static Config loadConfiguration(Reader reader) {
        Config config = new Config();

        try(BufferedReader bufferedReader = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader)){
            StringBuilder contents = new StringBuilder();

            String line;
            while((line = bufferedReader.readLine()) != null) {
                contents.append(line).append('\n');
            }

            config.loadFromString(contents.toString());
        } catch (IOException | InvalidConfigurationException ex) {
            ex.printStackTrace();
        }

        return config;
    }
}
