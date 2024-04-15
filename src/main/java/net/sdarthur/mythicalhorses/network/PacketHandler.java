package net.sdarthur.mythicalhorses.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.sdarthur.mythicalhorses.MythicalHorses;

public class PacketHandler {
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(MythicalHorses.MODID, "main"))
            .serverAcceptedVersions(s -> true)
            .clientAcceptedVersions(s -> true)
            .networkProtocolVersion(() -> "1.0")
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(SAmuletPickUp.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .decoder(SAmuletPickUp::new)
                .encoder(SAmuletPickUp::encode)
                .consumerMainThread(SAmuletPickUp::handle)
                .add();

        INSTANCE.messageBuilder(SAmuletSpawn.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .decoder(SAmuletSpawn::new)
                .encoder(SAmuletSpawn::encode)
                .consumerMainThread(SAmuletSpawn::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
