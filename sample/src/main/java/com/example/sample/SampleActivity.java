package com.example.sample;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.noveogroup.debugdrawer.api.NoveoDebugDrawer;
import com.noveogroup.debugdrawer.api.provider.InspectionProvider;
import com.noveogroup.debugdrawer.data.model.Endpoint;

import io.palaima.debugdrawer.DebugDrawer;

@SuppressWarnings("FieldCanBeLocal")
public class SampleActivity extends AppCompatActivity {

    private DebugDrawer drawer;
    private TextView server;
    private TextView stetho;
    private TextView canary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        server = findViewById(R.id.server);
        stetho = findViewById(R.id.stetho);
        canary = findViewById(R.id.canary);

        if (savedInstanceState == null) {
            drawer = DebugDrawerHelper.makeDrawer(this);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        drawer.onStart();

        final Endpoint endpoint = NoveoDebugDrawer.getEndpointProvider().getEndpoint();
        server.setText("Endpoint: " + (endpoint != null ? endpoint.getUrl() : "null"));

        final InspectionProvider inspections = NoveoDebugDrawer.getInspectionProvider();
        stetho.setText(inspections.isStethoEnabled() ? "enabled" : "disabled");
        canary.setText(inspections.isLeakCanaryEnabled() ? "enabled" : "disabled");
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
