package com.noveogroup.debugdrawer.module;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.noveogroup.debugdrawer.R;
import com.noveogroup.debugdrawer.Utils;
import com.noveogroup.debugdrawer.api.SupportDebugModule;
import com.noveogroup.debugdrawer.data.model.Endpoint;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@SuppressWarnings({"PMD.AvoidThrowingNullPointerException", "WeakerAccess"})
public class EndpointsModule extends SupportDebugModule {

    final List<Endpoint> endpoints;
    Spinner serverSpinner;

    public EndpointsModule(final List<Endpoint> endpoints) {
        super();
        if (Utils.isEmpty(endpoints)) {
            throw new NullPointerException("Endpoints can't be empty");
        }

        this.endpoints = endpoints;

        if (getSettings().readEndpoint() == null) {
            getSettings().changeEndpoint(endpoints.get(0)).subscribe();
        }
    }

    private static <T, R> List<R> convertList(final @NonNull List<T> source, final Function<T, R> converter) {
        return Observable.fromIterable(source).map(converter).toList().blockingGet();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        final Context context = inflater.getContext();
        final View view = inflater.inflate(R.layout.dd_debug_drawer_module_endpoints, parent, false);
        view.setClickable(false);
        view.setEnabled(false);

        serverSpinner = view.findViewById(R.id.dd_debug_tools_spinner_server);
        serverSpinner.setAdapter(new ArrayAdapter<String>(context, R.layout.dd_spinner_selected, convertList(endpoints, Endpoint::getUrl)) {
            @Override
            public View getDropDownView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
                final Endpoint selectedEndpoint = getSettings().readEndpoint();
                final TextView itemView = (TextView) LayoutInflater.from(context).inflate(R.layout.dd_spinner_regular, parent, false);
                final Endpoint item = endpoints.get(position);
                itemView.setText(item.getName());
                itemView.setSelected(item.equals(selectedEndpoint));
                return itemView;
            }
        });
        serverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                final Endpoint prev = getSettings().readEndpoint();
                final Endpoint next = endpoints.get(position);
                handleSelection(context, prev, next);
            }

            @Override
            public void onNothingSelected(final AdapterView<?> adapterView) {
                serverSpinner.setSelection(0);
            }
        });

        refresh();
        return view;
    }

    void handleSelection(final Context context, final Endpoint prev, final Endpoint next) {
        if (prev == null || !next.equals(prev)) {
            final String message = context.getString(
                    R.string.dd_alert_restart_url_message,
                    prev == null ? "{NONE}" : prev.getUrl(),
                    next.getUrl());
            showConfirmationDialog(
                    context,
                    message,
                    () -> getSettings().changeEndpoint(next).subscribe(EndpointsModule.this::rebirth),
                    () -> {         //revert settings
                        final int previousPosition = endpoints.indexOf(prev);
                        if (previousPosition >= 0) {
                            serverSpinner.setSelection(previousPosition);
                        }
                    });
        }
    }

    @Override
    public void onOpened() {
        refresh();
    }

    private void refresh() {
        final Endpoint settingsUrl = getSettings().readEndpoint();
        final int position = endpoints.indexOf(settingsUrl);
        if (position >= 0) {
            serverSpinner.setSelection(position);
        }
    }

}
