package net.ramixin.ramchants.mixins.etable;

import net.minecraft.component.type.ItemEnchantmentsComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.BiFunction;

@Mixin(ItemEnchantmentsComponent.Builder.class)
public class BuilderMixin {

    @ModifyArg(method = "add", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Object2IntOpenHashMap;merge(Ljava/lang/Object;ILjava/util/function/BiFunction;)I", remap = false), index = 2)
    private BiFunction<? super Integer, ? super Integer, ? extends Integer> stackEnchantmentsOnAdd(BiFunction<? super Integer, ? super Integer, ? extends Integer> original) {
        return (BiFunction<Integer, Integer, Integer>) Integer::sum;
    }

}
