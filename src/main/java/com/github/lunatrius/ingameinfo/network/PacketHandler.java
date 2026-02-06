package com.github.lunatrius.ingameinfo.network;

import com.github.lunatrius.ingameinfo.network.message.MessageSeed;
import com.github.lunatrius.ingameinfo.reference.Reference;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);

    public static void init() {
        INSTANCE.registerMessage(MessageSeed.class, MessageSeed.class, 0, Side.CLIENT);

        if (Loader.isModLoaded("thaumcraft")) {
            INSTANCE.registerMessage(com.github.lunatrius.ingameinfo.compat.thaumcraft.network.ResponseMessage.ResponseHandler.class, com.github.lunatrius.ingameinfo.compat.thaumcraft.network.ResponseMessage.class, 1, Side.CLIENT);
            INSTANCE.registerMessage(com.github.lunatrius.ingameinfo.compat.thaumcraft.network.RemoteDataMessage.Handler.class, com.github.lunatrius.ingameinfo.compat.thaumcraft.network.RemoteDataMessage.class, 2, Side.SERVER);
        }

        if (Loader.isModLoaded("bloodmagic")) {
            INSTANCE.registerMessage(com.github.lunatrius.ingameinfo.compat.bloodmagic.network.ResponseMessage.ResponseHandler.class, com.github.lunatrius.ingameinfo.compat.bloodmagic.network.ResponseMessage.class, 3, Side.CLIENT);
            INSTANCE.registerMessage(com.github.lunatrius.ingameinfo.compat.bloodmagic.network.RemoteDataMessage.Handler.class, com.github.lunatrius.ingameinfo.compat.bloodmagic.network.RemoteDataMessage.class, 4, Side.SERVER);
        }
    }
}
