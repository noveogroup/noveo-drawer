
package com.noveogroup.debugdrawer.data.preferences;

import android.content.Context;

import com.noveogroup.preferences.NoveoPreferences;
import com.noveogroup.preferences.api.RxPreference;

import java.util.HashMap;
import java.util.Map;

public class DeveloperPreferences {

    private final Map<String, RxPreference<Boolean>> enablersMap;
    private final Map<String, RxPreference<String>> selectorMap;

    private final NoveoPreferences preferences;

    public DeveloperPreferences(final Context context) {
        enablersMap = new HashMap<>();
        selectorMap = new HashMap<>();

        preferences = new NoveoPreferences(context, "noveo.debug.drawer");
    }

    public RxPreference<String> getSelectorByName(final String name) {
        if (!selectorMap.containsKey(name)) {
            selectorMap.put(name, preferences.getString(name).rx());
        }
        return selectorMap.get(name);
    }

    public RxPreference<Boolean> getEnablerByName(final String name) {
        if (!enablersMap.containsKey(name)) {
            enablersMap.put(name, preferences.getBoolean(name, false).rx());
        }
        return enablersMap.get(name);
    }

}
