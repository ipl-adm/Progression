package joshie.progression.gui.editors;

import joshie.progression.api.criteria.ICriteria;
import joshie.progression.api.criteria.IRewardProvider;
import joshie.progression.api.criteria.ITriggerProvider;
import joshie.progression.gui.core.GuiList;
import joshie.progression.helpers.CollectionHelper;
import joshie.progression.network.PacketHandler;
import joshie.progression.network.PacketSelectRewards;
import joshie.progression.player.PlayerTracker;

import java.util.LinkedHashSet;
import java.util.Set;

import static joshie.progression.gui.core.GuiList.NEW_REWARD;
import static joshie.progression.gui.core.GuiList.THEME;

public class FeatureReward extends FeatureDrawable<IRewardProvider> {
    private Set<IRewardProvider> selected;

    public FeatureReward() {
        super("reward", 140, NEW_REWARD, THEME.rewardBoxGradient1, THEME.rewardBoxGradient2, THEME.rewardBoxFont, THEME.rewardBoxGradient2);
    }

    public FeatureReward setCriteria(ICriteria criteria) {
        selected = new LinkedHashSet(); //Reset the selected
        setDrawable(criteria.getRewards());
        return this;
    }

    public Set<IRewardProvider> getSelected() {
        return selected;
    }

    @Override
    public int drawSpecial(IRewardProvider drawing, int offsetX, int offsetY, int mouseOffsetX, int mouseOffsetY) {
        boolean allTrue = true;
        for (ITriggerProvider provider: drawing.getCriteria().getTriggers()) {
            if (!provider.getProvided().isCompleted()) allTrue = false;
        }

        if (allTrue) {
            if (selected.contains(drawing) || !drawing.mustClaim()) {
                offset.drawGradient(offsetX, offsetY, 1, 2, drawing.getWidth(GuiList.MODE) - 1, 75, 0x33222222, 0x00CCCCCC, 0x00000000);
            }
        }

        return super.drawSpecial(drawing, offsetX, offsetY, mouseOffsetX, mouseOffsetY);
    }

    @Override
    public boolean clickSpecial(IRewardProvider provider, int mouseOffsetX, int mouseOffsetY) {
        if (mouseOffsetY < 2 || mouseOffsetY > 73) return false;
        for (ITriggerProvider trigger: provider.getCriteria().getTriggers()) {
            if (!trigger.getProvided().isCompleted()) return false;
        }

        ICriteria criteria = provider.getCriteria();
        if (!criteria.canRepeatInfinite() && PlayerTracker.getClientPlayer().getMappings().getCriteriaCount(criteria) >= criteria.getRepeatAmount()) return false;

        if (mouseOffsetX > 0 && mouseOffsetX < provider.getWidth(GuiList.MODE) && provider.mustClaim()) {
            //Click processed as this item must be claimed, now we check the side of selected, vs other things
            if (selected.contains(provider)) {
                CollectionHelper.remove(selected, provider);
                return true;
            } //If we already had it, screw validation

            int maximum = criteria.givesAllRewards() ? criteria.getRewards().size(): criteria.getAmountOfRewards();
            int standard = 0;
            for (IRewardProvider reward: criteria.getRewards()) {
                if (!reward.mustClaim()) standard++;
            }

            int current = selected.size() + standard;
            if (current < maximum) {
                selected.add(provider);
                current++;
            }

            if (current >= maximum) { //TODO: Automatic claiming, replace wth a button
                PacketHandler.sendToServer(new PacketSelectRewards(selected));
                selected = new LinkedHashSet(); //Reset the hashset
            }
        }

        return false;
    }
}
