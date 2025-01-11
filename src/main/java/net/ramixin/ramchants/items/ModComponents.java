package net.ramixin.ramchants.items;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;
import net.ramixin.ramchants.Ramchants;

import java.util.function.UnaryOperator;

public interface ModComponents {

    ComponentType<Integer> TIMES_GRINDED = register("times_grinded", (builder) -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.INTEGER));
    ComponentType<Boolean> SEALED = register("enchantments_sealed", (builder) -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));


    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Ramchants.id(id), (builderOperator.apply(ComponentType.builder())).build());
    }

    static void onInitialize() {

    }

}
