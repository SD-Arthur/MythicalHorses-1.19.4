package net.sdarthur.mythicalhorses.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.sdarthur.mythicalhorses.MythicalHorses;
import net.sdarthur.mythicalhorses.client.models.GenericHorseModel;
import net.sdarthur.mythicalhorses.entities.GenericHorse;

public class GenericHorseRenderer extends MobRenderer<GenericHorse, GenericHorseModel> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(MythicalHorses.MODID, "textures/entities/generic_horse.png");

    public GenericHorseRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GenericHorseModel(ctx.bakeLayer(GenericHorseModel.LAYER_LOCATION)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(GenericHorse entity) {
        return TEXTURE;
    }
}
