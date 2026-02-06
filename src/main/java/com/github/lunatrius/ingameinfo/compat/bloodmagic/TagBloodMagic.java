package com.github.lunatrius.ingameinfo.compat.bloodmagic;

import WayofTime.bloodmagic.util.helper.NetworkHelper;
import com.github.lunatrius.ingameinfo.compat.bloodmagic.network.RemoteDataMessage;
import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;

public abstract class TagBloodMagic extends Tag
{
    @Override
    public String getCategory() {
        return "bloodmagic";
    }

    protected static long lastRemoteUpdate = 0;


    public static class CurrentLP extends TagBloodMagic {

        @Override
        public String getValue() {
            try {
                if (world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 500 || delay < 0) {
                        IGIBloodMagic.network.sendToServer(new RemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return String.valueOf(IGIBloodMagic.cachedData.getTag("CurrentLP"));
                } else {
                    return String.valueOf(NetworkHelper.getSoulNetwork(player).getCurrentEssence());
                }
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class OrbTier extends TagBloodMagic {
        @Override
        public String getValue() {
            try {
                if (world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 500 || delay < 0) {
                        IGIBloodMagic.network.sendToServer(new RemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return String.valueOf(IGIBloodMagic.cachedData.getTag("OrbTier"));
                } else {
                    return String.valueOf(NetworkHelper.getSoulNetwork(player).getOrbTier());
                }
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }


    public static void register() {
        TagRegistry.INSTANCE.register(new CurrentLP().setName("bmcurrentlp"));
        TagRegistry.INSTANCE.register(new OrbTier().setName("bmorbtier"));
    }

    public void log(Tag tag, Throwable ex) {
        IGIBloodMagic.getLogger().warn(IGIBloodMagic.metadata.modId + ":" + tag.getName(), ex);
    }
}