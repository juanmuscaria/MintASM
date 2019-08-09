package io.github.juanmuscaria.mintasm;

import net.cubespace.Yamler.Config.*;

import java.io.File;

@SuppressWarnings("WeakerAccess")
public class Configs extends YamlConfig {
    private static Configs instance;

    @Comment("Serve para ativar o modo de depuração.")
    public Boolean debug = false;

    @Comment("Defina true para ativar as correções do thermos.")
    public Boolean mixins_thermos = true;

    @Comment("Defina true para ativar as correções do EnderIO.")
    public Boolean mixins_enderio = false;

    public static Configs getInstance(){
        return instance;
    }

    public Configs(){
        CONFIG_FILE = new File("MintASM.yml"); //Define o arquivo de configuração
        CONFIG_MODE = ConfigMode.PATH_BY_UNDERSCORE; //Define que o _ seja o path da config (path_to_config = path.to.config).
        instance = this;
        try {
            this.init(); //Usa o proprio sistema da api para carregar as coisas
        } catch (InvalidConfigurationException e) {
            System.out.println("Não foi possivel carregar o arquivo de configuração, usando configurações padrões!");
            e.printStackTrace();
        }
    }

    @Override
    public void update(ConfigSection config) {
        //Pore enquanto não irei fazer um conversor de configurações antigas.
    }
}
