package net.ramixin.ramchants.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public interface ModUtil {

    static void applyKnockback(float val, LivingEntity entity) {
        entity.velocityDirty = true;
        entity.velocityModified = true;
        entity.takeKnockback(val * 0.5, MathHelper.sin((entity.getYaw() + 180) * ((float) Math.PI / 180F)), (-MathHelper.cos((entity.getYaw() + 180) * ((float) Math.PI / 180F))));
    }

}
