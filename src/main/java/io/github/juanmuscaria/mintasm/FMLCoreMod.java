package io.github.juanmuscaria.mintasm;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.Name("MintASM")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class FMLCoreMod implements IFMLLoadingPlugin{

    public FMLCoreMod(){
        System.setProperty("mixin.debug.export", "true");
        System.out.println("[MintASM]-Carregando arquivo de configurações!");
        new Configs();
        MixinBootstrap.init();
        if (Configs.getInstance().mixins_thermos){
            System.out.println("[MintASM]-Injetando mixins do thermos!");
            Mixins.addConfiguration("mixins/mixins.thermos.json");
        }
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return MintASM.class.getName();
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
