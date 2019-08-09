package io.github.juanmuscaria.mintasm;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import org.apache.logging.log4j.Logger;


@SuppressWarnings("NonAsciiCharacters")
public class MintASM extends DummyModContainer {
    public static MintASM MintASM;
    public static Logger logger;

    public MintASM(){
        super(new ModMetadata());
        ModMetadata metadata = getMetadata();
        metadata.authorList.add("juanmuscaria");
        metadata.modId = "mintasm";
        metadata.name = "MintASM";
        metadata.version = "1.0";
        metadata.url = "https://juanmuscaria.github.io/";
        metadata.description =  "Um mod para corrigir varias coisas via Mixin.";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }

    @Subscribe
    public void modConstruction(FMLConstructionEvent evt){
        MintASM = this;

    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @Subscribe
    public void init(FMLInitializationEvent evt) {
    }


    @Subscribe
    public void postInit(FMLPostInitializationEvent evt) {
    }
}
