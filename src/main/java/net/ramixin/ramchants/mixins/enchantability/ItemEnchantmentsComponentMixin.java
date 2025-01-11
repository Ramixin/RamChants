package net.ramixin.ramchants.mixins.enchantability;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.entry.RegistryEntry;
import net.ramixin.ramchants.util.ItemEnchantmentsComponentDuck;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEnchantmentsComponent.class)
public class ItemEnchantmentsComponentMixin implements ItemEnchantmentsComponentDuck {

    @Shadow @Final
    Object2IntOpenHashMap<RegistryEntry<Enchantment>> enchantments;

    @Override
    public IntCollection ramChants$getEnchantmentLevels() {
        return this.enchantments.values();
    }
}
