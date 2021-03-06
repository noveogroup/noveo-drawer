package com.noveogroup.debugdrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noveogroup.debugdrawer.buildconfig.R;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"PMD.AvoidThrowingNullPointerException", "WeakerAccess"})
public class SelectorModule extends ConfigDebugModule {

    final Map<String, SelectorViewHolder> holders;

    final SelectorProvider provider;
    final SettingsWriter<String> writer;

    public SelectorModule(final DebugBuildConfiguration configuration) {
        super(configuration);
        this.holders = new LinkedHashMap<>();
        provider = configuration.getSelectorProvider();
        writer = configuration.getSelectorWriter();

        if (Utils.isEmpty(provider.names())) {
            throw new NullPointerException("Selectors not found");
        }

        writer.applyInitial();
    }

    public static void init(final DebugBuildConfiguration configuration, final Selector... selectors) {
        configuration.initSelectors(Arrays.asList(selectors));
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @NonNull final ViewGroup parent) {
        final Context context = inflater.getContext();
        final LinearLayout gridLayout = (LinearLayout) inflater.inflate(R.layout.dd_debug_drawer_module_selector, parent, false);
        gridLayout.setClickable(false);
        gridLayout.setEnabled(false);

        for (final String name : provider.names()) {
            inflater.inflate(R.layout.dd_item_selector_include, gridLayout, true);

            final SelectorViewHolder holder = new SelectorViewHolder(gridLayout);
            final List<String> values = provider.values(name);

            holder.name.setText(name + ":");
            holder.spinner.setAdapter(new ArrayAdapter<String>(context, R.layout.dd_spinner_selected, values) {
                @Override
                public View getDropDownView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
                    final String selected = provider.read(name);
                    final TextView itemView = (TextView) LayoutInflater.from(context).inflate(R.layout.dd_spinner_regular, parent, false);
                    final String item = values.get(position);
                    itemView.setText(item);
                    itemView.setSelected(item.equals(selected));
                    return itemView;
                }
            });

            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(final AdapterView<?> adapterView, final View view, final int position, final long id) {
                    final String prev = provider.read(name);
                    final String next = values.get(position);
                    handleSelection(context, name, values, prev, next);
                }

                @Override
                public void onNothingSelected(final AdapterView<?> adapterView) {
                    holder.spinner.setSelection(0);
                }
            });

            holders.put(name, holder);
        }

        refresh();
        return gridLayout;
    }

    void handleSelection(final Context context, final String name, final List<String> values, final String prev, final String next) {
        if (prev == null || !next.equals(prev)) {
            final CharSequence message = html(context.getString(
                    R.string.dd_alert_restart_selector_message,
                    name, prev == null ? "{NONE}" : prev, next));
            showConfirmationDialog(
                    context,
                    message,
                    () -> {
                        writer.change(name, next);
                        rebirth();
                    },
                    () -> {         //revert settings
                        final int previousPosition = values.indexOf(prev);
                        if (previousPosition >= 0) {
                            holders.get(name).spinner.setSelection(previousPosition);
                        }
                    });
        }
    }

    @Override
    public void onOpened() {
        refresh();
    }

    @Override
    public String getDebugInfo() {
        final StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append("Selectors = {\n");
        for (final String name : holders.keySet()) {
            stringBuilder.append("  \"").append(name).append("\": [\n");
            final String selected = provider.read(name);
            for (final String value : provider.values(name)) {
                stringBuilder
                        .append("    ")
                        .append(value)
                        .append(TextUtils.equals(value, selected) ? " [+]" : "")
                        .append(",\n");
            }
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "\n  ]");
            stringBuilder.append(",\n");
        }
        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "\n}");
        return stringBuilder.toString();
    }

    private void refresh() {
        for (final String name : provider.names()) {
            final String value = provider.read(name);
            final int position = provider.values(name).indexOf(value);
            if (position >= 0) {
                holders.get(name).spinner.setSelection(position);
            }
        }
    }

    public static class SelectorViewHolder {
        TextView name;
        AppCompatSpinner spinner;

        public SelectorViewHolder(final ViewGroup view) {
            name = (TextView) view.getChildAt(view.getChildCount() - 2);
            spinner = (AppCompatSpinner) view.getChildAt(view.getChildCount() - 1);
        }
    }

}
