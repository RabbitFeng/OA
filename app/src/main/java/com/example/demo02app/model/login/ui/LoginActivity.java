package com.example.demo02app.model.login.ui;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.demo02app.R;

public class LoginActivity extends AppCompatActivity implements RegisterFragment.RegisterListener {

    private static final String TAG = LoginActivity.class.getName();

    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();

    private SparseArray<String> titleSparseArray = new SparseArray<>();

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            fragmentSparseArray.put(R.id.rb_login, new LoginFragment());
            titleSparseArray.put(R.id.rb_login, getString(R.string.ui_login));
            fragmentSparseArray.put(R.id.rb_register, new RegisterFragment(this));
            titleSparseArray.put(R.id.rb_register, getString(R.string.ui_register));

            radioGroup = findViewById(R.id.rg);
            // 为radioGroup设定点击事件
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragmentSparseArray.get(checkedId))
                        .commitNow();
//                Objects.requireNonNull(getSupportActionBar()).setTitle(titleSparseArray.get(checkedId));
            });
            // 默认选中login
            radioGroup.check(R.id.rb_login);
        }
    }


    @Override
    public void onSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        // 转到login
        radioGroup.check(R.id.rb_login);
    }
}