package com.example.demo02app.model.login.ui;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.demo02app.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private final SparseArray<Fragment> fragmentSparseArray = new SparseArray<Fragment>() {
        {
            put(LOGIN_KEY, new LoginFragment());
            put(REGISTER_KEY, new RegisterFragment());
        }
    };
    private final int LOGIN_KEY = 0x01;
    private final int REGISTER_KEY = 0x02;


//    private SparseArray<String> titleSparseArray = new SparseArray<>();

//    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragmentSparseArray.get(LOGIN_KEY,new LoginFragment()))
                .commitNow();

//        if (savedInstanceState == null) {
//            fragmentSparseArray.put(R.id.rb_login, new LoginFragment());
//            titleSparseArray.put(R.id.rb_login, getString(R.string.ui_login));
//            fragmentSparseArray.put(R.id.rb_register, new RegisterFragment(this));
//            titleSparseArray.put(R.id.rb_register, getString(R.string.ui_register));
//
//            radioGroup = findViewById(R.id.rg);
//            // 为radioGroup设定点击事件
//            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragmentSparseArray.get(checkedId))
//                        .commitNow();
////                Objects.requireNonNull(getSupportActionBar()).setTitle(titleSparseArray.get(checkedId));
//            });
//            // 默认选中login
//            radioGroup.check(R.id.rb_login);
//        }
    }

    public void toRegister(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container,fragmentSparseArray.get(REGISTER_KEY,new RegisterFragment()))
                .addToBackStack("register")
                .commit();
    }
}