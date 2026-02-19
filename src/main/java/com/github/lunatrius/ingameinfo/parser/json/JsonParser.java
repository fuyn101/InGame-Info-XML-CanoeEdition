package com.github.lunatrius.ingameinfo.parser.json;

import com.github.lunatrius.ingameinfo.Alignment;
import com.github.lunatrius.ingameinfo.parser.IParser;
import com.github.lunatrius.ingameinfo.reference.Reference;
import com.github.lunatrius.ingameinfo.value.Value;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonParser implements IParser {
    private JsonElement element;

    @Override
    public boolean load(final InputStream inputStream) {
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            final com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

            this.element = parser.parse(inputStreamReader);

            inputStreamReader.close();
        } catch (final Exception e) {
            Reference.logger.fatal("Could not read json configuration file!", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean parse(final Map<Alignment, List<List<Value>>> format) {
        if (!this.element.isJsonObject()) {
            return false;
        }

        final JsonObject config = this.element.getAsJsonObject();
        final Set<Map.Entry<String, JsonElement>> entries = config.entrySet();

        for (final Map.Entry<String, JsonElement> entry : entries) {
            final Alignment alignment = Alignment.parse(entry.getKey());
            if (alignment != null) {
                final JsonElement value = entry.getValue();
                if (value.isJsonObject()) {
                    final JsonObject alignmentObj = value.getAsJsonObject();
                    if (alignmentObj.has("x")) {
                        alignment.x = alignmentObj.get("x").getAsInt();
                    }
                    if (alignmentObj.has("y")) {
                        alignment.y = alignmentObj.get("y").getAsInt();
                    }
                    if (alignmentObj.has("lines")) {
                        format.put(alignment, getLines(alignmentObj.get("lines")));
                    }
                } else if (value.isJsonArray()) {
                    format.put(alignment, getLines(value));
                }
            }
        }

        return true;
    }

    private List<List<Value>> getLines(final JsonElement elementLines) {
        final List<List<Value>> listLines = new ArrayList<List<Value>>();

        final JsonArray arrayLines = elementLines.getAsJsonArray();
        for (final JsonElement elementLine : arrayLines) {
            if (elementLine != null && elementLine.isJsonArray()) {
                listLines.add(getValues(elementLine.getAsJsonArray()));
            }
        }

        return listLines;
    }

    private List<Value> getValues(final JsonArray arrayValues) {
        final List<Value> values = new ArrayList<Value>();

        for (final JsonElement elementValue : arrayValues) {
            if (elementValue != null && elementValue.isJsonObject()) {
                final JsonObject object = elementValue.getAsJsonObject();
                final Set<Map.Entry<String, JsonElement>> entries = object.entrySet();
                for (final Map.Entry<String, JsonElement> entry : entries) {
                    final String type = entry.getKey();
                    final Value value = Value.fromString(type);

                    if (!value.isValid()) {
                        continue;
                    }

                    if (value.isSimple()) {
                        value.setRawValue(entry.getValue().getAsString(), false);
                    } else if (entry.getValue().isJsonArray()) {
                        value.values.addAll(getValues(entry.getValue().getAsJsonArray()));
                    }
                    values.add(value);
                }
            }
        }

        return values;
    }
}
