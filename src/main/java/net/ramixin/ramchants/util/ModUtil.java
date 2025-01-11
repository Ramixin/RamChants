package net.ramixin.ramchants.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public interface ModUtil {

    static void applyKnockback(float val, LivingEntity entity, float yaw) {
        entity.velocityDirty = true;
        entity.velocityModified = true;
        entity.takeKnockback(val * 0.5, MathHelper.sin(yaw * ((float) Math.PI / 180F)), (-MathHelper.cos((entity.getYaw() + 180) * ((float) Math.PI / 180F))));
    }

    static Vec3d toVector(float pitch, float yaw) {
        return new Vec3d(Math.cos(yaw) * Math.cos(pitch), Math.sin(yaw) * Math.cos(pitch), Math.sin(pitch));
    }

    static float[] toPitchAndYaw(Vec3d vec) {
        return new float[]{
                (float) Math.atan2(vec.y, Math.sqrt(vec.x*vec.x + vec.z*vec.z)),
                (float) Math.atan2(vec.z, vec.x)
        };
    }

    static float[] getRecoilPitchAndYaw(float pitch, float yaw) {
        return toPitchAndYaw(toVector(pitch, yaw).multiply(-1));
    }

}
