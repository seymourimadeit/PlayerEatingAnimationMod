package tallestegg.playereatinganimation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.model.DrownedModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.Pose;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

@Mixin(DrownedModel.class)
public class DrownedModelMixin<T extends ZombieEntity> extends ZombieModel<T> {

	protected DrownedModelMixin(float p_i48914_1_, float p_i48914_2_, int p_i48914_3_, int p_i48914_4_) {
		super(p_i48914_1_, p_i48914_2_, p_i48914_3_, p_i48914_4_);
	}

	@Inject(at = @At("TAIL"), method = "setRotationAngles(Lnet/minecraft/entity/monster/ZombieEntity;FFFFF)V")
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
		if (entityIn.isInWater()) {
	         float f1 = limbSwing % 26.0F;
	         entityIn.setPose(Pose.SWIMMING);
	         HandSide handside = this.getMainHand(entityIn);
	         float f2 = handside == HandSide.RIGHT && this.swingProgress > 0.0F ? 0.0F : this.swimAnimation;
	         float f3 = handside == HandSide.LEFT && this.swingProgress > 0.0F ? 0.0F : this.swimAnimation;
	         if (f1 < 14.0F) {
	            this.bipedLeftArm.rotateAngleX = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleX, 0.0F);
	            this.bipedRightArm.rotateAngleX = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleX, 0.0F);
	            this.bipedLeftArm.rotateAngleY = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleY, (float)Math.PI);
	            this.bipedRightArm.rotateAngleY = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleY, (float)Math.PI);
	            this.bipedLeftArm.rotateAngleZ = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleZ, (float)Math.PI + 1.8707964F * this.getArmAngleSq(f1) / this.getArmAngleSq(14.0F));
	            this.bipedRightArm.rotateAngleZ = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleZ, (float)Math.PI - 1.8707964F * this.getArmAngleSq(f1) / this.getArmAngleSq(14.0F));
	         } else if (f1 >= 14.0F && f1 < 22.0F) {
	            float f6 = (f1 - 14.0F) / 8.0F;
	            this.bipedLeftArm.rotateAngleX = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleX, ((float)Math.PI / 2F) * f6);
	            this.bipedRightArm.rotateAngleX = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleX, ((float)Math.PI / 2F) * f6);
	            this.bipedLeftArm.rotateAngleY = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleY, (float)Math.PI);
	            this.bipedRightArm.rotateAngleY = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleY, (float)Math.PI);
	            this.bipedLeftArm.rotateAngleZ = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleZ, 5.012389F - 1.8707964F * f6);
	            this.bipedRightArm.rotateAngleZ = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleZ, 1.2707963F + 1.8707964F * f6);
	         } else if (f1 >= 22.0F && f1 < 26.0F) {
	            float f4 = (f1 - 22.0F) / 4.0F;
	            this.bipedLeftArm.rotateAngleX = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleX, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4);
	            this.bipedRightArm.rotateAngleX = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleX, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f4);
	            this.bipedLeftArm.rotateAngleY = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleY, (float)Math.PI);
	            this.bipedRightArm.rotateAngleY = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleY, (float)Math.PI);
	            this.bipedLeftArm.rotateAngleZ = this.rotLerpRad(f3, this.bipedLeftArm.rotateAngleZ, (float)Math.PI);
	            this.bipedRightArm.rotateAngleZ = MathHelper.lerp(f2, this.bipedRightArm.rotateAngleZ, (float)Math.PI);
	         }
	         this.bipedLeftLeg.rotateAngleX = MathHelper.lerp(this.swimAnimation, this.bipedLeftLeg.rotateAngleX, 0.3F * MathHelper.cos(limbSwing * 0.33333334F + (float)Math.PI));
	         this.bipedRightLeg.rotateAngleX = MathHelper.lerp(this.swimAnimation, this.bipedRightLeg.rotateAngleX, 0.3F * MathHelper.cos(limbSwing * 0.33333334F));
	      }
	}
	
	   private float getArmAngleSq(float limbSwing) {
		      return -65.0F * limbSwing + limbSwing * limbSwing;
	   }
}
