package com.github.lunatrius.ingameinfo.compat.bloodmagic;

import WayofTime.bloodmagic.util.helper.NetworkHelper;
import com.github.lunatrius.ingameinfo.compat.bloodmagic.bmnetwork.BMRemoteDataMessage;
import com.github.lunatrius.ingameinfo.network.PacketHandler;
import com.github.lunatrius.ingameinfo.reference.Reference;
import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TagBloodMagic extends Tag
{
    public static NBTTagCompound cachedData = new NBTTagCompound();

    @Override
    public String getCategory() {
        return "bloodmagic";
    }

    protected static long lastRemoteUpdate = 0;


    public static class CurrentLP extends TagBloodMagic {

        @Override
        public String getValue() {
            try {
                if (world != null && world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 500 || delay < 0) {
                        PacketHandler.INSTANCE.sendToServer(new BMRemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return String.valueOf(cachedData.getTag("CurrentLP"));
                } else if (player != null) {
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
                if (world != null && world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 500 || delay < 0) {
                        PacketHandler.INSTANCE.sendToServer(new BMRemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return String.valueOf(cachedData.getTag("OrbTier"));
                } else if (player != null) {
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
        Reference.logger.warn(Reference.MODID + ":" + tag.getName(), ex);
    }
}