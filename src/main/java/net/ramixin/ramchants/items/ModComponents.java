package net.ramixin.ramchants.items;

import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;
import net.minecraft.util.dynamic.Codecs;
import net.ramixin.ramchants.Ramchants;

import java.util.function.UnaryOperator;

public interface ModComponents {

    ComponentType<Integer> TIMES_GRINDED = register("times_grinded", (builder) -> builder.codec(Codecs.POSITIVE_INT).packetCodec(PacketCodecs.INTEGER));
    ComponentType<Unit> SEALED = register("enchantments_sealed", (builder) -> builder.codec(Unit.CODEC).packetCodec(PacketCodec.unit(Unit.INSTANCE)));


    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Ramchants.id(id), (builderOperator.apply(ComponentType.builder())).build());
    }

    static void onInitialize() {

    }

}
