package com.github.lunatrius.ingameinfo.tag.registry;

import com.github.lunatrius.ingameinfo.reference.Reference;
import com.github.lunatrius.ingameinfo.tag.*;

import java.util.*;

public class TagRegistry {
    public static final TagRegistry INSTANCE = new TagRegistry();

    private Map<String, Tag> stringTagMap = new HashMap<String, Tag>();

    private void register(final String name, final Tag tag) {
        if (this.stringTagMap.containsKey(name)) {
            Reference.logger.error("Duplicate tag key '" + name + "'!");
            return;
        }

        if (name == null) {
            Reference.logger.error("Tag name cannot be null!");
            return;
        }

        this.stringTagMap.put(name.toLowerCase(Locale.ENGLISH), tag);
    }

    public void register(final Tag tag) {
        register(tag.getName(), tag);

        for (final String name : tag.getAliases()) {
            register(name, tag);
        }
    }

    public String getValue(final String name) {
        final Tag tag = this.stringTagMap.get(name.toLowerCase(Locale.ENGLISH));
        return tag != null ? tag.getValue() : null;
    }

    public List<Tag> getRegisteredTags() {
        final List<Tag> tags = new ArrayList<Tag>();
        for (final Map.Entry<String, Tag> entry : this.stringTagMap.entrySet()) {
            tags.add(entry.getValue());
        }
        return tags;
    }

    public void init() {
        TagFormatting.register();
        TagMisc.register();
        TagPerformance.register();
        TagMouseOver.register();
        TagNearbyPlayer.register();
        TagPlayerEquipment.register();
        TagPlayerGeneral.register();
        TagPlayerPosition.register();
        TagPlayerPotion.register();
        TagRiding.register();
        TagTime.register();
        TagWorld.register();

        Reference.logger.info("Registered " + this.stringTagMap.size() + " tags.");
    }
}
