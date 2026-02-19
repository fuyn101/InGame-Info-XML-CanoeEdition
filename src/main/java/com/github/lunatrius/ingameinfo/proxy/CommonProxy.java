package com.github.lunatrius.ingameinfo.proxy;

import com.github.lunatrius.ingameinfo.core.handler.ConfigurationHandler;
import com.github.lunatrius.ingameinfo.core.version.VersionChecker;
import com.github.lunatrius.ingameinfo.network.PacketHandler;
import com.github.lunatrius.ingameinfo.reference.Reference;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
    public void preInit(final FMLPreInitializationEvent event) {
        Reference.logger = event.getModLog();
        com.github.lunatrius.ingameinfo.handler.ConfigurationHandler.init(event.getSuggestedConfigurationFile());

    }

    public void init(final FMLInitializationEvent event) {
        PacketHandler.init();
    }

    public void postInit(final FMLPostInitializationEvent event) {
        if (VersionChecker.isAllowedToCheck("Global") && ConfigurationHandler.VersionCheck.checkForUpdates) {
            VersionChecker.startVersionCheck();
        }
    }

    public void processIMC(final FMLInterModComms.IMCEvent event) {
        for (final FMLInterModComms.IMCMessage message : event.getMessages()) {
            if ("checkUpdate".equals(message.key) && message.isStringMessage()) {
                processMessage(message.getSender(), message.getStringValue());
            }
        }
    }

    private void processMessage(final String sender, final String forgeVersion) {
        final ModContainer container = Loader.instance().getIndexedModList().get(sender);
        if (container != null) {
            VersionChecker.registerMod(container, forgeVersion);
        }
    }

    public void serverStarting(final FMLServerStartingEvent event) {
    }

    public void serverStopping(final FMLServerStoppingEvent event) {
    }
}
