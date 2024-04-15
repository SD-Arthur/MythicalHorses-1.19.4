package net.sdarthur.mythicalhorses.events;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sdarthur.mythicalhorses.MythicalHorses;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.init.ItemInit;
import net.sdarthur.mythicalhorses.network.PacketHandler;
import net.sdarthur.mythicalhorses.network.SAmuletPickUp;

@Mod.EventBusSubscriber(modid = MythicalHorses.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AmuletEvent {

    @SubscribeEvent
    public void onClick(PlayerInteractEvent.EntityInteract event) {
        if(!event.getLevel().isClientSide) {
            return;
        }

        if(!(event.getTarget() instanceof GenericHorse)) {
            return;
        }

        Player player = event.getEntity();
        ItemStack itemStack = player.getItemInHand(event.getHand());
        GenericHorse horse = (GenericHorse) event.getTarget();

        if(itemStack != ItemInit.AMULET.get().getDefaultInstance()) {
            return;
        }

        if(!(((GenericHorse) event.getTarget()).isOwnedBy(player))) {
            return;
        }

        if(player.isShiftKeyDown()) {
            return;
        }

        PacketHandler.sendToServer(new SAmuletPickUp(horse.getUUID()));

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    public static void pickUp(GenericHorse horse, Player player) {
        ItemStack stack = new ItemStack(ItemInit.AMULET_ACTIVE.get());

        ItemStack currentItem = player.getMainHandItem();
        currentItem.shrink(1);

        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        horse.discard();
    }
}
