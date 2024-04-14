package net.sdarthur.mythicalhorses.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.items.Amulet;

import java.util.UUID;
import java.util.function.Supplier;

public class SAmuletPickUp {
    private final UUID horse;

    public SAmuletPickUp(UUID horse) {
        this.horse = horse;
    }

    public SAmuletPickUp(FriendlyByteBuf buffer) {
        this(buffer.readUUID());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(horse);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer sender = context.get().getSender();
        if (sender == null) {
            return;
        }
        sender.level.getEntitiesOfClass(GenericHorse.class, sender.getBoundingBox().inflate(8D), v -> v.getUUID().equals(horse)).stream().findAny().ifPresent(GenericHorse -> {
            Amulet.pickUp(GenericHorse, sender);
        });
    }
}
