package net.ramixin.ramchants.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryFixedCodec;
import net.minecraft.util.collection.Weight;
import net.minecraft.util.collection.Weighted;
import net.minecraft.util.dynamic.Codecs;

public final class LinkedCurse implements Weighted {

    public static final Codec<LinkedCurse> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryFixedCodec.of(RegistryKeys.ENCHANTMENT).fieldOf("curse").forGetter(LinkedCurse::linkedEnchantment),
            Codecs.POSITIVE_INT.fieldOf("weight").forGetter(LinkedCurse::weight)
    ).apply(instance, LinkedCurse::new));

    private final RegistryEntry<Enchantment> linkedEnchantment;
    private final int weight;

    LinkedCurse(RegistryEntry<Enchantment> linkedEnchantment, int weight) {
        this.linkedEnchantment = linkedEnchantment;
        this.weight = weight;
    }

    public RegistryEntry<Enchantment> linkedEnchantment() {
        return linkedEnchantment;
    }

    public int weight() {
        return weight;
    }

    @Override
    public String toString() {
        return "LinkedCurse[" +
                "linkedEnchantment=" + linkedEnchantment + ", " +
                "weight=" + weight + ']';
    }

    @Override
    public Weight getWeight() {
        return Weight.of(weight);
    }
}
