package ml.thecakeking.example.handler;

import ml.thecakeking.example.LGMMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class ChatHandler
{
    private MinecraftClient _client;
    public ChatHandler(MinecraftClient client) {
        this._client = client;
    }

    public void LGMCommandHandler(String commandText)
    {
        commandText = commandText.substring(5);
        LGMMod.LOGGER.info(commandText);
        if (commandText.startsWith("auto-miner")) {
            AutoMiner(commandText);
        }
    }

    private boolean AutoMinerEnabled = false;
    private void AutoMiner(String commandText) {
        LGMMod.LOGGER.info("AUTO MINER IS NOW enable: " + commandText);
        AutoMinerEnabled = true;

        BlockPos blockPos = _client.player.getCameraBlockPos();



            //_client.interactionManager.(blockPos, d);

    }
}
