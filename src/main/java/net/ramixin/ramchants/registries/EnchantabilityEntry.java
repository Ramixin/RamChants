package net.ramixin.ramchants.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EnchantableComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ramixin.ramchants.enchantments.ModHelper;
import net.ramixin.ramchants.items.ModComponents;
import net.ramixin.ramchants.util.ItemEnchantmentsComponentDuck;

import java.util.OptionalInt;

public final class EnchantabilityEntry {

    public static final Codec<EnchantabilityEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("value").forGetter(EnchantabilityEntry::baseValue),
            Precedence.CODEC.fieldOf("precedence").forGetter(EnchantabilityEntry::getPrecedence)
    ).apply(instance, EnchantabilityEntry::new));
    public static final EnchantabilityEntry DEFAULT = new EnchantabilityEntry(0, Precedence.LOOSE);

    private final int baseValue;

    private final Precedence precedence;

    private EnchantabilityEntry(int baseValue, Precedence precedence) {
        this.baseValue = baseValue;
        this.precedence = precedence;
    }

    public int getEnchantmentsCost(ItemStack stack) {
        OptionalInt count = ItemEnchantmentsComponentDuck.get(ModHelper.getEnchantments(stack)).ramChants$getEnchantmentLevels().intStream().reduce(Integer::sum);
        if (count.isEmpty()) return 0;
        return count.getAsInt();
    }

    public int getGrindsCost(ItemStack stack) {
        return stack.getOrDefault(ModComponents.TIMES_GRINDED, 0);
    }

    public static EnchantabilityEntry getEnchantability(DynamicRegistryManager manager, ItemStack stack) {
        Identifier id = Registries.ITEM.getId(stack.getItem());
        Registry<EnchantabilityEntry> registry = manager.getOrThrow(ModRegistries.ENCHANTABILITY);
        EnchantabilityEntry entry = registry.get(id);
        EnchantableComponent comp = stack.get(DataComponentTypes.ENCHANTABLE);
        if(entry == null && comp == null) return DEFAULT;
        else if(entry == null) return new EnchantabilityEntry(comp.value(), Precedence.NEUTRAL);
        else if(comp == null) return entry;
        else {
            EnchantabilityEntry compEntry = new EnchantabilityEntry(comp.value(), Precedence.NEUTRAL);
            return switch (entry.precedence) {
                case LOOSE -> compEntry;
                case NEUTRAL -> {
                    EnchantableComponent defaultComp = stack.getDefaultComponents().get(DataComponentTypes.ENCHANTABLE);
                    if (defaultComp == comp) yield entry;
                    else yield compEntry;
                }
                case STRICT -> entry;
            };
        }
    }

    public int baseValue() {
        return baseValue;
    }

    public Precedence getPrecedence() {
        return precedence;
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
