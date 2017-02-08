package com.torv.adam.changeskin;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** init skin */
        String packageName = "";
        Bundle bundle = getIntent().getExtras();
        if(null != bundle) {
            packageName = bundle.getString(Skin.BUNDLE_KEY_PACKAGE_NAME, "");
        }
        Skin.instance.init(this, packageName);
        /** init skin */

        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.id_textView);
        btn = (Button) findViewById(R.id.id_btn);

        textView.setTextColor(Skin.instance.getColor("colorPrimary"));
        btn.getBackground().setColorFilter(Skin.instance.getColor("colorPrimary"), PorterDuff.Mode.MULTIPLY);
    }
}