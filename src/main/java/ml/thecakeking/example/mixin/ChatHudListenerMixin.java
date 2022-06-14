package ml.thecakeking.example.mixin;

import ml.thecakeking.example.LGMMod;
import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ChatHudListener.class)
public abstract class ChatHudListenerMixin {

    //Can also be used to check if player name is mention in chat :O
    //@Inject(method = "onChatMessage", at = @At("HEAD"))//Can be used for filter incoming text message
    public void onChatMessage(MessageType type, Text message, UUID sender, CallbackInfo ci)
    {
        //LGMMod.LOGGER.info("onChatMessage: Text getString: " + message.getString());
    }
}
