package net.sdarthur.mythicalhorses.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.init.ItemInit;
import net.sdarthur.mythicalhorses.network.PacketHandler;
import net.sdarthur.mythicalhorses.network.SAmuletPickUp;

public class Amulet extends Item {
    public Amulet(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {
        if(!player.getLevel().isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if(!(entity instanceof GenericHorse)) {
            return InteractionResult.FAIL;
        }

        GenericHorse horse = (GenericHorse) entity;

        if(!(horse.isOwnedBy(player))) {
            return InteractionResult.FAIL;
        }

        if(player.isShiftKeyDown()) {
            return InteractionResult.FAIL;
        }

        PacketHandler.sendToServer(new SAmuletPickUp(horse.getUUID()));

        return InteractionResult.SUCCESS;
    }

    public static void pickUp(GenericHorse horse, Player player) {
        ItemStack stack = new ItemStack(ItemInit.AMULET_ACTIVE.get());

        ItemStack currentItem = player.getMainHandItem();
        currentItem.shrink(1);

        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        horse.discard();
    }
}
