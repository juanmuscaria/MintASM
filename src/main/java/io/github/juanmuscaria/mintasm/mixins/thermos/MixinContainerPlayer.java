package io.github.juanmuscaria.mintasm.mixins.thermos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.server.v1_7_R4.Container;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryView;
import org.bukkit.entity.HumanEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

//Fix de um unchecked cast, assim causando um InternalServerError disconnect. (https://github.com/Funwayguy/BetterQuesting/issues/605)
@SuppressWarnings("ALL") //Isso é um mixin, ele vai gerar um monte de warnings inuteis.
@Mixin(ContainerPlayer.class)
public abstract class MixinContainerPlayer {
    @Shadow
    @Final
    private EntityPlayer thePlayer;

    @Shadow //O mixin vai reclamar disso, mas no thermos esse field existe. Causará erro no forge normal!
    private CraftInventoryView bukkitEntity = null;

    @Shadow //O mixin vai reclamar disso, mas no thermos esse field existe. Causará erro no forge normal!
    private InventoryPlayer player;

    @Shadow public InventoryCrafting craftMatrix;

    @Inject(method = "onCraftMatrixChanged", at = @At("HEAD"), cancellable = true) //Injetar bem no inicio do metodo, assim poder cancelar ele e evitar o unchecked cast.
    public void onCraftMatrixChanged(IInventory p_75130_, CallbackInfo ci) {
        try {
            Object a = (Object) this; //É preciso fazer isso, já que o this nesse contexto é essa classe, mas no runtime vai ser o ContainerPlayer.
            ContainerPlayer self = (ContainerPlayer) a;
            Class containerclass = self.getClass().getSuperclass();
            Field craftersField = containerclass.getDeclaredField("field_75149_d");
            craftersField.setAccessible(true);
            List crafters = (List) craftersField.get(self);

                Field last = CraftingManager.getInstance().getClass().getDeclaredField("lastCraftView");
                last.setAccessible(true);
                last.set(CraftingManager.getInstance(), getBukkitView(self));
                self.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(self.craftMatrix, this.thePlayer.worldObj));
                ItemStack craftResult = CraftingManager.getInstance().findMatchingRecipe(self.craftMatrix, this.thePlayer.worldObj);
                self.craftResult.setInventorySlotContents(0, craftResult);

                if (crafters.size() < 1)
                {
                    return;
                }
                if (crafters.get(0) instanceof EntityPlayerMP) {
                    EntityPlayerMP player = (EntityPlayerMP) crafters.get(0);
                    player.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(player.openContainer.windowId, 0, craftResult));
                }
                else {
                    self.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(self.craftMatrix, this.thePlayer.worldObj));
                }
            ci.cancel();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Pura gambiarra do thermos
    @SuppressWarnings("ConstantConditions")
    public CraftInventoryView getBukkitView(ContainerPlayer self) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassCastException {
        if (bukkitEntity != null)
        {
            return bukkitEntity;
        }
        Object cm = self.craftMatrix;
        Object cr = self.craftResult;
        CraftInventoryCrafting inventory = new CraftInventoryCrafting((net.minecraft.server.v1_7_R4.InventoryCrafting) cm, (net.minecraft.server.v1_7_R4.IInventory) cr);
        Method getbukkitentity = player.player.getClass().getMethod("getBukkitEntity");
        Object a = self;
        bukkitEntity = new CraftInventoryView((HumanEntity) getbukkitentity.invoke(player.player), inventory, (Container) a);
        return bukkitEntity;
    }
}
