package net.sdarthur.mythicalhorses.items;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.init.ItemInit;
import org.jetbrains.annotations.NotNull;

public class AmuletActive extends Item {
    public AmuletActive(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() != Direction.UP)
            return InteractionResult.FAIL;
        ItemStack stack = context.getItemInHand();
        if (stack.getTag() != null && !stack.getTag().getString("AmuletEntityID").isEmpty()) {
            Level world = context.getLevel();
            String id = stack.getTag().getString("AmuletEntityID");
            EntityType type = EntityType.byString(id).orElse(null);
            if (type != null) {
                Entity entity = type.create(world);
                if (entity instanceof GenericHorse) {
                    GenericHorse horse = (GenericHorse) entity;
                    horse.load(stack.getTag().getCompound("EntityTag"));
                }
                //Still needed to allow for intercompatibility
                if (stack.getTag().contains("EntityUUID"))
                    entity.setUUID(stack.getTag().getUUID("EntityUUID"));

                entity.absMoveTo(context.getClickedPos().getX() + 0.5D, (context.getClickedPos().getY() + 1), context.getClickedPos().getZ() + 0.5D, 180 + (context.getHorizontalDirection()).toYRot(), 0.0F);

                if (world.addFreshEntity(entity)) {
                    stack.shrink(1);

                    ItemStack amuletStack = new ItemStack(ItemInit.AMULET.get());

                    Player player = context.getPlayer();
                    player.setItemInHand(InteractionHand.MAIN_HAND, amuletStack);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
