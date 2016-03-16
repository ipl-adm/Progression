package joshie.progression.criteria.filters;

import joshie.progression.helpers.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class FilterBlockOre extends FilterItemOre {
    public FilterBlockOre() {
        super("blockOre", 0xFF663300);
    }

    @Override
    public boolean matches(ItemStack check) {
        Block block = ItemHelper.getBlock(check);
        return block == null ? false : super.matches(check);
    }
}
