package net.sdarthur.mythicalhorses.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.sdarthur.mythicalhorses.mother_classes.TamableHorse;

public class GenericFlyingHorse extends GenericHorse {
    public GenericFlyingHorse(EntityType<? extends TamableHorse> t_entity, Level level) {
        super(t_entity, level);
    }

    @Override
    public void tick() {
        super.tick();

        AttributeInstance gravity = this.getAttribute(ForgeMod.ENTITY_GRAVITY.get());

        if(gravity != null) {
            double fallSpeed = gravity.getValue() * -2;

            if(this.getDeltaMovement().y() < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
                this.onGround = false;
            }
        }
    }

    @Override
    public void onPlayerJump(int i) {
        this.setDeltaMovement(this.getDeltaMovement().x(), this.getDeltaMovement().y() + 1.0F, this.getDeltaMovement().z());
    }
}
