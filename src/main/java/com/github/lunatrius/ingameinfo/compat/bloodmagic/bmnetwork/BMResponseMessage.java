package com.github.lunatrius.ingameinfo.compat.bloodmagic.bmnetwork;

import com.github.lunatrius.ingameinfo.compat.bloodmagic.TagBloodMagic;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BMResponseMessage implements IMessage {

    public NBTTagCompound data;

    public BMResponseMessage() {
    }

    public BMResponseMessage(NBTTagCompound data) {
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

    public static class ResponseHandler implements IMessageHandler<BMResponseMessage, IMessage> {

        public ResponseHandler() {
        }

        @Override
        public IMessage onMessage(BMResponseMessage message, final MessageContext ctx) {
            if (message.data.hasKey("OrbTier")) {
                TagBloodMagic.cachedData = message.data.copy();
            }
            return null;
        }
    }
}