package ml.thecakeking.example.mixin;

import ml.thecakeking.example.LGMMod;
import ml.thecakeking.example.componts.ActionId;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.FishingBobberEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @Shadow private boolean caughtFish;

    @Inject(at = @At("TAIL"), method = "onTrackedDataSet")
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo callbackInfo) {
        if (caughtFish && LGMMod.autoFishingEnabled) {
            int delay = new Random().nextInt(5) + 1;
            LGMMod.CoolDownManager.AddCoolDownEvent(delay, ActionId.AutoFishing);
            LGMMod.CoolDownManager.AddCoolDownEvent(delay + 10, ActionId.AutoFishing);
        }
    }
}
