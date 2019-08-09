package io.github.juanmuscaria.mintasm;

import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import java.lang.reflect.Field;

public class Hooks {
    public static void func_149941_e(Object  world, int p_149941_2_, int p_149941_3_, int p_149941_4_,Object self2) {
        BlockDropper self = (BlockDropper) self2;
        World p_149941_1_ = (World) world;
        SourceBlock var5 = new SourceBlock(p_149941_1_, p_149941_2_, p_149941_3_, p_149941_4_);
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)var5.getTileEntity();
        if (tileentitydispenser != null) {
            int var7 = tileentitydispenser.i();
            if (var7 < 0) {
                p_149941_1_.d(1001, p_149941_2_, p_149941_3_, p_149941_4_, 0);
            } else {
                ItemStack itemstack = tileentitydispenser.getItem(var7);
                int i1 = p_149941_1_.getData(p_149941_2_, p_149941_3_, p_149941_4_) & 7;
                IInventory iinventory = TileEntityHopper.getInventoryAt(p_149941_1_, (double)(p_149941_2_ + Facing.b[i1]), (double)(p_149941_3_ + Facing.c[i1]), (double)(p_149941_4_ + Facing.d[i1]));
                ItemStack itemstack1;
                if (iinventory != null) {
                    //itemstack1 = TileEntityHopper.func_145889_a(iInventory, itemstack.copy().splitStack(1), Facing.oppositeSide[i1]);

                    // CraftBukkit start - Fire event when pushing items into other inventories
                    CraftItemStack oitemstack = CraftItemStack.asCraftMirror(itemstack.cloneItemStack().a(1));
                    org.bukkit.inventory.Inventory destinationInventory;

                    //if (itemstack1 == null) {

                    // Have to special case large chests as they work oddly
                    if (iinventory instanceof InventoryLargeChest)
                    {
                        destinationInventory = new org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryDoubleChest((net.minecraft.server.v1_7_R4.InventoryLargeChest) iinventory);
                    }
                    else
                    {
                        // Cauldron start - support mod inventories, with no owners
                        try {

                            if (iinventory.getOwner().getInventory() != null) {
                                destinationInventory = iinventory.getOwner().getInventory();
                            } else {
                                // TODO: create a mod inventory for passing to the event, instead of null
                                destinationInventory = null;
                            }
                        } catch (AbstractMethodError e) { // fixes openblocks AbstractMethodError
                            if (iinventory instanceof TileEntity) {
                                try {
                                    org.bukkit.inventory.InventoryHolder holder = CaudronUtils.getOwner((TileEntity) iinventory);
                                    if (holder != null) {
                                        destinationInventory = holder.getInventory();
                                    } else {
                                        destinationInventory = null;
                                    }
                                }
                                catch (Exception e2){
                                    e2.printStackTrace();
                                    destinationInventory = null;
                                }
                            } else {
                                destinationInventory = null;
                            }
                        }
                        // Cauldron end
                    }

                    InventoryMoveItemEvent event = new InventoryMoveItemEvent(tileentitydispenser.getOwner().getInventory(), oitemstack.clone(), destinationInventory, true);
                    p_149941_1_.getServer().getPluginManager().callEvent(event);
                    //Bukkit.getServer().getPluginManager().callEvent(event);

                    if (event.isCancelled())
                    {
                        return;
                    }

                    itemstack1 = TileEntityHopper.addItem(iinventory, (ItemStack) ((Object)CraftItemStack.asNMSCopy(event.getItem())), Facing.OPPOSITE_FACING[i1]);

                    if (event.getItem().equals(oitemstack) && itemstack1 == null)
                    {
                        // CraftBukkit end
                        itemstack1 = itemstack.cloneItemStack();
                        if (--itemstack1.count == 0) {
                            itemstack1 = null;
                        }
                    } else {
                        itemstack1 = itemstack.cloneItemStack();
                    }
                } else {
                    itemstack1 = null;
                    try {
                        Field field_149947_P = self.getClass().getField("P");
                        field_149947_P.setAccessible(true);
                        IDispenseBehavior objectFromField = (IDispenseBehavior) field_149947_P.get(self);
                        itemstack1  = objectFromField.a(var5, itemstack);

                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (itemstack1 != null && itemstack1.count == 0) {
                        itemstack1 = null;
                    }
                }

                tileentitydispenser.setItem(var7, itemstack1);
            }

        }
    }
}