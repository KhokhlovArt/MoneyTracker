package com.example.khokhlovart_loftschool.moneytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.Serializable;

public class AddActivity extends AppCompatActivity {
    public static final String PAGE_TYPE = "page_type"; // ID типа фрагмента с которого перешли на форму
    public static final int RCT_ADD_ITEM = 100;         // ID Intent-а для резултата "Добавить элемент"
    public static final String RESULT_ITEM = "item";    // ID возвращаемого результата с вновь созданным обектом ItemsCost
    public static int type = -1;
    private boolean isHasName = false;
    private boolean isHasCost = false;
    private EditText et_name;
    private EditText et_cost;
    private ImageButton btn_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        et_name = (EditText)    findViewById(R.id.name);
        et_cost = (EditText)    findViewById(R.id.cost);
        btn_add = (ImageButton) findViewById(R.id.btnAdd);
        btn_add.setEnabled(false);
        type = getIntent().getIntExtra(PAGE_TYPE,-1);
        et_name.setFocusable(true);
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

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result_intent = new Intent();
                result_intent.putExtra(RESULT_ITEM, (Serializable) new ItemCosts(Integer.parseInt(String.valueOf(et_cost.getText())), et_name.getText().toString()));
                setResult(RESULT_OK, result_intent);
                finish();
            }
        });
    }
    private void setAddBtnEnabled()
    {
        btn_add.setEnabled((isHasName && isHasCost));
        btn_add.setBackground((isHasName && isHasCost) ? getDrawable(R.drawable.ic_add_black_24dp) : getDrawable(R.drawable.ic_add_gray_24dp));
    }

}
