package net.sdarthur.mythicalhorses.items;

import net.minecraft.world.item.Item;

public class Amulet extends Item {

    public Amulet(Properties properties) {
        super(properties);
    }

//    public void onClick(PlayerInteractEvent.EntityInteract event) {
//        if(!event.getLevel().isClientSide) {
//            return;
//        }
//
//        if(!(event.getTarget() instanceof GenericHorse)) {
//            return;
//        }
//
//        Player player = event.getEntity();
//        ItemStack itemStack = player.getItemInHand(event.getHand());
//        GenericHorse horse = (GenericHorse) event.getTarget();
//
//        if(itemStack != ItemInit.AMULET.get().getDefaultInstance()) {
//            return;
//        }
//
//        if(!(((GenericHorse) event.getTarget()).isOwnedBy(player))) {
//            return;
//        }
//
//        if(player.isShiftKeyDown()) {
//            return;
//        }
//
//        PacketHandler.sendToServer(new SAmuletPickUp(horse.getUUID()));
//
//        event.setCancellationResult(InteractionResult.SUCCESS);
//        event.setCanceled(true);
//    }

//    public static void pickUp(GenericHorse horse, Player player) {
//        ItemStack stack = new ItemStack(ItemInit.AMULET_ACTIVE.get());
//
//        ItemStack currentItem = player.getMainHandItem();
//        currentItem.shrink(1);
//
//        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
//        horse.discard();
//    }
}
