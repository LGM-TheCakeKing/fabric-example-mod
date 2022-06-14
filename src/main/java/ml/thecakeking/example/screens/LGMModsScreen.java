package ml.thecakeking.example.screens;

import ml.thecakeking.example.LGMMod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.LiteralText;

public class LGMModsScreen extends Screen
{
    private  final Screen Parent;
    private final GameOptions Settings;
    public LGMModsScreen(Screen parent, GameOptions gameOptions) {
        super(new LiteralText("LGM Mods Menu"));
        this.Parent = parent;
        this.Settings = gameOptions;
    }


    protected void init() { //Make a list of button, Name main boolean
        //Test button
        this.addDrawableChild(new ButtonWidget(210, 210, 100, 20,
                new LiteralText("Test Button"), x -> {
            x.setMessage(new LiteralText("Wohhuo! Du trykkede på mig!"));
        }));

        //Auto fishing button
        this.addDrawableChild(new ButtonWidget(24, 0, 128, 20,
                GetButtonTextIsEnable("Auto Fishing", LGMMod.autoFishingEnabled), x -> {
            LGMMod.autoFishingEnabled = !LGMMod.autoFishingEnabled;
            x.setMessage(GetButtonTextIsEnable("Auto fishing",  LGMMod.autoFishingEnabled));
        }));

        //Auto swapper button
        this.addDrawableChild(new ButtonWidget(24, 25, 128, 20,
                GetButtonTextIsEnable("Auto swapper", LGMMod.autoSwapperEnabled), x -> {
            LGMMod.autoSwapperEnabled = !LGMMod.autoSwapperEnabled;
            x.setMessage(GetButtonTextIsEnable("Auto swapper",  LGMMod.autoSwapperEnabled));
        }));

        //Back button to main game screen
        this.addDrawableChild(new ButtonWidget(100, 100, 100, 20,
                ScreenTexts.BACK, x -> {
            this.client.setScreen(this.Parent);
        }));
    }

    private LiteralText GetButtonTextIsEnable(String functionName, boolean isEnabled) {
                return new LiteralText(functionName + " is " + (isEnabled ? "enable" : "disable"));
    }
}
