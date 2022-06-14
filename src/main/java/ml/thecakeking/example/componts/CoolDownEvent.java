package ml.thecakeking.example.componts;

public class CoolDownEvent {
    public CoolDownEvent(int tickDelay, ActionId actionId) {
        this.TickDelay = tickDelay;
        this.ActionId = actionId;
    }
    public int TickDelay;
    public ActionId ActionId;
}
