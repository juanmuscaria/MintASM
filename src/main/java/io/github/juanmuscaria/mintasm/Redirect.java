package io.github.juanmuscaria.mintasm;

import net.minecraft.world.World;

//Redireciona as calls do forge para o plugin
public class Redirect {
    static {
        try {
            Class.forName("io.github.juanmuscaria.mintasm.Hooks");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void func_149941_e(World p_149941_1_, int p_149941_2_, int p_149941_3_, int p_149941_4_,Object self) {
        Hooks.func_149941_e(p_149941_1_,p_149941_2_,p_149941_3_,p_149941_4_,self);
    }
}
