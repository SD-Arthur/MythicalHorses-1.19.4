package net.sdarthur.mythicalhorses.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sdarthur.mythicalhorses.MythicalHorses;
import net.sdarthur.mythicalhorses.entities.GenericFlyingHorse;
import net.sdarthur.mythicalhorses.entities.GenericHorse;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MythicalHorses.MODID);

    public static final RegistryObject<EntityType<GenericHorse>> GENERIC_HORSE = ENTITIES.register("generic_horse",
            () -> EntityType.Builder.of(GenericHorse::new, MobCategory.AMBIENT)
                    .sized(1.0F, 1.0F)
                    .build(new ResourceLocation(MythicalHorses.MODID, "generic_horse").toString()));

    public static final RegistryObject<EntityType<GenericFlyingHorse>> GENERIC_FLYING_HORSE = ENTITIES.register("generic_flying_horse",
            () -> EntityType.Builder.of(GenericFlyingHorse::new, MobCategory.AMBIENT)
                    .sized(1.0F, 1.0F)
                    .build(new ResourceLocation(MythicalHorses.MODID, "generic_flying_horse").toString()));
}
