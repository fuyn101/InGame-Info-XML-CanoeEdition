package com.github.lunatrius.ingameinfo.compat.thaumcraft.tcnetwork;

import com.github.lunatrius.ingameinfo.compat.thaumcraft.TagThaumcraft;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TCResponseMessage implements IMessage {

    public NBTTagCompound data;

    public TCResponseMessage() {
    }

    public TCResponseMessage(NBTTagCompound data) {
        this.data = data.copy();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }

    public static class ResponseHandler implements IMessageHandler<TCResponseMessage, IMessage> {

        public ResponseHandler() {
        }

        @Override
        public IMessage onMessage(final TCResponseMessage message, final MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    if (message.data != null && message.data.hasKey("LocalFlux")) {
                        TagThaumcraft.cachedData = message.data.copy();
                    }
                }
            });
            return null;
        }
    }
}