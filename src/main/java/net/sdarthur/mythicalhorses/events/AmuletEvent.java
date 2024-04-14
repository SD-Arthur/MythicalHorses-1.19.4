package net.sdarthur.mythicalhorses.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.init.ItemInit;

public class AmuletEvent {

    @SubscribeEvent
    public void onClick(PlayerInteractEvent.EntityInteract event) {
        if(!event.getLevel().isClientSide) {
            return;
        }

        Player player = event.getEntity();
        ItemStack itemStack = player.getItemInHand(event.getHand());

        if(itemStack != ItemInit.AMULET.get().getDefaultInstance()) {
            return;
        }

        if(!(event.getTarget() instanceof GenericHorse)) {
            return;
        }

        if(player.isShiftKeyDown()) {
            return;
        }
    }
}
