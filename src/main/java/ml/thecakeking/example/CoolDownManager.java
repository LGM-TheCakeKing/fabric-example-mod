package ml.thecakeking.example;

import ml.thecakeking.example.componts.ActionId;
import ml.thecakeking.example.componts.CoolDownEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;

public class CoolDownManager {
    private MinecraftClient _client;
    public CoolDownManager(MinecraftClient client) {
        this._client = client;
    }

    private int totalCountTicks = 0;
    public void tick() {
        if(coolDownEvents.isEmpty()) return;
        totalCountTicks++;

        for (CoolDownEvent coolDownEvent : coolDownEvents) {
            if (coolDownEvent.TickDelay == totalCountTicks) {
                if (coolDownEvent.ActionId == ActionId.AutoFishing) {
                    AutoFishing();
                }
                RemoveCompletedCoolDownEvent();
            }
        }
    }

    private List<CoolDownEvent> coolDownEvents = new ArrayList<>();
    public void AddCoolDownEvent(int tickDelay, ActionId actionId) {
        coolDownEvents.add(new CoolDownEvent(totalCountTicks + tickDelay, actionId));
    }
    private void RemoveCompletedCoolDownEvent() {
        List<CoolDownEvent> newCoolDownEvents = new ArrayList<>();
        for (CoolDownEvent coolDownEvent : coolDownEvents) {
            if (coolDownEvent.TickDelay > totalCountTicks) {
                newCoolDownEvents.add(coolDownEvent);
            }
        }
        coolDownEvents = newCoolDownEvents;
    }

    private void AutoFishing() {
        _client.interactionManager.interactItem(_client.player, _client.world, Hand.MAIN_HAND);
        _client.player.swingHand(Hand.MAIN_HAND);
    }
}
