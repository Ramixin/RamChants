package net.ramixin.ramchants.registries;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum Precedence implements StringIdentifiable {


    LOOSE("loose"), // Used if no component is found
    NEUTRAL("neutral"), // Used unless default-component or lower is found
    STRICT("strict") // Always used,
    ;

    private final String id;

    public static final Codec<Precedence> CODEC = StringIdentifiable.createCodec(Precedence::values);

    Precedence(String id) {
        this.id = id;
    }

    @Override
    public String asString() {
        return id;
    }
}
