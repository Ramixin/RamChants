package net.ramixin.ramchants.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.DynamicRegistryManager;

public class RamchantsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
    }

    public static DynamicRegistryManager getRegistryManager() {
        if(MinecraftClient.getInstance().world == null) throw new IllegalStateException("attempted to get RegistryManager before world load");
        return MinecraftClient.getInstance().world.getRegistryManager();
    }
}
