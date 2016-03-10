package joshie.progression.criteria.triggers;

import java.util.UUID;

import com.google.gson.JsonObject;

import joshie.progression.api.ITriggerData;
import joshie.progression.criteria.triggers.data.DataCount;
import joshie.progression.gui.fields.TextField;
import joshie.progression.helpers.JSONHelper;

public abstract class TriggerBaseCounter extends TriggerBase {
    public int amount = 1;
    
	public TriggerBaseCounter(String name, int color) {
		super(name, color, "count");
        editList.add(new TextField("amount", this));
	}

	@Override
	public boolean isCompleted(ITriggerData iTriggerData) {
		return ((DataCount)iTriggerData).count >= amount;
	}
	
	@Override
	public boolean onFired(UUID uuid, ITriggerData iTriggerData, Object... data) {	
	    if (!super.onFired(uuid, iTriggerData, data)) return false;
		DataCount triggerData = (DataCount)iTriggerData;
		if (canIncrease(data)) {
			triggerData.count++;
		}
		
		return true;
	}
	
	@Override
    public void readFromJSON(JsonObject data) {
	    super.readFromJSON(data);
	    amount = JSONHelper.getInteger(data, "amount", amount);
    }

    @Override
    public void writeToJSON(JsonObject data) {
        super.writeToJSON(data);
        JSONHelper.setInteger(data, "amount", amount, 1);
    }

	protected abstract boolean canIncrease(Object... data);
}
