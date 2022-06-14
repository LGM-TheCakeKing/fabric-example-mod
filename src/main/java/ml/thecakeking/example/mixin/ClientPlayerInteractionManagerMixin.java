package ml.thecakeking.example.mixin;

import ca.weblite.objc.Client;
import ml.thecakeking.example.LGMMod;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow @Final private MinecraftClient client;
    @Shadow private ItemStack selectedStack;
    private Item blockInHand;
    @Inject(method = "interactBlock", at = @At("HEAD"))
    public void interactBlockHead(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (!LGMMod.autoSwapperEnabled) return;

        ArrayList<ItemStack> hands = (ArrayList<ItemStack>) player.getItemsHand();
        if (hand.equals(Hand.MAIN_HAND)) blockInHand = hands.get(0).getItem();
        if (hand.equals(Hand.OFF_HAND)) blockInHand = hands.get(1).getItem();
    }
    @Inject(method = "interactBlock", at = @At("TAIL"))
    public void interactBlockTail(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (!LGMMod.autoSwapperEnabled) return;

        for (ItemStack itemHand: player.getItemsHand()) {
            if (itemHand.toString().equals("0 air")) {
                if (player.getInventory().contains(new ItemStack(blockInHand))) {
                    int slot = player.getInventory().indexOf(new ItemStack(blockInHand));
                    MinecraftClient.getInstance().interactionManager.pickFromInventory(slot);
                }
            }
        }
    }

    private Item itemInHand;
    @Inject(method = "interactItem", at = @At("HEAD"))
    public void interactItemHead(PlayerEntity player, World world, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!LGMMod.autoSwapperEnabled) return;

        ArrayList<ItemStack> hands = (ArrayList<ItemStack>) player.getItemsHand();
        if (hand.equals(Hand.MAIN_HAND)) itemInHand = hands.get(0).getItem();
        if (hand.equals(Hand.OFF_HAND)) itemInHand = hands.get(1).getItem();
    }
    @Inject(method = "interactItem", at = @At("TAIL"))
    public void interactItemTail(PlayerEntity player, World world, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!LGMMod.autoSwapperEnabled) return;

        for (ItemStack itemHand: player.getItemsHand()) {
            if (itemHand.toString().equals("0 air")) {
                if (player.getInventory().contains(new ItemStack(itemInHand))) {
                    int slot = player.getInventory().indexOf(new ItemStack(itemInHand));
                    MinecraftClient.getInstance().interactionManager.pickFromInventory(slot);
                }
            }
        }
    }

    private int NameHere = 10;
    @Inject(method = "sendPlayerAction", at = @At("TAIL"))
    public void sendPlayerActionHead(PlayerActionC2SPacket.Action action, BlockPos pos, Direction direction, CallbackInfo ci) {
        if (!LGMMod.autoSwapperEnabled) return;
        if (!selectedStack.isDamageable()) return;
        if (!action.equals(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK)) return;
        if ((selectedStack.getMaxDamage()-selectedStack.getDamage()) > NameHere) return;
        SwapToolIfMoreInInventory();
    }

    private void SwapToolIfMoreInInventory() {
        ClientPlayerEntity player = client.player;
        PlayerInventory inventory = player.getInventory();
        int selectedToolIndex = GetIndexInstanceOfItem(selectedStack.getItem());
        for (int i = 0; i < inventory.main.size(); i++) {
            ItemStack item = inventory.main.get(i);
            if (GetIndexInstanceOfItem(item.getItem()) == selectedToolIndex) {
                if ((item.getMaxDamage()-item.getDamage()) > NameHere*2) {
                    LGMMod.LOGGER.info("slot Item is instance of the selected item can has more durability");
                    MinecraftClient.getInstance().interactionManager.pickFromInventory(i);
                    this.selectedStack = item;
                    return;
                }
            }
        }
    }

    private int GetIndexInstanceOfItem(Item item) {
        if (item instanceof PickaxeItem)    return 0;
        if (item instanceof SwordItem)      return 1;
        if (item instanceof AxeItem)        return 2;
        if (item instanceof ShovelItem)     return 3;
        return -1;
    }
}
