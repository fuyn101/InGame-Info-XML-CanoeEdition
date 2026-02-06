package com.github.lunatrius.ingameinfo.compat.thaumcraft;

import com.github.lunatrius.ingameinfo.compat.thaumcraft.network.RemoteDataMessage;
import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.capabilities.IPlayerWarp;
import thaumcraft.api.capabilities.ThaumcraftCapabilities;

public abstract class TagThaumcraft extends Tag
{
    static long lastRemoteUpdate = 0;

    @Override
    public String getCategory() {
        return "thaumcraft";
    }

    public static class LocalAura extends TagThaumcraft {

        @Override
        public String getValue() {
            try {
                if (world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 500 || delay < 0) {
                        IGIThaumcraft.network.sendToServer(new RemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return Float.toString(IGIThaumcraft.cachedData.getFloat("LocalVis"));
                } else {
                    return Float.toString(AuraHelper.getVis(player.world, player.getPosition()));
                }
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class LocalFlux extends TagThaumcraft {

        @Override
        public String getValue() {
            try {
                if (world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 500 || delay < 0) {
                        IGIThaumcraft.network.sendToServer(new RemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return Float.toString(IGIThaumcraft.cachedData.getFloat("LocalFlux"));
                } else {
                    return Float.toString(AuraHelper.getFlux(player.world, player.getPosition()));
                }
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class PermWarp extends TagThaumcraft {

        @Override
        public String getValue() {
            IPlayerWarp warp = player.getCapability(ThaumcraftCapabilities.WARP, null);
            return Integer.toString(warp.get(IPlayerWarp.EnumWarpType.PERMANENT));
        }
    }

    public static class NormalWarp extends TagThaumcraft {

        @Override
        public String getValue() {
            IPlayerWarp warp = player.getCapability(ThaumcraftCapabilities.WARP, null);
            return Integer.toString(warp.get(IPlayerWarp.EnumWarpType.NORMAL));
        }
    }

    public static class TempWarp extends TagThaumcraft {

        @Override
        public String getValue() {
            IPlayerWarp warp = player.getCapability(ThaumcraftCapabilities.WARP, null);
            return Integer.toString(warp.get(IPlayerWarp.EnumWarpType.TEMPORARY));
        }
    }

    public static void register() {
        TagRegistry.INSTANCE.register(new LocalAura().setName("thaumaura"));
        TagRegistry.INSTANCE.register(new LocalFlux().setName("thaumflux"));
        TagRegistry.INSTANCE.register(new PermWarp().setName("thaumwarpperm"));
        TagRegistry.INSTANCE.register(new NormalWarp().setName("thaumwarpnormal"));
        TagRegistry.INSTANCE.register(new TempWarp().setName("thaumwarptemp"));
    }

    public void log(Tag tag, Throwable ex) {
        IGIThaumcraft.getLogger().warn(IGIThaumcraft.metadata.modId + ":" + tag.getName(), ex);
    }
}