package joshie.progression.criteria.rewards;

import java.util.List;
import java.util.UUID;

import org.lwjgl.input.Mouse;

import joshie.progression.Progression;
import joshie.progression.api.IFilter;
import joshie.progression.api.IHasEventBus;
import joshie.progression.api.ISetterCallback;
import joshie.progression.api.fields.IField;
import joshie.progression.api.fields.ISpecialFieldProvider;
import joshie.progression.api.fields.ISpecialFieldProvider.DisplayMode;
import joshie.progression.crafting.ActionType;
import joshie.progression.crafting.CraftingRegistry;
import joshie.progression.gui.fields.ItemFilterFieldPreview;
import joshie.progression.gui.newversion.GuiItemFilterEditor;
import joshie.progression.helpers.MCClientHelper;
import joshie.progression.lib.GuiIDs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;

public abstract class RewardBaseAction extends RewardBaseItemFilter implements ISetterCallback, ISpecialFieldProvider, IHasEventBus {
    protected ActionType type = ActionType.CRAFTING;
    public boolean disableUsage = true;
    public boolean disableCrafting = true;
    public boolean disableGeneral = true;

    /** Moving actions to be seperated from one another **/
    public RewardBaseAction(String name, int color, ActionType type) {
        super(name, color);
        this.type = type;
    }

    @Override
    public boolean shouldReflectionSkipField(String name) {
        return name.equals("filters");
    }

    @Override
    public void addSpecialFields(List<IField> fields, DisplayMode mode) {
        if (mode == DisplayMode.EDIT) fields.add(new ItemFilterFieldPreview("filters", this, 25, 30, 2.8F));
    }

    @Override
    public EventBus getEventBus() {
        return MinecraftForge.EVENT_BUS;
    }

    @Override
    public void reward(UUID uuid) {}

    @Override
    public void onAdded() {
        CraftingRegistry.addRequirement(type, criteria, filters, disableUsage, disableCrafting, disableGeneral);
    }

    @Override
    public void onRemoved() {
        CraftingRegistry.removeRequirement(type, criteria, filters, disableUsage, disableCrafting, disableGeneral);
    }

    @Override
    public boolean setField(String fieldName, Object object) {
        if (fieldName.equals("filters")) {
            //Close the open gui so we avoid fucking with shit
            int x = Mouse.getX();
            int y = Mouse.getY();
            MCClientHelper.getPlayer().closeScreen();
            GuiItemFilterEditor.INSTANCE.switching = true;

            onRemoved();
            filters = (List<IFilter>) object;
            onAdded();

            //Reopen the gui

            MCClientHelper.getPlayer().openGui(Progression.instance, GuiIDs.ITEM, MCClientHelper.getWorld(), 0, 0, 0);
            Mouse.setCursorPosition(x, y);
            return true;
        }

        return false;
    }

    @Override
    public void addTooltip(List list) {
        list.add(EnumChatFormatting.WHITE + "Allow " + type.getDisplayName());
        ItemStack stack = preview == null ? BROKEN : preview;
        list.add(stack.getDisplayName());
    }
}
