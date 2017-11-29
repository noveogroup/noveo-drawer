
package com.noveogroup.debugdrawer.data.preferences;

import android.content.Context;

import com.noveogroup.debugdrawer.data.model.Endpoint;
import com.noveogroup.preferences.NoveoPreferences;
import com.noveogroup.preferences.api.RxPreference;

public class DeveloperPreferences {

    public final RxPreference<Boolean> stethoEnabled;
    public final RxPreference<Boolean> leakCanaryEnabled;
    public final RxPreference<Endpoint> backendUrl;

    public DeveloperPreferences(final Context context) {
        final NoveoPreferences drawerPreferences = new NoveoPreferences(context, "debugdrawer");
        stethoEnabled = drawerPreferences.getBoolean("developer.key_stetho", false).rx();
        leakCanaryEnabled = drawerPreferences.getBoolean("developer.key.leak_canary", false).rx();
        backendUrl = drawerPreferences.getObject("developer.key.server_url", Endpoint.PREFERENCE_STRATEGY).rx();
    }


}
