package net.ramixin.ramchants.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.ramixin.ramchants.Ramchants;
import net.ramixin.ramchants.enchantments.effects.ModEnchantmentEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(RangedWeaponItem.class)
public class RangedWeaponItemMixin {

    @ModifyVariable(method = "shootAll", at = @At("HEAD"), index = 5, argsOnly = true)
    private List<ItemStack> applyInaccuracyEnchantmentEffect(List<ItemStack> original, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ServerWorld world) {
        if(!EnchantmentHelper.hasAnyEnchantmentsWith(stack, ModEnchantmentEffects.INACCURACY)) return original;
        int keep = world.getRandom().nextBetween(0,original.size()-1);
        List<ItemStack> rebuiltList = new ArrayList<>();
        for(int i = 0; i < original.size(); ++i) rebuiltList.add(i == keep ? original.get(i) : ItemStack.EMPTY);
        Ramchants.LOGGER.info("after: {}", rebuiltList);
        return rebuiltList;
    }

    @WrapOperation(method = "shootAll", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"))
    private <E> E allowRightArrowAngle(List<E> instance, int i, Operation<E> original, @Local(ordinal = 5) LocalFloatRef localI) {
        E value = original.call(instance, i);
        if(!(value instanceof ItemStack stack)) throw new ClassCastException("Expected type ItemStack");
        if(stack.isEmpty()) localI.set(-localI.get());
        return value;
    }

}
