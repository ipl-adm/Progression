package joshie.progression.crafting.actions;

import java.util.Iterator;

import joshie.progression.crafting.ActionType;
import joshie.progression.handlers.CraftingEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ActionHarvestDrop extends ActionForgeEvent {
    public ActionHarvestDrop(String name) {
        super(name);
    }

    @SubscribeEvent
    public void onHarvestDrop(HarvestDropsEvent event) {
        EntityPlayer player = event.harvester;
        if (player != null) {
            Iterator<ItemStack> it = event.drops.iterator();
            while (it.hasNext()) {
                ItemStack stack = it.next();
                if (CraftingEvents.isEventCancelled(player, ActionType.HARVESTDROP, player.getCurrentEquippedItem(), stack)) {
                    it.remove();
                }
            }
        }
    }
}
