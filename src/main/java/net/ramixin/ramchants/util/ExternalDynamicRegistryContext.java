package net.ramixin.ramchants.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resource.LifecycledResourceManager;
import net.minecraft.server.MinecraftServer;

import java.util.Optional;

public class ExternalDynamicRegistryContext implements ServerLifecycleEvents.ServerStopping, ServerLifecycleEvents.ServerStarted, ServerLifecycleEvents.EndDataPackReload {

    private static final ExternalDynamicRegistryContext INSTANCE = new ExternalDynamicRegistryContext();

    private DynamicRegistryManager registryManager;

    @Override
    public void endDataPackReload(MinecraftServer server, LifecycledResourceManager resourceManager, boolean success) {
        registryManager = server.getRegistryManager();
    }

    @Override
    public void onServerStarted(MinecraftServer server) {
        registryManager = server.getRegistryManager();
    }

    @Override
    public void onServerStopping(MinecraftServer server) {
        registryManager = null;
    }

    public Optional<DynamicRegistryManager> getRegistryManager() {
        return Optional.ofNullable(registryManager);
    }

    public static ExternalDynamicRegistryContext getInstance() {
        return INSTANCE;
    }

    public static void onInitialize() {
        ServerLifecycleEvents.SERVER_STOPPING.register(INSTANCE);
        ServerLifecycleEvents.SERVER_STARTED.register(INSTANCE);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(INSTANCE);
    }
}
