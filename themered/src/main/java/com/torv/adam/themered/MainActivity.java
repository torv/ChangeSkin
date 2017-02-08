package com.torv.adam.themered;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onApplyClicked(View view) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.torv.adam.changeskin", "com.torv.adam.changeskin.MainActivity"));
        Bundle bundle = new Bundle();
        bundle.putString("bundle_key_package_name", getPackageName());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
