package com.github.lunatrius.ingameinfo.compat.toughasnails;

import com.github.lunatrius.ingameinfo.reference.Reference;
import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import net.minecraft.client.resources.I18n;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import toughasnails.api.TANCapabilities;
import toughasnails.temperature.TemperatureDebugger;
import toughasnails.temperature.TemperatureHandler;

public abstract class TagTAN extends Tag
{
    @Override
    public String getCategory() {
        return "toughasnails";
    }

    public static class CurrentTemp extends TagTAN {

        @Override
        public String getValue() {
            try {
                TemperatureHandler tempStats = (TemperatureHandler)player.getCapability(TANCapabilities.TEMPERATURE, player.getHorizontalFacing());
                return String.valueOf(tempStats.getTemperature().getRawValue());
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class TargetTemp extends TagTAN {

        @Override
        public String getValue() {
            try {
                TemperatureHandler tempStats = (TemperatureHandler)player.getCapability(TANCapabilities.TEMPERATURE, player.getHorizontalFacing());
                TemperatureDebugger tempDebug = tempStats.debugger;
                return String.valueOf(Math.min(Math.max(0,tempDebug.targetTemperature),25));
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class Season extends TagTAN {
        @Override
        public String getValue() {
            try {
                ISeasonState seasonData = SeasonHelper.getSeasonState(world);
                return getSeasonName(seasonData.getSubSeason());
            } catch (Throwable e) {
                log(this, e);
            }
            return "Unknown";
        }

        private String getSeasonName(sereneseasons.api.season.Season.SubSeason season) {
            return I18n.format("toughasnails.subseason." + season.name().replace("_","").toLowerCase() + ".desc");
        }
    }

    static void register() {
        TagRegistry.INSTANCE.register(new TagTAN.CurrentTemp().setName("tancurrtemp"));
        TagRegistry.INSTANCE.register(new TagTAN.TargetTemp().setName("tantargettemp"));
        TagRegistry.INSTANCE.register(new TagTAN.Season().setName("tanseason"));
    }

    void log(Tag tag, Throwable ex) {
        Reference.logger.warn(Reference.MODID + ":" + tag.getName(), ex);
    }
}