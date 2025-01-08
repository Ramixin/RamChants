package net.ramixin.ramchants;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public interface ModSounds {

    SoundEvent GHOST_QUIVER = register("item.bow.ghost_quiver");

    private static SoundEvent register(String id) {
        Identifier identifier = Ramchants.id(id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    static void onInitialize() {}
}
