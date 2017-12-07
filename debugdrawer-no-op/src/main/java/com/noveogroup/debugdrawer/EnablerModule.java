package com.noveogroup.debugdrawer;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

public class EnablerModule extends SupportDebugModule {

    @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
    public EnablerModule() {
        super();
    }

    public static void init(final Enabler... enablers) {
        NoveoDebugDrawer.initEnablers(Arrays.asList(enablers));
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        return new View(parent.getContext());
    }

}
