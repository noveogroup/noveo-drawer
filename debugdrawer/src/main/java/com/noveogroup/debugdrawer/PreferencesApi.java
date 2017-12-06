
package com.noveogroup.debugdrawer;

import android.content.Context;

import com.noveogroup.preferences.NoveoPreferences;
import com.noveogroup.preferences.api.Preference;

import java.util.HashMap;
import java.util.Map;

final class PreferencesApi {

    private final Map<String, Preference<Boolean>> enablersMap;
    private final Map<String, Preference<String>> selectorMap;

    private final NoveoPreferences preferences;

    PreferencesApi(final Context context) {
        enablersMap = new HashMap<>();
        selectorMap = new HashMap<>();

        preferences = new NoveoPreferences(context, "noveo.debug.drawer");
    }

    Preference<String> getSelectorByName(final String name) {
        if (!selectorMap.containsKey(name)) {
            selectorMap.put(name, preferences.getString(name));
        }
        return selectorMap.get(name);
    }

    Preference<Boolean> getEnablerByName(final String name) {
        if (!enablersMap.containsKey(name)) {
            enablersMap.put(name, preferences.getBoolean(name, false));
        }
        return enablersMap.get(name);
    }

}
