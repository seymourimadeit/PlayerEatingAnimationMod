package tallestegg.playereatinganimation;

import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(PlayerEatingAnimation.MODID)
public class PlayerEatingAnimation
{
    public static final String MODID = "playereatinganimation";
    
    public PlayerEatingAnimation() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event) 
    {
    }
    
    @Mod.EventBusSubscriber(modid = PlayerEatingAnimation.MODID)
    public static class EventsAndStuff {
    	@SubscribeEvent
    	public static void onDrownedPoseChange(EntityEvent.EyeHeight event) {
    		if (event.getEntity() instanceof DrownedEntity && event.getPose() == Pose.SWIMMING) {
    			event.setNewHeight(0.2F);
    			event.getEntity().size = EntitySize.flexible(0.6F, 0.6F);
    		}
    	}
    }
}
