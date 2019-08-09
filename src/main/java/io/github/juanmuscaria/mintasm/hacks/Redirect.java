package io.github.juanmuscaria.mintasm.hacks;

import io.github.juanmuscaria.mintasm.Fixs;
import net.minecraft.world.World;

//Redireciona as calls do forge para o plugin, essa class deve ser injetada no classloader do plugin e ser chamada via reflection.
public class Redirect {
    public void dropperFix(World p_149941_1_, int p_149941_2_, int p_149941_3_, int p_149941_4_,Object self,Object shadow) {
        Fixs.dropperFix(p_149941_1_,p_149941_2_,p_149941_3_,p_149941_4_,self,shadow);
    }
}
