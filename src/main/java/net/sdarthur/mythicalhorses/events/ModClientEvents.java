package net.sdarthur.mythicalhorses.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sdarthur.mythicalhorses.MythicalHorses;
import net.sdarthur.mythicalhorses.client.models.GenericHorseModel;
import net.sdarthur.mythicalhorses.client.renderer.GenericFlyingHorseRenderer;
import net.sdarthur.mythicalhorses.client.renderer.GenericHorseRenderer;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.init.EntityInit;

import javax.swing.text.JTextComponent;
import java.lang.reflect.Field;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MythicalHorses.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.GENERIC_HORSE.get(), GenericHorseRenderer::new);
        event.registerEntityRenderer(EntityInit.GENERIC_FLYING_HORSE.get(), GenericFlyingHorseRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GenericHorseModel.LAYER_LOCATION, GenericHorseModel::createBodyLayer);
    }
}