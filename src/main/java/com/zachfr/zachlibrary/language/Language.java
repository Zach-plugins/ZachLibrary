package com.zachfr.zachlibrary.language;

import com.zachfr.zachlibrary.ZachLibrary;
import com.zachfr.zachlibrary.utils.Config;

import java.io.File;

public class Language {
    private ZachLibrary instance;
    private final Config language = new Config();

    public Language(ZachLibrary instance) {
        this.instance = instance;
        load();
    }

    public void load(){
        if(!instance.getDataFolder().exists())
            instance.getDataFolder().mkdir();

        File languageFile = new File(instance.getDataFolder(), "language.yml");

        if(!languageFile.exists())
            instance.saveResource("language.yml", false);

        language.load(languageFile, instance.getResource("language.yml"));

        Message.setPrefix(language.isSet("messages.prefix") ? language.getString("messages.prefix") : null);
    }

    public Message getMessage(String key){
        return new Message(language.getString(key) == null ? key : language.getString(key));
    }

    public Message newMessage(String message){
        return new Message(message);
    }
}
