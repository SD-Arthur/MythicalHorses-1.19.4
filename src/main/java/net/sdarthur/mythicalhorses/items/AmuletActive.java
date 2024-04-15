package net.sdarthur.mythicalhorses.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sdarthur.mythicalhorses.network.PacketHandler;
import net.sdarthur.mythicalhorses.network.SAmuletSpawn;

public class AmuletActive extends Item {
    public AmuletActive(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();

        if(world.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        else {
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
