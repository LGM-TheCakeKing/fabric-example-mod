package ml.thecakeking.example.mixin;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import ml.thecakeking.example.LGMMod;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    //@Inject(at = @At("HEAD"), method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", cancellable = true)
    public void send(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> callback, CallbackInfo ci) throws NoSuchFieldException {
        if (packet.getClass().getName().equals("net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$PositionAndOnGround")) return;
        if (packet.getClass().getName().equals("net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$LookAndOnGround")) return;
        if (packet.getClass().getName().equals("net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$Full")) return;
        if (packet.getClass().getName().equals("net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket")) return;
        if (packet.getClass().getName().equals("net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket")) return;
        if (packet.getClass().getName().equals("net.minecraft.network.packet.c2s.play.HandSwingC2SPacket")) return;


        LGMMod.LOGGER.info("Package: " + packet.getClass().getName());
    }
}

//PlayerActionC2SPacket destroying blocks
//PlayerInteractBlockC2SPacket placing block
//ClickSlotC2SPacket clicking slot in inv, can use to move`?