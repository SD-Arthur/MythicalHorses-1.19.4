package net.sdarthur.mythicalhorses.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.init.ItemInit;
import net.sdarthur.mythicalhorses.network.PacketHandler;
import net.sdarthur.mythicalhorses.network.SAmuletPickUp;
import net.sdarthur.mythicalhorses.network.SChatMessage;
import org.jetbrains.annotations.NotNull;

public class Amulet extends Item {
    public Amulet(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand) {

        if(!player.level.isClientSide && hand == InteractionHand.MAIN_HAND && entity instanceof GenericHorse && (((GenericHorse) entity).isOwnedBy(player))) {
            player.swing(hand);
            entity.remove(Entity.RemovalReason.DISCARDED);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    public static void pickUp(GenericHorse horse, Player player) {
        ItemStack stack = new ItemStack(ItemInit.AMULET_ACTIVE.get());

        ItemStack currentItem = player.getMainHandItem();
        currentItem.shrink(1);

        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        horse.discard();
    }
}
