package net.krlite.splasher.fabric;


import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientScreenInputEvent;
import net.fabricmc.api.ClientModInitializer;
import net.krlite.bounced.Bounced;
import net.krlite.splasher.Splasher;
import net.minecraft.client.gui.screen.TitleScreen;

public class SplasherClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Splasher.onInitClient();

        ClientGuiEvent.INIT_POST.register((screen, screenAccess) -> {
            if (screen instanceof TitleScreen) {
                ClientScreenInputEvent.MOUSE_CLICKED_POST.register((client, currentScreen, mouseX, mouseY, button) -> {
                    if (Splasher.isBouncedLoaded) {
                        // Linkage with Bounced
                        mouseY -= Bounced.primaryPos();
                    }

                    if (Splasher.isMouseHovering(screenAccess.getScreen().width, mouseX, mouseY) && Splasher.CONFIG.randomRate.onClick()) {
                        Splasher.push();
                        Splasher.playClickingSound();
                    }
                    return EventResult.pass();
                });
            }
        });
    }
}
