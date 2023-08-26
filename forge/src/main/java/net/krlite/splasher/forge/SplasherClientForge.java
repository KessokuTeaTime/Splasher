package net.krlite.splasher.forge;

import dev.architectury.platform.forge.EventBuses;
import net.krlite.splasher.Splasher;
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
    }
}
