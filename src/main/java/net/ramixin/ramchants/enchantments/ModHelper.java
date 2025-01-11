package net.ramixin.ramchants.enchantments;

import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.random.Random;
import net.ramixin.ramchants.items.ModComponents;
import net.ramixin.ramchants.mixins.curses.EnchantmentHelperAccessor;
import net.ramixin.ramchants.registries.LinkedCursesEntry;
import net.ramixin.ramchants.util.EnchantmentDuck;
import net.ramixin.ramchants.util.ModEffectValueConsumer;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.Optional;

public interface ModHelper {

    static float getArmorEffectivenessMultiplier(ServerWorld world, ItemStack stack, Entity user, DamageSource damageSource, float defaultValue) {
        MutableFloat mutableFloat = new MutableFloat(defaultValue);
        EnchantmentHelperAccessor.invokeForEachEnchantment(stack, (enchantment, level) -> EnchantmentDuck.get(enchantment.value()).ramChants$modifyArmorEffectivenessMultiplier(world, level, stack, user, damageSource, mutableFloat));
        return mutableFloat.floatValue();
    }

    static float getGhostQuiverChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyGhostQuiverChance);
    }

    static float getKnockbackUserStrength(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyKnockbackUser);
    }

    static float getHitMissChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyMissChance);
    }

    static float getConsumeDropChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyConsumeDropChance);
    }

    static float getStagnationAmount(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyStagnationAmount);
    }

    static float getBetrayalChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyBetrayalChance);
    }

    static float getDeflectionChance(ServerWorld world, ItemStack stack) {
        return getValue(world, stack, EnchantmentDuck::ramChants$modifyDeflectionChance);
    }

    private static float getValue(ServerWorld world, ItemStack stack, ModEffectValueConsumer<ServerWorld, Integer, ItemStack, MutableFloat> function) {
        if(stack == null) return 0;
        MutableFloat mutableFloat = new MutableFloat((float) 0);
        EnchantmentHelperAccessor.invokeForEachEnchantment(stack, (enchantment, level) -> function.accept(EnchantmentDuck.get(enchantment.value()), world, level, stack, mutableFloat));
        return mutableFloat.floatValue();
    }

    static ItemEnchantmentsComponent getEnchantments(ItemStack stack) {
        return stack.getOrDefault(getComponentType(stack), ItemEnchantmentsComponent.DEFAULT);
    }

    static ComponentType<ItemEnchantmentsComponent> getComponentType(ItemStack stack) {
        return stack.getItem() == Items.ENCHANTED_BOOK ? DataComponentTypes.STORED_ENCHANTMENTS : DataComponentTypes.ENCHANTMENTS;
    }

    @SuppressWarnings("deprecation")
    static void removeEnchantment(ItemStack stack, RegistryEntry<Enchantment> entry) {
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(getEnchantments(stack));
        builder.remove(predEntry -> predEntry.matches(entry));
        stack.set(getComponentType(stack), builder.build());
    }

    static Text getEnchantmentNameWithoutLevel(RegistryEntry<Enchantment> entry) {
        MutableText mutableText = entry.value().description().copy();
        if (entry.isIn(EnchantmentTags.CURSE)) return Texts.setStyleIfAbsent(mutableText, Style.EMPTY.withColor(Formatting.RED));
        else return Texts.setStyleIfAbsent(mutableText, Style.EMPTY.withColor(Formatting.GRAY));
    }

    static Optional<RegistryEntry<Enchantment>> getCurse(DynamicRegistryManager registryManager, Random random, RegistryEntry<Enchantment> enchantment) {
        LinkedCursesEntry entry = LinkedCursesEntry.getLinkedCurses(registryManager, enchantment.getKey().orElseThrow());
        return entry.getRandomCurse(random);
    }

    static void incrementGrinds(ItemStack stack) {
        int current = stack.getOrDefault(ModComponents.TIMES_GRINDED, 0);
        stack.set(ModComponents.TIMES_GRINDED, current + 1);
    }
}
