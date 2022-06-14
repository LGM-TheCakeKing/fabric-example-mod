package ml.thecakeking.example.mixin;

import ml.thecakeking.example.LGMMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    private Item[] itemsInHands;
    @Inject(method = "eatFood", at = @At("HEAD"))
    public void eatFoodHead(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (!LGMMod.autoSwapperEnabled) return;

        ArrayList<ItemStack> hands = (ArrayList<ItemStack>) MinecraftClient.getInstance().player.getItemsHand();
        itemsInHands = new Item[] { hands.get(0).getItem(), hands.get(1).getItem() };
    }
    @Inject(method = "eatFood", at = @At("TAIL"))
    public void eatFoodTail(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (!LGMMod.autoSwapperEnabled) return;
        if (!stack.toString().equals("0 air")) return;

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        ArrayList<ItemStack> hands = (ArrayList<ItemStack>) player.getItemsHand();
        for(int i = 0; i < 2; i++) {
            if (!hands.get(i).getItem().equals(itemsInHands[i])) {
                if (player.getInventory().contains(new ItemStack(itemsInHands[i]))) {
                    int slot = player.getInventory().indexOf(new ItemStack(itemsInHands[i]));
                    MinecraftClient.getInstance().interactionManager.pickFromInventory(slot);
                }
            }
        }
    }
}
