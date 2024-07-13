package net.sdarthur.mythicalhorses.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import net.sdarthur.mythicalhorses.init.EntityInit;
import net.sdarthur.mythicalhorses.init.ItemInit;
import net.sdarthur.mythicalhorses.mother_classes.TamableHorse;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class GenericHorse extends TamableAnimal implements PlayerRideableJumping {

    protected float playerJumpPendingScale;
    protected boolean isJumping;

    public GenericHorse(EntityType<? extends TamableAnimal> t_entity, Level level) {
        super(t_entity, level);
        setMaxUpStep(1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));

        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Items.WHEAT), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Items.APPLE), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Items.GOLDEN_APPLE), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(Items.GOLDEN_CARROT), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, Ingredient.of(ItemInit.SUGAR_CUBES.get()), false));

        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.05));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 14.0f)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.215f)
                .add(Attributes.JUMP_STRENGTH, 0.562f)
                .add(Attributes.FOLLOW_RANGE, 12d);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return EntityInit.GENERIC_HORSE.get().create(serverLevel);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(player.getUsedItemHand());

        if(this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || itemstack.is(Items.APPLE) && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }

        //TAME
        else if (itemstack.is(Items.APPLE) && !isTame()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            if (this.random.nextInt(5) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                this.tame(player);
                this.navigation.stop();
                this.level.broadcastEntityEvent(this, (byte) 7);
            } else {
                this.level.broadcastEntityEvent(this, (byte) 6);
            }

            return InteractionResult.SUCCESS;
        }

        //HEAL
        else if(isTame() && !isVehicle() && !itemstack.isEmpty()) {
            if((itemstack.is(ItemInit.SUGAR_CUBES.get())) && this.getHealth() < this.getMaxHealth()){
                this.heal(6.0F);
                itemstack.shrink(1);
            }
        }
        else if(!this.isVehicle() && !this.isBaby() && this.isTame() && itemstack.isEmpty()) {
            doPlayerRide(player);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    //------ FOOD ------
    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.GOLDEN_APPLE) || pStack.is(Items.GOLDEN_CARROT);
    }

    @Override
    public ItemStack eat(Level pLevel, ItemStack pFood) {
        return super.eat(pLevel, pFood);
    }

    //------ RIDING ------
    @Override
    protected void tickRidden(LivingEntity player, Vec3 vec3) {
        super.tickRidden(player, vec3);
        Vec2 vec2 = this.getRiddenRotation(player);
        this.setRot(vec2.y, vec2.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        if (this.isControlledByLocalInstance()) {

            if (this.isOnGround()) {
                this.setIsJumping(false);
                if (this.playerJumpPendingScale > 0.0F && !this.isJumping()) {
                    this.executeRidersJump(this.playerJumpPendingScale, vec3);
                }

                this.playerJumpPendingScale = 0.0F;
            }
        }

    }

    protected void doPlayerRide(Player player) {
        if (!this.level.isClientSide) {
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            player.startRiding(this);
        }

    }

    @Override
    protected Vec3 getRiddenInput(LivingEntity player, Vec3 vec3) {
        float f = player.xxa * 0.5F;
        float f1 = player.zza;
        if (f1 <= 0.0F) {
            f1 *= 0.25F;
        }

        if(this.onGround) {
            return new Vec3(f, 0.0, f1);
        }
        else {
            return new Vec3(f*5, 0.0, f1*5);
        }
    }

    @Override
    protected float getRiddenSpeed(LivingEntity player) {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    protected Vec2 getRiddenRotation(LivingEntity l_entity) {
        return new Vec2(l_entity.getXRot() * 0.5F, l_entity.getYRot());
    }

    @Override
    public void positionRider(Entity entity) {
        super.positionRider(entity);
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).yBodyRot = this.yBodyRot;
        }

    }

    @Override
    @javax.annotation.Nullable
    public LivingEntity getControllingPassenger() {
        if (this.isSaddled()) {
            Entity entity = this.getFirstPassenger();
            if (entity instanceof Player) {
                return (Player)entity;
            }
        }

        return super.getControllingPassenger();
    }

    @Override
    protected boolean canRide(Entity entity) {
        return super.canRide(entity);
    }

    public boolean isSaddled() {
        return true;
    }

    //------ JUMP ------
    protected void executeRidersJump(float v, Vec3 vec31) {
        double d0 = this.getCustomJump() * (double)v * (double)this.getBlockJumpFactor();
        double d1 = d0 + (double)this.getJumpBoostPower();
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, d0, vec3.z);
        this.setIsJumping(true);
        //CommonHooks.onLivingJump(this);
        if (vec31.z > 0.0) {
            float f = Mth.sin(this.getYRot() * 0.017453292F);
            float f1 = Mth.cos(this.getYRot() * 0.017453292F);
            this.setDeltaMovement(this.getDeltaMovement().add((-0.4F * f * v), 0.0, (0.4F * f1 * v)));
        }

    }

    public double getCustomJump() {
        return this.getAttributeValue(Attributes.JUMP_STRENGTH);
    }

    public void setIsJumping(boolean bool) {
        this.isJumping = bool;
    }

    public boolean isJumping() {
        return this.isJumping;
    }

    @Override
    public void onPlayerJump(int i) {
        if(i < 0) {
            i = 0;
        }

        if(i >= 90) {
            this.playerJumpPendingScale = 1.0F;
        }
        else {
            this.playerJumpPendingScale = 0.4F + 0.4F * (float)i / 90.0F;
        }
    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void handleStartJump(int i) {

    }

    @Override
    public void handleStopJump() {

    }

    //------ FALL DAMAGE ------
    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        if (pFallDistance > 1.0F) {
            this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
        }

        int i = this.calculateFallDamage(pFallDistance, pMultiplier);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(pSource, (float)i);
            if (this.isVehicle()) {
                Iterator var5 = this.getIndirectPassengers().iterator();

                while(var5.hasNext()) {
                    Entity entity = (Entity)var5.next();
                    entity.hurt(pSource, (float)i);
                }
            }

            this.playBlockFallSound();
            return true;
        }
    }

    @Override
    protected int calculateFallDamage(float pFallDistance, float pDamageMultiplier) {
        return Mth.ceil((pFallDistance * 0.5F - 3.0F) * pDamageMultiplier);
    }

    //------ SOUNDS ------
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HORSE_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.HORSE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HORSE_DEATH;
    }
}
