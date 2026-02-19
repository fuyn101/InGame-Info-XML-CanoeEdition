package com.github.lunatrius.ingameinfo.compat.bloodmagic.bmnetwork;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import com.github.lunatrius.ingameinfo.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BMRemoteDataMessage implements IMessage {

    public BMRemoteDataMessage() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<BMRemoteDataMessage, BMResponseMessage> {

        public Handler() {
        }

        @Override
        public BMResponseMessage onMessage(BMRemoteDataMessage message, MessageContext ctx) {
            final NBTTagCompound data = new NBTTagCompound();
            final EntityPlayerMP player = ctx.getServerHandler().player;
            IThreadListener mainThread = (WorldServer) player.world;
            mainThread.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        SoulNetwork soulNet = NetworkHelper.getSoulNetwork(player);
                        data.setInteger("CurrentLP", soulNet.getCurrentEssence());
                        data.setInteger("OrbTier", soulNet.getOrbTier());
                        BMResponseMessage response = new BMResponseMessage(data);
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