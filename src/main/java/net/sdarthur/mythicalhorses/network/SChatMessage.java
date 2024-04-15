package net.sdarthur.mythicalhorses.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SChatMessage {
    private final String MESSAGE_SOMETHING = "message.mythicalhorses.something_is_happening";

    public SChatMessage() {

    }

    public SChatMessage(FriendlyByteBuf buffer) {

    }

    public void encode(FriendlyByteBuf buffer) {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        player.sendSystemMessage(Component.translatable(MESSAGE_SOMETHING));
    }
}
