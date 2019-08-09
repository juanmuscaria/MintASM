package io.github.juanmuscaria.mintasm;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MintInterface extends JavaPlugin {
    public static MintInterface INSTANCE;
    public static Logger log;
    @Override
    public void  onEnable(){
        INSTANCE = this;
        log = Bukkit.getLogger();
        log.info("MintInterface iniciado com sucesso, os redirects já podem ser feitos.");
    }

    @Override
    public void  onDisable(){

    }

}
