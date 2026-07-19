package com.github.lunatrius.ingameinfo.network.message;

import com.github.lunatrius.ingameinfo.network.PacketHandler;
import com.github.lunatrius.ingameinfo.tag.TagPerformance;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePerformanceRequest implements IMessage {
    @Override
    public void fromBytes(final ByteBuf buf) {
    }

    @Override
    public void toBytes(final ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<MessagePerformanceRequest, IMessage> {
        @Override
        public IMessage onMessage(final MessagePerformanceRequest message, final MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().player;
            final IThreadListener mainThread = (WorldServer) player.world;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    final MinecraftServer server = player.server;
                    if (server == null) {
                        return;
                    }

                    final double meanTickTime = TagPerformance.getMeanTickTime(server.tickTimeArray);
                    PacketHandler.INSTANCE.sendTo(new MessagePerformanceResponse(meanTickTime, TagPerformance.getTps(meanTickTime)), player);
                }
            });
            return null;
        }
    }
}
