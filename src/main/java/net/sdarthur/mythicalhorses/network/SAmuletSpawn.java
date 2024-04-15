package net.sdarthur.mythicalhorses.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;
import net.sdarthur.mythicalhorses.init.EntityInit;

import java.util.function.Supplier;

public class SAmuletSpawn {
    private final BlockPos looking;

    public SAmuletSpawn(BlockPos looking) {
        this.looking = looking;
    }

    public SAmuletSpawn(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(looking);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if(player == null) {
                return;
            }

            ServerLevel level = (ServerLevel) player.level;
            EntityInit.GENERIC_HORSE.get().spawn(level, looking, MobSpawnType.MOB_SUMMONED);
        });
    }
}
