package com.github.lunatrius.ingameinfo.tag;

import com.github.lunatrius.ingameinfo.network.PacketHandler;
import com.github.lunatrius.ingameinfo.network.message.MessagePerformanceRequest;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import net.minecraft.server.MinecraftServer;

import java.util.Locale;

public abstract class TagPerformance extends Tag {
    private static final long REMOTE_UPDATE_INTERVAL = 1500L;

    private static long lastRemoteUpdate;
    private static double meanTickTime;
    private static double meanTps;

    @Override
    public String getCategory() {
        return "performance";
    }

    public static void setRemoteData(final double meanTickTime, final double meanTps) {
        TagPerformance.meanTickTime = meanTickTime;
        TagPerformance.meanTps = meanTps;
    }

    public static double getMeanTickTime(final long[] tickTimes) {
        long total = 0L;
        for (final long tickTime : tickTimes) {
            total += tickTime;
        }
        return total / tickTimes.length * 1.0E-6D;
    }

    public static double getTps(final double meanTickTime) {
        return Math.min(1000.0D / meanTickTime, 20.0D);
    }

    private static void requestRemoteData() {
        final long now = System.currentTimeMillis();
        final long delay = now - lastRemoteUpdate;
        if (delay > REMOTE_UPDATE_INTERVAL || delay < 0L) {
            lastRemoteUpdate = now;
            PacketHandler.INSTANCE.sendToServer(new MessagePerformanceRequest());
        }
    }

    private static String getPerformanceValue(final boolean tps) {
        if (world == null) {
            return "-1";
        }

        if (world.isRemote) {
            requestRemoteData();
            return String.format(Locale.ENGLISH, "%.2f", tps ? meanTps : meanTickTime);
        }

        final MinecraftServer server = world.getMinecraftServer();
        if (server == null) {
            return "-1";
        }

        final double meanTickTime = getMeanTickTime(server.tickTimeArray);
        return String.format(Locale.ENGLISH, "%.2f", tps ? getTps(meanTickTime) : meanTickTime);
    }

    public static class TPS extends TagPerformance {
        @Override
        public String getValue() {
            return getPerformanceValue(true);
        }
    }

    public static class MSPT extends TagPerformance {
        @Override
        public String getValue() {
            return getPerformanceValue(false);
        }
    }

    public static void register() {
        TagRegistry.INSTANCE.register(new TPS().setName("tps"));
        TagRegistry.INSTANCE.register(new MSPT().setName("mspt"));
    }
}
