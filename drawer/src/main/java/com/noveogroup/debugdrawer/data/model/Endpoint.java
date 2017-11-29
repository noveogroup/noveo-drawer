package com.noveogroup.debugdrawer.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.noveogroup.preferences.PreferenceStrategy;

import java.util.regex.Pattern;

public class Endpoint implements Parcelable {
    public static final PreferenceStrategy<Endpoint> PREFERENCE_STRATEGY = new EndpointStrategy();
    public static final Creator<Endpoint> CREATOR = new Creator<Endpoint>() {
        @Override
        public Endpoint createFromParcel(final Parcel source) {
            return new Endpoint(source);
        }

        @Override
        public Endpoint[] newArray(final int size) {
            return new Endpoint[size];
        }
    };

    private static final int HASHCODE_CONSTANT = 31;

    private final String name;
    private final String url;

    public Endpoint(final String name, final String url) {
        this.name = name;
        this.url = url;
    }

    protected Endpoint(final Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
    }

    @SuppressWarnings({"ControlFlowStatementWithoutBraces", "SimplifiableIfStatement"})
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Endpoint endpoint = (Endpoint) o;

        if (name != null ? !name.equals(endpoint.name) : endpoint.name != null) {
            return false;
        }
        return url != null ? url.equals(endpoint.url) : endpoint.url == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = HASHCODE_CONSTANT * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @SuppressWarnings("ConstantConditions")
    static class EndpointStrategy extends PreferenceStrategy<Endpoint> {
        private static final String SEPARATOR = "--//--";
        private static final Pattern PATTERN = Pattern.compile(SEPARATOR);

        EndpointStrategy() {
            super(
                    (editor, key, endpoint) -> editor.putString(key, endpoint.getName() + SEPARATOR + endpoint.getUrl()),
                    (sharedPreferences, key, endpoint) -> {
                        final String[] parts = PATTERN.split(sharedPreferences.getString(key, ""));
                        if (parts != null && parts.length == 2) {
                            return new Endpoint(parts[0], parts[1]);
                        }
                        return null;
                    },
                    RemoveAction.REMOVE_BY_KEY,
                    KeyFilter.EQUALS,
                    true);
        }
    }
}
