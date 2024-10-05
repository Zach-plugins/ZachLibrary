package com.zachfr.zachlibrary.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Config extends YamlConfiguration {

    private File file;

    public void load(File file, InputStream inputStream, String... ignoredSections){
        try {
            this.file = file;
            load(file);
            //syncWithConfig(file, inputStream, ignoredSections);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
