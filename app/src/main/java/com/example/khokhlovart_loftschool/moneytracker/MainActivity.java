package com.example.khokhlovart_loftschool.moneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "KhokhlovArt log: ";
    private boolean isHasName = false;
    private boolean isHasCost = false;
    private EditText et_name;
    private EditText et_cost;
    private ImageButton btn_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText)    findViewById(R.id.name);
        et_cost = (EditText)    findViewById(R.id.cost);
        btn_add = (ImageButton) findViewById(R.id.btnAdd);
        btn_add.setEnabled(false);
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isHasName = (charSequence.length() != 0);
                setAddBtnEnabled();
            }
        });
        et_cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isHasCost = (charSequence.length() != 0);
                setAddBtnEnabled();
            }
        });
    }

    private void setAddBtnEnabled()
    {
        btn_add.setEnabled((isHasName && isHasCost));
        btn_add.setBackground((isHasName && isHasCost) ? getDrawable(R.drawable.ic_add_black_24dp) : getDrawable(R.drawable.ic_add_gray_24dp));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "i start!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "i pause!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "i stop!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "i destroy! :(");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "i resume!");
    }
}
