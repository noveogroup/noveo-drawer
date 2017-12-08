package com.noveogroup.debugdrawer;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings({"PMD.AvoidThrowingNullPointerException", "WeakerAccess"})
public class ConfigDebugModule extends SupportDebugModule {

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        return new View(parent.getContext());
    }

    @Override
    public String getDebugInfo() {
        return "no op";
    }

}
