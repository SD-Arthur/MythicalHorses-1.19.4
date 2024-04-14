package net.sdarthur.mythicalhorses.network;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
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
            .networkProtocolVersion(() -> "1")
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(SAmuletPickUp.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(SAmuletPickUp::encode)
                .decoder(SAmuletPickUp::new)
                .consumerMainThread(SAmuletPickUp::handle)
                .add();

        INSTANCE.messageBuilder(SAmuletSpawn.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(SAmuletSpawn::encode)
                .decoder(SAmuletSpawn::new)
                .consumerMainThread(SAmuletSpawn::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        PacketDistributor.SERVER.noArg().send((Packet<?>) msg);
    }
}
