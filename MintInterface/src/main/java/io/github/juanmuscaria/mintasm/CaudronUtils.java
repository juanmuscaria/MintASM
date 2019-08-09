package io.github.juanmuscaria.mintasm;

import net.minecraft.server.v1_7_R4.TileEntity;
import org.bukkit.inventory.InventoryHolder;

public class CaudronUtils {
    public static InventoryHolder getOwner(TileEntity tileentity)
    {
        org.bukkit.block.BlockState state = tileentity.getWorld().getWorld().getBlockAt(tileentity.x, tileentity.y, tileentity.z).getState();

        if (state instanceof InventoryHolder)
        {
            return (InventoryHolder) state;
        }

        return null;
    }
}
