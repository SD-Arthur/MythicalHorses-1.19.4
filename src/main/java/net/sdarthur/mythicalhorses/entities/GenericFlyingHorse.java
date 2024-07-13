package net.sdarthur.mythicalhorses.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.sdarthur.mythicalhorses.mother_classes.FlyingMount;
import net.sdarthur.mythicalhorses.mother_classes.TamableHorse;

public class GenericFlyingHorse extends GenericHorse {
    public GenericFlyingHorse(EntityType<? extends GenericHorse> t_entity, Level level) {
        super(t_entity, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 14.0f)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.215f)
                .add(Attributes.FLYING_SPEED, 1.5f)
                .add(Attributes.JUMP_STRENGTH, 0.562f)
                .add(Attributes.FOLLOW_RANGE, 12d);
    }

    //TODO: Find where tf is flyingSpeed in IceAndFire, and do the movement thing (goingUp, goingDown, etc.)
    @Override
    public void travel(Vec3 pTravelVector) {
        super.travel(pTravelVector);

        if(this.isVehicle()) {
            float walkSpeedFactor = 0.80f;
            float flightSpeedFactor = 0.35F;
            LivingEntity rider = (LivingEntity)this.getControllingPassenger();

            // Mouse controlled yaw
            this.setYRot(rider.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(rider.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;

            float sideway = rider.xxa;
            float forward = rider.zza;

            float speedFactor = 1.0f;

            if(!onGround) {
                speedFactor *= flightSpeedFactor;
                this.setNoGravity(true);
            }
            else {
                speedFactor *= walkSpeedFactor;
                this.setNoGravity(false);
                // In air moving speed
                this.setSpeed(this.getSpeed() * 0.1F);
            }

            //Faster on sprint
            speedFactor *= rider.isSprinting() ? 1.5f : 1.0f;
            // Slower on going back/sideways
            forward *= forward <= 0f ? 0.25f : 1.0f;
            sideway *= 0.5F;

            if (this.isControlledByLocalInstance()) {
                this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * speedFactor);
                super.travel(new Vec3(sideway, 0.0f, forward));

                // Vanilla travel has a smaller friction factor for Y axis
                // Add more friction in case moving too fast on Y axis
                if (!onGround) {
                    // See LivingEntity#getFrictionInfluencedSpeed -> flyingSpeed (default: 0.02) is used when not on ground
                    //this.flyingSpeed = getSpeed();
                    this.setDeltaMovement(this.getDeltaMovement().multiply(1.0f, 0.92f, 1.0f));
                }
            }
            else if (rider instanceof Player) {
                this.setDeltaMovement(Vec3.ZERO);
            }
        }
        else {
            this.setNoGravity(false);

            setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED));
        }
    }

//    @Override
//    public void tick() {
//        super.tick();
//
//        AttributeInstance gravity = this.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
//
//        if(gravity != null) {
//            double fallSpeed = gravity.getValue() * -2.5;
//
//            if(this.getDeltaMovement().y() < fallSpeed) {
//                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
//                this.onGround = false;
//            }
//        }
//
//        if(!onGround) {
//            this.setSpeed(22.2f);
//        }
//    }

    @Override
    public void onPlayerJump(int i) {
        this.setDeltaMovement(this.getDeltaMovement().x(), this.getDeltaMovement().y() + 1.25F, this.getDeltaMovement().z());
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return 0;
    }

    public final boolean IGNORE_PHYSICS_ON_SERVER = false;

}
