package net.sdarthur.mythicalhorses.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sdarthur.mythicalhorses.MythicalHorses;
import net.sdarthur.mythicalhorses.client.models.GenericFlyingHorseModel;
import net.sdarthur.mythicalhorses.client.models.GenericHorseModel;
import net.sdarthur.mythicalhorses.entities.GenericFlyingHorse;
import net.sdarthur.mythicalhorses.entities.GenericHorse;

public class GenericFlyingHorseRenderer extends MobRenderer<GenericFlyingHorse, GenericFlyingHorseModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(MythicalHorses.MODID, "textures/entities/generic_flying_horse.png");

    public GenericFlyingHorseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GenericFlyingHorseModel(ctx.bakeLayer(GenericHorseModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(GenericFlyingHorse entity) {
        return TEXTURE;
    }
}
