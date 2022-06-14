package ml.thecakeking.example;

import ml.thecakeking.example.handler.ChatHandler;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LGMMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("LGMMods");

	public static boolean autoSwapperEnabled = false;
	public static boolean autoFishingEnabled = false;
	public static CoolDownManager CoolDownManager;
	public static ChatHandler ChatHandler;

	@Override
	public void onInitialize() { //TODO Mixin calls clean up, move logic to handler
		registerEvents();
		ChatHandler = new ChatHandler(MinecraftClient.getInstance());
		CoolDownManager = new CoolDownManager(MinecraftClient.getInstance());
		LOGGER.info("#######################################");
		LOGGER.info("#                                     #");
		LOGGER.info("#       LGM Mods has been loaded      #");
		LOGGER.info("#                                     #");
		LOGGER.info("#######################################");
	}

	private void registerEvents() {

	}
}
