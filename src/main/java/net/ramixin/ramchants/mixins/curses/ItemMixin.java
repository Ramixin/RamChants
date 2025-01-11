package net.ramixin.ramchants.mixins.curses;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.ramixin.ramchants.enchantments.effects.ModEnchantmentEffects;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin {

    @WrapMethod(method = "usageTick")
    private void applyBoilingArrowEffect(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, Operation<Void> original) {
        original.call(world, user, stack, remainingUseTicks);
        if(!(stack.getItem() instanceof BowItem)) return;
        if(EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffects.BOILING_STRING)) user.setOnFireFor(1);
    }

}
