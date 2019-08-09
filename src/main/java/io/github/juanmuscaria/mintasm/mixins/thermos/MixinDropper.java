package io.github.juanmuscaria.mintasm.mixins.thermos;


import io.github.juanmuscaria.mintasm.hacks.RedirectManager;
import net.minecraft.block.BlockDropper;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//Fix de um crash pela falta do check do getOwner (https://github.com/OpenMods/OpenBlocks/issues/807)
@SuppressWarnings("ALL") //Isso é um mixin, ele vai gerar um monte de warnings inuteis
@Mixin(BlockDropper.class)
public abstract class MixinDropper{
    @Shadow
    @Final
    private IBehaviorDispenseItem field_149947_P;

    @Inject(method = "func_149941_e", at = @At("HEAD"), cancellable = true)
    public void func_149941_e(World p_149941_1_, int p_149941_2_, int p_149941_3_, int p_149941_4_, CallbackInfo ci) {
        ci.cancel();
        RedirectManager.dropperFix(p_149941_1_,p_149941_2_,p_149941_3_,p_149941_4_,(Object)this,(Object)field_149947_P); //É preciso fazer um redirect, pois o plugin não estará carregado no momento
    }

}
