package net.krlite.splasher.forge;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientScreenInputEvent;
import dev.architectury.platform.forge.EventBuses;

import net.krlite.bounced.Bounced;
import net.krlite.splasher.Splasher;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Splasher.ID)
public class SplasherClientForge {
    public SplasherClientForge() {
        EventBuses.registerModEventBus(Splasher.ID, FMLJavaModLoadingContext.get().getModEventBus());
        IEventBus modEventBus = EventBuses.getModEventBus(Splasher.ID).get();

        modEventBus.addListener(this::onInitializeClient);
    }

    public void onInitializeClient(FMLClientSetupEvent event) {
        Splasher.onInitClient();

        ClientGuiEvent.INIT_POST.register((screen, screenAccess) -> {
            if (screen instanceof TitleScreen) {
                ClientScreenInputEvent.MOUSE_CLICKED_POST.register((client, currentScreen, mouseX, mouseY, button) -> {
                    double scaledWidth = screenAccess.getScreen().width;
                    if (Splasher.isBouncedLoaded) {
                        // Linkage with Bounced
                        mouseY -= Bounced.primaryPos();
                    }

                    if (Splasher.isMouseHovering(scaledWidth, mouseX, mouseY) && Splasher.CONFIG.randomRate.onClick()) {
                        Splasher.push();
                        Splasher.playClickingSound();
                    }
                    return EventResult.pass();
                });
            }
        });
    }
}
