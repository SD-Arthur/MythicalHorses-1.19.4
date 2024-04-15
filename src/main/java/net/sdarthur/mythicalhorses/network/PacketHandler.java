package net.sdarthur.mythicalhorses.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.sdarthur.mythicalhorses.MythicalHorses;

public class PacketHandler {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named( new ResourceLocation(MythicalHorses.MODID, "main"))
                .serverAcceptedVersions(s -> true)
                .clientAcceptedVersions(s -> true)
                .networkProtocolVersion(() -> "1.0")
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(SAmuletPickUp.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SAmuletPickUp::new)
                .encoder(SAmuletPickUp::encode)
                .consumerMainThread(SAmuletPickUp::handle)
                .add();

        net.messageBuilder(SAmuletSpawn.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SAmuletSpawn::new)
                .encoder(SAmuletSpawn::encode)
                .consumerMainThread(SAmuletSpawn::handle)
                .add();

        net.messageBuilder(SChatMessage.class, id() ,NetworkDirection.PLAY_TO_SERVER)
                .decoder(SChatMessage::new)
                .encoder(SChatMessage::encode)
                .consumerMainThread(SChatMessage::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
