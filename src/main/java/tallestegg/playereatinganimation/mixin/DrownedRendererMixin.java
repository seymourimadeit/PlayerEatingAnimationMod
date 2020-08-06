package tallestegg.playereatinganimation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.DrownedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

@Mixin(DrownedRenderer.class)
public class DrownedRendererMixin extends AbstractZombieRenderer<DrownedEntity, DrownedModel<DrownedEntity>> 
{
	protected DrownedRendererMixin(EntityRendererManager p_i50974_1_, DrownedModel<DrownedEntity> p_i50974_2_, DrownedModel<DrownedEntity> p_i50974_3_, DrownedModel<DrownedEntity> p_i50974_4_) {
		super(p_i50974_1_, p_i50974_2_, p_i50974_3_, p_i50974_4_);
	}
	
	@Overwrite(remap = true)
    protected void applyRotations(DrownedEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) 
    {
	 float f = entityLiving.getSwimAnimation(partialTicks);
	 if (entityLiving.isElytraFlying()) {
	      super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		  float f1 = (float)entityLiving.getTicksElytraFlying() + partialTicks;
		  float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
		  if (!entityLiving.isSpinAttacking()) {
		      matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f2 * (-90.0F - entityLiving.rotationPitch)));
		  }

		 Vector3d vector3d = entityLiving.getLook(partialTicks);
		 Vector3d vector3d1 = entityLiving.getMotion();
		 double d0 = Entity.horizontalMag(vector3d1);
		 double d1 = Entity.horizontalMag(vector3d);
		 if (d0 > 0.0D && d1 > 0.0D) {
		   double d2 = (vector3d1.x * vector3d.x + vector3d1.z * vector3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
		   double d3 = vector3d1.x * vector3d.z - vector3d1.z * vector3d.x;
		   matrixStackIn.rotate(Vector3f.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
		 }
	   } else if (entityLiving.isInWater()) {
		 super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		 float f3 = entityLiving.isInWater() ? -90.0F - entityLiving.rotationPitch : -90.0F;
		 float f4 = MathHelper.lerp(f, 0.0F, f3);
		 matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f4));
         if (entityLiving.isActualySwimming()) {
		   matrixStackIn.translate(0.0D, -1.0D, (double)0.3F);
		  }
		} else {
		  super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
       }
    }
}
