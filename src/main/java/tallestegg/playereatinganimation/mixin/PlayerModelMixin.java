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
	  ItemStack itemstack = entityIn.getHeldItemMainhand();
	  ItemStack itemstack1 = entityIn.getHeldItemOffhand();
	  if (itemstack.getUseAction() == UseAction.EAT && entityIn.isHandActive() && entityIn.getPrimaryHand() == HandSide.RIGHT || itemstack1.getUseAction() == UseAction.EAT && entityIn.isHandActive() && entityIn.getPrimaryHand() == HandSide.LEFT) 
	  {
            this.bipedRightArm.rotateAngleY = -0.5F;
            this.bipedRightArm.rotateAngleX = -1.3F;
            this.bipedRightArm.rotateAngleZ = MathHelper.cos(ageInTicks) * 0.05F;
	  } else if (itemstack.getUseAction() == UseAction.EAT && entityIn.isHandActive() && entityIn.getPrimaryHand() == HandSide.LEFT || itemstack1.getUseAction() == UseAction.EAT && entityIn.isHandActive() && entityIn.getPrimaryHand() == HandSide.RIGHT)
      {
          this.bipedLeftArm.rotateAngleY = 0.5F;
          this.bipedLeftArm.rotateAngleX = -1.3F;
          this.bipedLeftArm.rotateAngleZ = MathHelper.cos(ageInTicks) * 0.05F;
      }
	 }
}
