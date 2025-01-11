package net.ramixin.ramchants.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EnchantableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.ramixin.ramchants.items.ModComponents;
import net.ramixin.ramchants.util.ItemEnchantmentsComponentDuck;

import java.util.OptionalInt;

public final class EnchantabilityEntry {

    public static final Codec<EnchantabilityEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("value").forGetter(EnchantabilityEntry::baseValue)
    ).apply(instance, EnchantabilityEntry::new));
    public static final EnchantabilityEntry DEFAULT = new EnchantabilityEntry(0);

    private final int baseValue;

    private EnchantabilityEntry(int baseValue) {
        this.baseValue = baseValue;
    }

    public int getEnchantmentsCost(ItemStack stack) {
        OptionalInt count = ItemEnchantmentsComponentDuck.get(stack.getEnchantments()).ramChants$getEnchantmentLevels().intStream().reduce(Integer::sum);
        if (count.isEmpty()) return 0;
        return count.getAsInt();
    }

    public int getGrindsCost(ItemStack stack) {
        return stack.getOrDefault(ModComponents.TIMES_GRINDED, 0);
    }

    public static EnchantabilityEntry getEnchantability(DynamicRegistryManager manager, ItemStack stack) {
        Identifier id = Registries.ITEM.getId(stack.getItem());
        EnchantabilityEntry entry = manager.getOrThrow(ModRegistries.ENCHANTABILITY).get(id);
        if(entry != null) return entry;
        EnchantableComponent enchantabilityComp = stack.get(DataComponentTypes.ENCHANTABLE);
        if (enchantabilityComp != null) return new EnchantabilityEntry(enchantabilityComp.value());
        return DEFAULT;
    }

    public int baseValue() {
        return baseValue;
    }

    public int actualValue(ItemStack stack) {
        return actualBaseValue(stack) - getEnchantmentsCost(stack);
    }

    public int actualBaseValue(ItemStack stack) {
        return baseValue  - getGrindsCost(stack);
    }

    @Override
    public String toString() {
        return "EnchantabilityEntry[" +
                "baseValue=" + baseValue + ']';
    }

}
