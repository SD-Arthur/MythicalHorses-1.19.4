package net.sdarthur.mythicalhorses;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sdarthur.mythicalhorses.events.ModClientEvents;
import net.sdarthur.mythicalhorses.init.BlockInit;
import net.sdarthur.mythicalhorses.init.EntityInit;
import net.sdarthur.mythicalhorses.init.ItemInit;
import net.sdarthur.mythicalhorses.init.ModCreativeModeTabs;

@Mod(MythicalHorses.MODID)
public class MythicalHorses {
    public static final String MODID = "mythicalhorses";

    public MythicalHorses() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        bus.addListener(ModCreativeModeTabs::registerCreativeModeTabs);
        bus.addListener(this::addItemsToTabs);

        EntityInit.ENTITIES.register(bus);
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
    }

    private void addItemsToTabs(CreativeModeTabEvent.BuildContents event) {
        if(event.getTab() == ModCreativeModeTabs.MYTHICAL_HORSES_TAB) {
            event.accept(ItemInit.AMULET);
            event.accept(ItemInit.SUGAR_CUBES);
            event.accept(BlockInit.SPECIAL_HAY);
        }

        if(event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ItemInit.GENERIC_HORSE_SPAWN_EGG);
        }
    }
}