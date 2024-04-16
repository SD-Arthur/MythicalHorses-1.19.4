package net.sdarthur.mythicalhorses.events;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.network.chat.contents.KeybindContents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sdarthur.mythicalhorses.MythicalHorses;
import net.sdarthur.mythicalhorses.entities.GenericHorse;


@Mod.EventBusSubscriber(modid = MythicalHorses.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HorseFlyEvent {

    @SubscribeEvent
    public void horseFly(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();

        if(entity instanceof GenericHorse && entity.isVehicle()) {
            entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getDeltaMovement().y() + 50.0F, entity.getDeltaMovement().z());
//            entity.setPos(entity.position().x, entity.position().y + 2.0F, entity.position().z);
        }
    }
}
