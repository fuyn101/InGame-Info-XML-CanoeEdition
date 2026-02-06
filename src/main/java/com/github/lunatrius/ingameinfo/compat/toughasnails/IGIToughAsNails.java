package com.github.lunatrius.ingameinfo.compat.toughasnails;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

@Mod(useMetadata = true, modid = "igi|toughasnails")
public class IGIToughAsNails
{
    public static ModMetadata metadata;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        metadata = event.getModMetadata();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT) {
            return;
        }
        // Register tags
        if (Loader.isModLoaded("toughasnails")) {
            TagTAN.register();
        }
    }

    @NetworkCheckHandler
    public boolean allAreWelcome(Map<String,String> modList, Side side) {
        return true;
    }
}