package com.noveogroup.debugdrawer;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GradleModule extends SupportDebugModule {

    public GradleModule(final Class buildConfigClass) {
        //do nothing
    }

    public GradleModule(final String buildSource, final String buildDate, final String buildFlavor, final String buildType) {
        //do nothing
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        return new View(parent.getContext());
    }

}
