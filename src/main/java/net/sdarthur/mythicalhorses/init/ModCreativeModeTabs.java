package net.sdarthur.mythicalhorses.init;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sdarthur.mythicalhorses.MythicalHorses;

@Mod.EventBusSubscriber(modid = MythicalHorses.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModCreativeModeTabs {
    public static CreativeModeTab MYTHICAL_HORSES_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        MYTHICAL_HORSES_TAB = event.registerCreativeModeTab(new ResourceLocation(MythicalHorses.MODID, "mythical_horses_tab"),
                builder -> builder.icon(() -> new ItemStack(ItemInit.AMULET_ACTIVE.get()))
                        .title(Component.translatable("tabs.mythicalhorses.mythical_horses_tab")));
    }
}
