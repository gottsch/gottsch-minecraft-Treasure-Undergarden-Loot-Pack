
package mod.gottsch.forge.treasure2_undergarden_lp.datagen;

import mod.gottsch.forge.treasure2_undergarden_lp.TreasureUndergardenLP;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author Mark Gottschling on Mar 3, 2025
 *
 */
@Mod.EventBusSubscriber(modid = TreasureUndergardenLP.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();

		generator.addProvider(event.includeServer(), new ModLootTablesProvider(generator));

	}
}