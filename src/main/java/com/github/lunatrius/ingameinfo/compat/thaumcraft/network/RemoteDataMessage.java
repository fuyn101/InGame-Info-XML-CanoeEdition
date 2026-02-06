package com.github.lunatrius.ingameinfo.compat.thaumcraft.network;

import com.github.lunatrius.ingameinfo.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thaumcraft.api.aura.AuraHelper;

public class RemoteDataMessage implements IMessage {

    public RemoteDataMessage() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<RemoteDataMessage, ResponseMessage> {

        public Handler() {
        }

        @Override
        public ResponseMessage onMessage(RemoteDataMessage message, MessageContext ctx) {
            final NBTTagCompound data = new NBTTagCompound();
            final EntityPlayerMP player = ctx.getServerHandler().player;
            IThreadListener mainThread = (WorldServer) player.world;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        data.setFloat("LocalVis", AuraHelper.getVis(player.world, player.getPosition()));
                        data.setFloat("LocalFlux", AuraHelper.getFlux(player.world, player.getPosition()));
                        ResponseMessage response = new ResponseMessage(data);
                        PacketHandler.INSTANCE.sendTo(response, player);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }
}