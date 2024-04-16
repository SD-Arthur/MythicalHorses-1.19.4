package net.sdarthur.mythicalhorses.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.init.ItemInit;
import net.minecraft.core.Registry;

public class Amulet extends Item {
    public Amulet(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        if(!player.level.isClientSide && hand == InteractionHand.MAIN_HAND && target instanceof GenericHorse && (((GenericHorse) target).isOwnedBy(player))) {
            pickUp(player);
            ItemStack trueStack = player.getItemInHand(hand);

            CompoundTag newTag = new CompoundTag();
            CompoundTag entityTag = new CompoundTag();

            target.save(entityTag);
            newTag.put("EntityTag", entityTag);

            newTag.putString("AmuletEntityID", ForgeRegistries.ENTITY_TYPES.getKey(target.getType()).toString());
            trueStack.setTag(newTag);

            player.swing(hand);
//            player.level.playSound(player, player.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.NEUTRAL, 15.0F, 0.75F);
            target.remove(Entity.RemovalReason.DISCARDED);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    public static void pickUp(Player player) {
        ItemStack currentItem = player.getMainHandItem();
        currentItem.shrink(1);

        ItemStack stack = new ItemStack(ItemInit.AMULET_ACTIVE.get());

        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
    }
}
