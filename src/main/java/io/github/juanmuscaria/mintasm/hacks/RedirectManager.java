package io.github.juanmuscaria.mintasm.hacks;

import io.github.juanmuscaria.mintasm.MintASM;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

//Um hack para poder redirecionar chamadas de uma classe carregada no do forge para uma classe carregada no classloader de um plugin.
public class RedirectManager {
    private static Object redirect;
    static
    {
        try {
            redirect = Objects.requireNonNull(InjectionUtils.injectClass("MintASM-BukkitInterface", Redirect.class)).newInstance(); //Injeta a classe Redirect no classloader do plugin, podendo ter acesso ao bukkit.
        } catch (InstantiationException | IllegalAccessException | NullPointerException e) {
            MintASM.logger.fatal("Não foi possível injetar a classe Redirect no classloader do MintASM-BukkitInterface, verifique se o plugin está instalado e operando corretamente.");
            throw new RuntimeException(e);
        }
    }
    public static void dropperFix(World p_149941_1_, int p_149941_2_, int p_149941_3_, int p_149941_4_, Object self){
        try {
            Method func_149941_e = redirect.getClass().getDeclaredMethod("dropperFix",World.class,int.class,int.class,int.class,Object.class);
            func_149941_e.invoke(redirect,p_149941_1_,p_149941_2_,p_149941_3_,p_149941_4_,self);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            MintASM.logger.error("Ocorreu um erro ao acionar um dropper, pulando acionamento para evitar um crash.");
            e.printStackTrace();
        }
    }
}
