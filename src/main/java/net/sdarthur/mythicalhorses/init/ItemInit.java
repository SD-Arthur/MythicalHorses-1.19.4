package net.sdarthur.mythicalhorses.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sdarthur.mythicalhorses.MythicalHorses;
import net.sdarthur.mythicalhorses.items.Amulet;

import java.util.Properties;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MythicalHorses.MODID);

    public static final RegistryObject<Amulet> AMULET = ITEMS.register("amulet",
            () -> new Amulet(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Amulet> AMULET_ACTIVE = ITEMS.register("amulet_active",
            () -> new Amulet(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SUGAR_CUBES = ITEMS.register("sugar_cubes",
            () -> new Item(new Item.Properties().food(Foods.SUGAR_CUBES)));

    public static class Foods {
        public static final FoodProperties SUGAR_CUBES = new FoodProperties.Builder().nutrition(4).saturationMod(2.0F)
                .fast()
                .build();
    }

    public static final RegistryObject<ForgeSpawnEggItem> GENERIC_HORSE_SPAWN_EGG = ITEMS.register("generic_horse_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.GENERIC_HORSE, 0xc66a56, 0xffffff, new Item.Properties()));
}
