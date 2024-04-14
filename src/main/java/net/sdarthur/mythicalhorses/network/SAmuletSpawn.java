package net.sdarthur.mythicalhorses.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;
import net.sdarthur.mythicalhorses.entities.GenericHorse;
import net.sdarthur.mythicalhorses.init.EntityInit;

import java.util.UUID;
import java.util.function.Supplier;

public class SAmuletSpawn {
    private final UUID horse;
    private final BlockPos looking;

    public SAmuletSpawn(UUID horse, BlockPos looking) {
        this.horse = horse;
        this.looking = looking;
    }

    public SAmuletSpawn(FriendlyByteBuf buffer) {
        this(buffer.readUUID(), buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUUID(horse);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        if(player == null) {return;}

        ServerLevel level = (ServerLevel) player.level;
        EntityType<GenericHorse> toSpawn = EntityInit.GENERIC_HORSE.get();
        toSpawn.spawn(level, looking, MobSpawnType.MOB_SUMMONED);
    }
}
