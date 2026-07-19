package com.github.lunatrius.ingameinfo.network.message;

import com.github.lunatrius.ingameinfo.tag.TagPerformance;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePerformanceResponse implements IMessage {
    private double meanTickTime;
    private double meanTps;

    public MessagePerformanceResponse() {
    }

    public MessagePerformanceResponse(final double meanTickTime, final double meanTps) {
        this.meanTickTime = meanTickTime;
        this.meanTps = meanTps;
    }

    @Override
    public void fromBytes(final ByteBuf buf) {
        this.meanTickTime = buf.readDouble();
        this.meanTps = buf.readDouble();
    }

    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeDouble(this.meanTickTime);
        buf.writeDouble(this.meanTps);
    }

    public static class Handler implements IMessageHandler<MessagePerformanceResponse, IMessage> {
        @Override
        public IMessage onMessage(final MessagePerformanceResponse message, final MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    TagPerformance.setRemoteData(message.meanTickTime, message.meanTps);
                }
            });
            return null;
        }
    }
}
