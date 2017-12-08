package com.noveogroup.debugdrawer.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.noveogroup.debugdrawer.SelectorProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.palaima.debugdrawer.DebugDrawer;

@SuppressWarnings("FieldCanBeLocal")
public class SampleActivity extends AppCompatActivity {

    @BindView(R.id.server)
    TextView server;

    private DebugDrawer drawer;

    private DebugDrawerHelper drawerHelper;
    private SelectorProvider selectorProvider;
    private int themeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DI Injection
        this.selectorProvider = SampleApplication.injector.configuration.getSelectorProvider();
        this.drawerHelper = SampleApplication.injector.drawerHelper;
        this.themeId = drawerHelper.getTheme();

        //Create View
        setTheme(themeId);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Create Drawer
        if (savedInstanceState == null) {
            drawer = drawerHelper.makeDrawer(this);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        drawer.onStart();
        server.setText(
                "Endpoint: " + selectorProvider.read(DebugDrawerHelper.SELECTOR_ENDPOINT) + "\n" +
                        "Theme: " + selectorProvider.read(DebugDrawerHelper.SELECTOR_THEME));

    }

    @Override
    protected void onResume() {
        super.onResume();
        drawer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        drawer.onStop();
    }

}
