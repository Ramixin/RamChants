package net.ramixin.ramchants.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(EntityAttributes.class)
public abstract class EntityAttributesMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Ljava/lang/String;DDD)Lnet/minecraft/entity/attribute/ClampedEntityAttribute;"))
    private static ClampedEntityAttribute changeArgs(String string, double fallback, double min, double max, Operation<ClampedEntityAttribute> original) {
        switch(string) {
            case "attribute.name.submerged_mining_speed", "attribute.name.water_movement_efficiency" -> min = -1;
        }
        return original.call(string, fallback, min, max);
    }

}