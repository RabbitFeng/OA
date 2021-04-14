package com.example.demo02app.login.ui;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.demo02app.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();

    private SparseArray<String> titleSparseArray = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            fragmentSparseArray.put(R.id.rb_login, new LoginFragment());
            titleSparseArray.put(R.id.rb_login, getString(R.string.ui_login));
            fragmentSparseArray.put(R.id.rb_register, new RegisterFragment());
            titleSparseArray.put(R.id.rb_register, getString(R.string.ui_register));

            RadioGroup radioGroup = findViewById(R.id.rg);
            // 为radioGroup设定点击事件
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragmentSparseArray.get(checkedId))
                        .commitNow();
                Objects.requireNonNull(getSupportActionBar()).setTitle(titleSparseArray.get(checkedId));
            });
            // 默认选中login
            radioGroup.check(R.id.rb_login);
        }

    }
}