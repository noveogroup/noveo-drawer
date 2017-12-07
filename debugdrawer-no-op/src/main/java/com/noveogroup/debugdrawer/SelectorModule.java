package com.noveogroup.debugdrawer;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

@SuppressWarnings({"PMD.AvoidThrowingNullPointerException", "WeakerAccess"})
public class SelectorModule extends SupportDebugModule {

    public SelectorModule() {
        super();
    }

    public static void init(final Selector... selectors) {
        NoveoDebugDrawer.initSelectors(Arrays.asList(selectors));
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater layoutInflater, @NonNull final ViewGroup viewGroup) {
        return new View(viewGroup.getContext());
    }
}
