package mod.gottsch.forge.treasure2_undergarden_lp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.gottsch.forge.treasure2.Treasure;
import mod.gottsch.forge.treasure2.api.TreasureApi;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author Mark Gottschling on March 3, 2025
 */
@Mod(TreasureUndergardenLP.MOD_ID)
public class TreasureUndergardenLP {
    // Directly reference a slf4j logger
	public static Logger LOGGER = LogManager.getLogger(Treasure.MODID);

	public static final String MOD_ID = "treasure2_undergarden_lp";
	
    public TreasureUndergardenLP() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
    }

    /**
     * 
     * @param event
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
		// register loot tables
    	Treasure.LOGGER.debug("registering Treasure Undergarden LP");
		TreasureApi.registerLootTables(TreasureUndergardenLP.MOD_ID);
    }
}
