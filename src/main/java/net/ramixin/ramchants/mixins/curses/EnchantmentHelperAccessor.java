package net.ramixin.ramchants.mixins.curses;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EnchantmentHelper.class)
public interface EnchantmentHelperAccessor {

    @Invoker("forEachEnchantment")
    static void invokeForEachEnchantment(ItemStack stack, EnchantmentHelper.Consumer consumer) {
        throw new AssertionError();
    }

}
