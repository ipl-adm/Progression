package joshie.crafting.crafting;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import joshie.crafting.api.CraftingAPI;
import joshie.crafting.api.ICriteria;
import joshie.crafting.api.crafting.CraftingType;
import joshie.crafting.api.crafting.ICrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CrafterHuman implements ICrafter {
	//List of technologies this human has unlocked
	private final UUID uuid;
	private EntityPlayer player;
	
	public CrafterHuman(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public boolean canUseItemForCrafting(ItemStack stack) {
		Collection<ICriteria> conditions = CraftingAPI.crafting.getCriteria(CraftingType.CRAFTING, stack);
		if (conditions.size() < 1) return true;
		Set<ICriteria> completed = CraftingAPI.players.getPlayerData(uuid).getMappings().getCompletedCriteria();
		if (completed.containsAll(conditions)) {
			return true;
		} else return false;
	}

	@Override
	public boolean canCraftItem(CraftingType type, ItemStack stack) {
		Collection<ICriteria> conditions = CraftingAPI.crafting.getCriteria(type, stack);
		if (conditions.size() < 1) return true;
		Set<ICriteria> completed = CraftingAPI.players.getPlayerData(uuid).getMappings().getCompletedCriteria();
		if (completed.containsAll(conditions)) {
			return true;
		} else return false;
	}

	@Override
	public boolean canCraftWithAnything() {
		return false;
	}

	@Override
	public boolean canRepairItem(ItemStack stack) {
		return true;
	}
}
