package tallestegg.playereatinganimation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

@Mixin(PlayerModel.class)
public class PlayerModelMixin <T extends LivingEntity> extends BipedModel<T>
{	
	public PlayerModelMixin(float p_i1148_1_) {
		super(p_i1148_1_);
	}

	@Inject(at = @At("TAIL"), method = "setRotationAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V")
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) 
	{
		if (entityIn.getPrimaryHand() == HandSide.RIGHT) 
		{
           this.eatingAnimationRightHand(Hand.MAIN_HAND, entityIn, ageInTicks);
           this.eatingAnimationLeftHand(Hand.OFF_HAND, entityIn, ageInTicks);
		} else {
	       this.eatingAnimationRightHand(Hand.OFF_HAND, entityIn, ageInTicks);
	       this.eatingAnimationLeftHand(Hand.MAIN_HAND, entityIn, ageInTicks);
		}
	}
	
	public void eatingAnimationRightHand(Hand hand, LivingEntity entity, float ageInTicks)
	{
		ItemStack itemstack = entity.getHeldItem(hand);
		boolean drinkingoreating = itemstack.getUseAction() == UseAction.EAT || itemstack.getUseAction() == UseAction.DRINK;
		if (entity.getItemInUseCount() > 0 && drinkingoreating && entity.getActiveHand() == hand)
        {
            this.bipedRightArm.rotateAngleY = -0.5F;
            this.bipedRightArm.rotateAngleX = -1.3F;
            this.bipedRightArm.rotateAngleZ = MathHelper.cos(ageInTicks) * 0.15F;
        }
	}
	
	public void eatingAnimationLeftHand(Hand hand, LivingEntity entity, float ageInTicks)
	{
		ItemStack itemstack = entity.getHeldItem(hand);
		boolean drinkingoreating = itemstack.getUseAction() == UseAction.EAT || itemstack.getUseAction() == UseAction.DRINK;
		if (entity.getItemInUseCount() > 0 && drinkingoreating && entity.getActiveHand() == hand)
        {
            this.bipedLeftArm.rotateAngleY = 0.5F;
            this.bipedLeftArm.rotateAngleX = -1.3F;
            this.bipedLeftArm.rotateAngleZ = MathHelper.cos(ageInTicks) * 0.15F;
        }
	}
}
