package com.github.lunatrius.ingameinfo.reference;

@SuppressWarnings("HardCodedStringLiteral")
public final class Names {
    public static final class Mods {
        public static final String BLOODMAGIC_MODID = "AWWayofTime";
        public static final String BLOODMAGIC_NAME = "Blood Magic";

        public static final String SIMPLYJETPACKS_MODID = "simplyjetpacks";
        public static final String SIMPLYJETPACKS_NAME = "Simply Jetpacks";

        public static final String TERRAFIRMACRAFT_MODID = "terrafirmacraft";
        public static final String TERRAFIRMACRAFT_NAME = "TerraFirmaCraft";

        public static final String THAUMCRAFT_MODID = "Thaumcraft";
        public static final String THAUMCRAFT_NAME = "Thaumcraft";
    }

    public static final class Command {
        public static final class Message {
            public static final String USAGE = "commands.ingameinfoxml.usage";
            public static final String RELOAD = "commands.ingameinfoxml.reload";
            public static final String LOAD = "commands.ingameinfoxml.load";
            public static final String SAVE = "commands.ingameinfoxml.save";
            public static final String SUCCESS = "commands.ingameinfoxml.success";
            public static final String FAILURE = "commands.ingameinfoxml.failure";
            public static final String ENABLE = "commands.ingameinfoxml.enable";
            public static final String DISABLE = "commands.ingameinfoxml.disable";
        }

        public static final String NAME = "igi";
        public static final String RELOAD = "reload";
        public static final String LOAD = "load";
        public static final String SAVE = "save";
        public static final String ENABLE = "enable";
        public static final String DISABLE = "disable";
        public static final String TAGLIST = "taglist";
        public static final String EDIT = "edit";
        public static final String ALIGNMENT = "alignment";
    }

    public static final class Config {
        public static final class Category {
            public static final String GENERAL = "general";
            public static final String ALIGNMENT = "alignment";
        }

        public static final String FILENAME = "filename";
        public static final String FILENAME_DESC = "The configuration that should be loaded on startup.";
        public static final String REPLACE_DEBUG = "replaceDebug";
        public static final String REPLACE_DEBUG_DESC = "Replace the debug overlay (F3) with the InGameInfoXML overlay.";
        public static final String SHOW_IN_CHAT = "showInChat";
        public static final String SHOW_IN_CHAT_DESC = "Display the overlay in chat.";
        public static final String SHOW_ON_PLAYER_LIST = "showOnPlayerList";
        public static final String SHOW_ON_PLAYER_LIST_DESC = "Display the overlay on the player list.";
        public static final String SCALE = "scale";
        public static final String SCALE_DESC = "The overlay will be scaled by this amount.";
        public static final String FILE_INTERVAL = "fileInterval";
        public static final String FILE_INTERVAL_DESC = "The interval between file reads for the 'file' tag (in seconds).";

        public static final String SHOW_OVERLAY_POTIONS = "showOverlayPotions";
        public static final String SHOW_OVERLAY_POTIONS_DESC = "Display the vanilla potion overlay.";

        public static final String SHOW_OVERLAY_ITEM_ICONS = "showOverlayItemIcons";
        public static final String SHOW_OVERLAY_ITEM_ICONS_DESC = "Display the item overlay on icon (durability, stack size).";

        public static final String ALIGNMENT_DESC = "Offsets for %s (X<space>Y).";

        public static final String LANG_PREFIX = Reference.MODID + ".config";
    }

    public static final class Files {
        public static final String NAME = "InGameInfo";

        public static final String FILE_XML = "InGameInfo.xml";
        public static final String FILE_JSON = "InGameInfo.json";
        public static final String FILE_TXT = "InGameInfo.txt";

        public static final String EXT_XML = ".xml";
        public static final String EXT_JSON = ".json";
        public static final String EXT_TXT = ".txt";
    }

    public static final class Keys {
        public static final String CATEGORY = "ingameinfoxml.key.category";
        public static final String TOGGLE = "ingameinfoxml.key.toggle";
        public static final String EDITOR = "ingameinfoxml.key.editor";
    }

    public static final class Editor {
        public static final String TITLE = "ingameinfoxml.editor.title";
        public static final String SAVE = "ingameinfoxml.editor.save";
        public static final String RELOAD = "ingameinfoxml.editor.reload";
        public static final String RESET = "ingameinfoxml.editor.reset";
        public static final String GRID = "ingameinfoxml.editor.grid";
        public static final String SNAP = "ingameinfoxml.editor.snap";
        public static final String POSITION = "ingameinfoxml.editor.position";
        public static final String ALIGNMENT = "ingameinfoxml.editor.alignment";
        public static final String SELECTED = "ingameinfoxml.editor.selected";
        public static final String POSITION_EDITOR = "ingameinfoxml.editor.positioneditor";
        public static final String ALIGNMENT_EDITOR = "ingameinfoxml.editor.alignmenteditor";
        public static final String CONFIRM_TITLE = "ingameinfoxml.editor.confirm.title";
        public static final String CONFIRM_MESSAGE = "ingameinfoxml.editor.confirm.message";
        public static final String CONFIRM_SAVE = "ingameinfoxml.editor.confirm.save";
        public static final String CONFIRM_DISCARD = "ingameinfoxml.editor.confirm.discard";
        public static final String CONFIRM_CANCEL = "ingameinfoxml.editor.confirm.cancel";
        public static final String CONFIG = "ingameinfoxml.editor.config";
        public static final String UNSAVED = "ingameinfoxml.editor.unsaved";
        public static final String ALIGNMENT_NONE = "ingameinfoxml.editor.alignment.none";
        public static final String QUICK_ALIGN = "ingameinfoxml.editor.quickalign";
        public static final String PREVIEW = "ingameinfoxml.editor.preview";
        public static final String X_OFFSET = "ingameinfoxml.editor.xoffset";
        public static final String Y_OFFSET = "ingameinfoxml.editor.yoffset";
        public static final String CONTROL_CLICK = "ingameinfoxml.editor.control.click";
        public static final String CONTROL_DRAG = "ingameinfoxml.editor.control.drag";
        public static final String CONTROL_TAB = "ingameinfoxml.editor.control.tab";
        public static final String CONTROL_ARROWS = "ingameinfoxml.editor.control.arrows";
        public static final String CONTROL_G = "ingameinfoxml.editor.control.g";
        public static final String CONTROL_CTRLS = "ingameinfoxml.editor.control.ctrls";
        public static final String CONTROL_CTRLR = "ingameinfoxml.editor.control.ctrlr";
        public static final String CONTROL_CTRLSNAP = "ingameinfoxml.editor.control.ctrlsnap";
    }
}
