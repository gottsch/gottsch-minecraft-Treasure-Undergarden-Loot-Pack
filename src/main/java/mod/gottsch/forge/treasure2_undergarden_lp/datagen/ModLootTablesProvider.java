package mod.gottsch.forge.treasure2_undergarden_lp.datagen;

import mod.gottsch.forge.treasure2.Treasure;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * NOTE can't get runData() to work with Undergarden as a dependency
 * so creating loot tables with substitutes and have to find/replace after
 * generation.
 *
 * In general
 * WOOD -> CLOGGRUM
 * GOLD -> FROSTEEL
 * IRON -> UTHERIUM
 * DIAMOND -> FORGOTTEN
 * TNT -> BLISTER BOMB
 * ARROW -> DEPTHROCKPEBBLE
 * SPECTRAL_ARROW -> GOO BALL
 * TIPPED_ARROW -> that yellow thing
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
 *
 * Some specials have to be manually updated, like Shields.
 *
 * Make sure to remove the "type":"chest" entry from the loot table
 *
 * @author by Mark Gottschling on 3/3/2025
 */
public class ModLootTablesProvider extends LootTableProvider {
    public ModLootTablesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(Chests::new, LootContextParamSets.ALL_PARAMS)
        ));
    }

    public static class Chests implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            generateScarce(consumer);
            generateRare(consumer);
            generateEpic(consumer);
            generateLegendary(consumer);
            generateMythic(consumer);
        }

        private void generateMythic(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
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
                            .add(LootItem.lootTableItem(Items.TIPPED_ARROW).setWeight(3)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(10F, 20.0F))))
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

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(2))

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

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(4)
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

                            .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(2)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(2)
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
                            .add(LootItem.lootTableItem(Items.TIPPED_ARROW).setWeight(1)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(5F, 10.0F))))
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
                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(2)
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

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(4)
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

                    .add(LootItem.lootTableItem(Items.DIAMOND_CHESTPLATE).setWeight(1)
                            .apply(SetItemDamageFunction
                                    .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                    .add(LootItem.lootTableItem(Items.DIAMOND_LEGGINGS).setWeight(1)
                            .apply(SetItemDamageFunction
                                    .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                    .add(LootItem.lootTableItem(Items.DIAMOND_BOOTS).setWeight(1)
                            .apply(SetItemDamageFunction
                                    .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                    .add(LootItem.lootTableItem(Items.DIAMOND_SWORD).setWeight(1)
                            .apply(SetItemDamageFunction
                                    .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                    .add(LootItem.lootTableItem(Items.SHIELD).setWeight(1)
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
                    .add(LootItem.lootTableItem(Items.SHIELD).setWeight(1)
                            .apply(SetItemDamageFunction
                                    .setDamage(UniformGenerator.between(0.6F, 0.9F))))

                    .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(1))
                    .add(LootItem.lootTableItem(Items.GOLDEN_HOE).setWeight(1))
                    .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(1))
                    .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(1))

                    .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(1)
                            .apply(SetItemCountFunction
                                    .setCount(UniformGenerator.between(5.0F, 10.0F))))
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

                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(4)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.5F))))

                            .add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.IRON_HOE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(4))
                            .add(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(4))

                            .add(LootItem.lootTableItem(Items.PORKCHOP).setWeight(2)
                                    .apply(SetItemCountFunction
                                            .setCount(UniformGenerator.between(1.0F, 4.0F))))
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
                            .add(LootItem.lootTableItem(Items.SHIELD).setWeight(1)
                                    .apply(SetItemDamageFunction
                                            .setDamage(UniformGenerator.between(0.3F, 0.6F))))

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
