package net.sdarthur.mythicalhorses.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sdarthur.mythicalhorses.network.PacketHandler;
import net.sdarthur.mythicalhorses.network.SAmuletSpawn;

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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();

        if(world.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);

            if(!blockstate.getCollisionShape(world, blockpos).isEmpty()) {
                blockpos = blockpos.relative(direction);
            }

            PacketHandler.sendToServer(new SAmuletSpawn(blockpos));
        }

        return super.useOn(context);
    }
}
