package net.ramixin.ramchants.enchantments.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentValueEffect;
import net.minecraft.util.math.random.Random;

public record AddBinomialEnchantmentEffect(EnchantmentLevelBasedValue chance) implements EnchantmentValueEffect {

    public static final MapCodec<AddBinomialEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("chance").forGetter(AddBinomialEnchantmentEffect::chance)).apply(instance, AddBinomialEnchantmentEffect::new));

    @Override
    public float apply(int level, Random random, float inputValue) {
        float f = this.chance.getValue(level);
        int i = 0;

        for(int j = 0; (float)j < inputValue; ++j) {
            if (random.nextFloat() < f) {
                ++i;
            }
        }

        return inputValue + i;
    }

    @Override
    public MapCodec<? extends EnchantmentValueEffect> getCodec() {
        return CODEC;
    }
}
