package mod.gottsch.forge.treasure2_undergarden_lp.datagen;

import com.mojang.datafixers.util.Pair;
import mod.gottsch.forge.treasure2.Treasure;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * NOTE can't get runData() to work with Undergarden as a dependency
 * so creating loot tables with substitutes and have to find/replace after
 * generation.
 *
 * In general
 * WOOD/LEATHER -> CLOGGRUM
 * STONE/CHAIN -> ANCIENT (NO BOOTS)
 * GOLD -> FROSTEEL
 * IRON -> UTHERIUM
 * DIAMOND -> FORGOTTEN
 * TNT -> BLISTER BOMB
 * ARROW -> DEPTHROCKPEBBLE
 * SPECTRAL_ARROW -> GOO BALL
 *
 * BOW -> SLINGSHOT
 *
 * IRON_NUGGET -> CLOGGRUM_NUGGET
 * GOLD_NUGGET -> FROSTEEL_NUGGET
 * PORKCHOP -> UTHERIUM_SHARD
 * BEEF -> FORGOTTEN_NUGGET
 *
 * IRON_INGOT -> CLOGGRUM_INGOT
 * GOLD_INGOT -> FROSTEEL_INGOT
 * COOKED_PORKCHOP -> UTHERIUM_CRYSTAL
 * COOKED_BEEF -> FORGOTTEN_INGOT
 *
 * MUSIC_DISC_BLOCKS -> MAMMOTH_DISC
 *MUSIC_DISC_CAT -> LIMAX_MAXIMUS_DISC
 * MUSIC_DISC_CHIRP -> RELICT_DISC
 * MUSIC_DISC_FAR -> GLOOMPER_ANTHEM_DISC
 * MUSIC_DISC_MALL -> GLOOMPER_SECRET_DISC
 *
 * NETHERITE_UPGRADE_SMITHING_TEMPLATE -> FORGOTTEN_UPGRADE_TEMPLATE
 * NETHERITE_PICKAXE -> CLOGGRUM_BATTLEAXE
 * NETHERITE_AXE -> FORGOTTEN_BATTLEAXE
 *
 * EMERALD -> RAGLIUM_CRYSTAL
 * DIAMOND -> CATALYST
 * Some specials have to be manually updated, like Shields.
 *
 * Make sure to remove the "type":"chest" entry from the loot table
 *
 * @author by Mark Gottschling on 3/3/2025
 */
public class ModLootTablesProvider extends LootTableProvider {
    public ModLootTablesProvider(DataGenerator output) {
        super(output);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((location, lootTable) -> LootTables.validate(validationtracker, location, lootTable));
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return List.of(
                Pair.of(Chests::new, LootContextParamSets.ALL_PARAMS));
    }

    public static class Chests  implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            generateScarce(consumer);
            generateRare(consumer);
            generateEpic(consumer);
            generateLegendary(consumer);
            generateMythical(consumer);

            generateSkull(consumer);
            generateGoldSkull(consumer);
            generateCrystalSkull(consumer);
        }

        private void generateCrystalSkull(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(new ResourceLocation(Treasure.MODID, "injects/chests/crystal_skull/undergarden_crystal_skull"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("undergarden_crystal_skull_items")
                            .setRolls(ConstantValue.exactly(1F))

                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5F, 10F))))
                    )
            );
        }

        private void generateGoldSkull(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(new ResourceLocation(Treasure.MODID, "injects/chests/gold_skull/undergarden_gold_skull"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("undergarden_gold_skull_items")
                            .setRolls(ConstantValue.exactly(1F))

                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(2F, 5F))))
                    )
            );
        }

        private void generateSkull(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(new ResourceLocation(Treasure.MODID, "injects/chests/skull/undergarden_skull"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("undergarden_skull_items")
                            .setRolls(ConstantValue.exactly(1F))

                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(0F, 2F))))
                    )
            );
        }

        private void generateMythical(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(new ResourceLocation(Treasure.MODID, "injects/chests/mythical/undergarden_mythical"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("undergarden_mythical_items")
                            .setRolls(ConstantValue.exactly(2F))

                            // misc non-metal weapons
                            .add(LootItem.lootTableItem(Items.TNT).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(20F, 30F))))

                            // Cloggrum
                            .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            .add(LootItem.lootTableItem(Items.WOODEN_SWORD).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(2))

                            .add(LootItem.lootTableItem(Items.WOODEN_AXE).setWeight(1)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(20F, 30F))))

                            // ancient
                            .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(ConstantValue.exactly(30F))))

                            // frosteel
                            .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20F, 30F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20F, 30F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20F, 30F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20F, 30F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20F, 30F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(1)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20F, 30F))))

                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(20F, 30F))))

                            // utherium
                            .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.COOKED_PORKCHOP).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(10F, 20F))))
                    )

                    // forgotten/ancient
                    .withPool(LootPool.lootPool()
                            .name("undergarden_mythical_forgotten_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(30))

                            .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 0.95F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(5F, 10F))))

                            .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(1)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(5F, 10F))))

                            .add(LootItem.lootTableItem(Items.DIAMOND_HOE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND_SHOVEL).setWeight(1))

                            .add(LootItem.lootTableItem(Items.COOKED_BEEF).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5F, 15F))))

                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_BLOCKS).setWeight(1))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(1))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_CHIRP).setWeight(1))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_FAR).setWeight(1))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_MALL).setWeight(1))

                            // battle axes
                            .add(LootItem.lootTableItem(Items.NETHERITE_PICKAXE).setWeight(2).setQuality(1)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(5F, 10F))))

                            .add(LootItem.lootTableItem(Items.NETHERITE_AXE).setWeight(2).setQuality(1)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(5F, 10F))))


                    )
            );
        }

        private void generateLegendary(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(new ResourceLocation(Treasure.MODID, "injects/chests/legendary/undergarden_legendary"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("undergarden_legendary_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(10))

                            // misc non-metal weapons
                            .add(LootItem.lootTableItem(Items.BOW).setWeight(1))
                            .add(LootItem.lootTableItem(Items.ARROW).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(30.0F, 40.0F))))
                            .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(20F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.TNT).setWeight(3)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(10F, 20F))))

                            // Cloggrum
                            .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(4)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(4)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(4)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(4)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.WOODEN_SWORD).setWeight(4)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(4))

                            .add(LootItem.lootTableItem(Items.WOODEN_AXE).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(10F, 20F))))

                            // ancient
                            .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET).setWeight(4)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(4)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 30.0F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS).setWeight(4)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20.0F, 30.0F))))

                            // frosteel
                            .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(2)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15F, 20F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_HOE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(1))

                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(10F, 20F))))
                    )
                    // utherium
                    .withPool(LootPool.lootPool()
                            .name("undergarden_legendary_utherium_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(30))

                            .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(8F, 1F))))

                            .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(8F, 1F))))

                            .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(8F, 1F))))

                            .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(8F, 1F))))

                            .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(2))
                            .add(LootItem.lootTableItem(Items.IRON_HOE).setWeight(2))
                            .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(2))
                            .add(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(2))

                            .add(LootItem.lootTableItem(Items.COOKED_PORKCHOP).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5F, 10F))))
                    )

                    // forgotten
                    .withPool(LootPool.lootPool()
                            .name("undergarden_legendary_forgotten_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(40))

                            .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND_HOE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND_SHOVEL).setWeight(1))

                            .add(LootItem.lootTableItem(Items.COOKED_BEEF).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(2.0F, 5.0F))))

                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_BLOCKS).setWeight(1))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(1))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_CHIRP).setWeight(1))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_FAR).setWeight(1))

                            .add(LootItem.lootTableItem(Items.NETHERITE_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.NETHERITE_AXE).setWeight(1))
                    )
            );
        }

        /*
         * EPIC items should are almost  undamaged except for newly introduced metals.
         * introduces enchantments on the lower metals and introduces Forgotten with low chance
         */
        private void generateEpic(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(new ResourceLocation(Treasure.MODID, "injects/chests/epic/undergarden_epic"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("undergarden_epic_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(20))

                            // misc non-metal weapons
                            .add(LootItem.lootTableItem(Items.BOW).setWeight(4))
                            .add(LootItem.lootTableItem(Items.ARROW).setWeight(3)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(20.0F, 30.0F))))
                            .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(10F, 20.0F))))

                            .add(LootItem.lootTableItem(Items.TNT).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5F, 10F))))

                            // Cloggrum
                            .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))

                            .add(LootItem.lootTableItem(Items.WOODEN_SWORD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F))))

                            .add(LootItem.lootTableItem(Items.WOODEN_AXE).setWeight(1).apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))
                            .add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.WOODEN_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.WOODEN_SHOVEL).setWeight(1))

                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5.0F, 10.0F))))

                            // ancient
                            .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(15.0F, 20.0F))))

                            // frosteel
                            .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.8F, 1F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_HOE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(1))

                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5.0F, 10.0F))))
                    )
                    .withPool(LootPool.lootPool()
                            .name("undergarden_epic_utherium_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(50))

                            .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(3))
                            .add(LootItem.lootTableItem(Items.IRON_HOE).setWeight(3))
                            .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(3))
                            .add(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(3))

                            .add(LootItem.lootTableItem(Items.COOKED_PORKCHOP).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(1.0F, 4.0F))))
                    )

                    .withPool(LootPool.lootPool()
                            .name("undergarden_epic_forgotten_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(50))

                            .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                            .add(LootItem.lootTableItem(Items.DIAMOND_AXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND_HOE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND_SHOVEL).setWeight(1))

                            .add(LootItem.lootTableItem(Items.BEEF).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(2.0F, 5.0F))))

                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_BLOCKS).setWeight(1))
                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(1))
                    )
            );
        }

        /*
         * RARE items should are less damaged somewhat without any enchantments and introduces Utherium with low chance
         */
        private void generateRare(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(new ResourceLocation(Treasure.MODID, "injects/chests/rare/undergarden_rare"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("undergarden_rare_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(30))

                            // misc non-metal weapons
                            .add(LootItem.lootTableItem(Items.BOW).setWeight(4))
                            .add(LootItem.lootTableItem(Items.ARROW).setWeight(3)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(10.0F, 15.0F))))
                            .add(LootItem.lootTableItem(Items.SPECTRAL_ARROW).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(2.0F, 10.0F))))
                            .add(LootItem.lootTableItem(Items.TNT).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(2.0F, 5.0F))))

                            // Cloggrum
                            .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.WOODEN_SWORD).setWeight(4)

                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))
                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.WOODEN_AXE).setWeight(2))
                            .add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(2))
                            .add(LootItem.lootTableItem(Items.WOODEN_PICKAXE).setWeight(2))
                            .add(LootItem.lootTableItem(Items.WOODEN_SHOVEL).setWeight(2))

                            .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5.0F, 10.0F))))

                            // ancient
                            .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            // frosteel
                            .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(1))

                            .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_HOE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(1))

                            .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5.0F, 10.0F))))

                            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
                    )
                    .withPool(LootPool.lootPool()
                            .name("undergarden_rare_utherium_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(50))

                            .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                            .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                            .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                            .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                            .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.IRON_HOE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(4))

                            .add(LootItem.lootTableItem(Items.PORKCHOP).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(1.0F, 4.0F))))

                            .add(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(4))
                    )
            );
        }

        /*

         */
        private void generateScarce(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(new ResourceLocation(Treasure.MODID, "injects/chests/scarce/undergarden_scarce"), LootTable.lootTable()

                    // SCARCE items should all be damaged somewhat without any enchantments
                    .withPool(LootPool.lootPool()
                            .name("undergarden_scarce_items")
                            .setRolls(ConstantValue.exactly(1F))
                            .add(EmptyLootItem.emptyItem().setWeight(50))

                            // misc non-metal weapons
                            .add(LootItem.lootTableItem(Items.BOW).setWeight(4))
                            .add(LootItem.lootTableItem(Items.ARROW).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(2.0F, 10.0F))))
                            .add(LootItem.lootTableItem(Items.TNT).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(1.0F, 2.0F))))

                            // Cloggrum
                            .add(LootItem.lootTableItem(Items.LEATHER_HELMET).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.LEATHER_BOOTS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.WOODEN_SWORD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.WOODEN_AXE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.WOODEN_HOE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.WOODEN_PICKAXE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.WOODEN_SHOVEL).setWeight(4))

                            .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(1.0F, 4.0F))))

                            // ancient
                            .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            // frosteel
                            .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

                            .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(1))

                            .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_HOE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(1))
                            .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(1))

                            .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(1.0F, 4.0F))))
                    )
            );
        }
    }
}
