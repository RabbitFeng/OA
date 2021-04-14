package com.example.demo02app;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.meeting.ui.MeetingFragment;
import com.example.demo02app.message.ui.MessageFragment;
import com.example.demo02app.mine.ui.MineFragment;
import com.example.demo02app.notice.ui.NoticeFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private MainViewModel viewModel;

    private SparseArray<Fragment> fragmentSparseArray = new SparseArray<>();

    private SparseArray<Integer> titleSparseArray = new SparseArray<>();

    private RadioGroup rg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainViewModel.Factory factory = new MainViewModel.Factory();
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);
        if (savedInstanceState == null) {
            fragmentSparseArray.put(R.id.rb_message, MessageFragment.newInstance());
            fragmentSparseArray.put(R.id.rb_meeting, MeetingFragment.newInstance());
            fragmentSparseArray.put(R.id.rb_notice, NoticeFragment.newInstance());
            fragmentSparseArray.put(R.id.rb_mine, MineFragment.newInstance());
            titleSparseArray.put(R.id.rb_message, R.string.ui_message);
            titleSparseArray.put(R.id.rb_meeting, R.string.ui_meeting);
            titleSparseArray.put(R.id.rb_notice, R.string.ui_notice);
            titleSparseArray.put(R.id.rb_mine, R.string.ui_mine);

            rg = findViewById(R.id.rg);

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fl_container, fragmentSparseArray.get(checkedId))
                            .commitNow();
                    Objects.requireNonNull(getSupportActionBar()).setTitle(titleSparseArray.get(checkedId));
                }
            });

            rg.check(R.id.rb_message);
        }
    }
}