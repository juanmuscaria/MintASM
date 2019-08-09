package io.github.juanmuscaria.mintasm;

import net.minecraft.server.v1_7_R4.TileEntity;
import org.bukkit.inventory.InventoryHolder;

//Uma cópia direta co caudron utils, já que ele estará ofuscado na hora de acessar ele.
class CaudronUtils {
    static InventoryHolder getOwner(TileEntity tileentity)
    {
        org.bukkit.block.BlockState state = tileentity.getWorld().getWorld().getBlockAt(tileentity.x, tileentity.y, tileentity.z).getState();
        if (state instanceof InventoryHolder)
        {
            return (InventoryHolder) state;
        }

        return null;
    }
}
