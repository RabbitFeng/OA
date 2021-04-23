package com.example.demo02app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.databinding.ActivityMainBinding;
import com.example.demo02app.model.login.ui.LoginActivity;
import com.example.demo02app.model.meeting.ui.MeetingFragment;
import com.example.demo02app.model.message.ui.MessageFragment;
import com.example.demo02app.model.mine.ui.MineFragment;
import com.example.demo02app.model.notice.ui.NoticeFragment;
import com.example.demo02app.service.JWebSocketClientService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private MainViewModel viewModel;

    private ActivityMainBinding binding;

    private final SparseArray<Fragment> fragmentSparseArray = new SparseArray<Fragment>() {
        {
            put(R.id.rb_message, MessageFragment.newInstance());
            put(R.id.rb_meeting, MeetingFragment.newInstance());
            put(R.id.rb_notice, NoticeFragment.newInstance());
            put(R.id.rb_mine, MineFragment.newInstance());
        }
    };

    private final SparseIntArray titleSparseArray = new SparseIntArray() {
        {
            put(R.id.rb_message, R.string.ui_message);
            put(R.id.rb_meeting, R.string.ui_meeting);
            put(R.id.rb_notice, R.string.ui_notice);
            put(R.id.rb_mine, R.string.ui_mine);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            MainViewModel.Factory factory = new MainViewModel.Factory(getApplication());
            viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

            binding.rg.check(R.id.rb_message);

            binding.tb.setNavigationIcon(R.mipmap.navigation);

            // drawableLayout
            binding.dl.addDrawerListener(new ActionBarDrawerToggle(this, binding.dl, binding.tb, titleSparseArray.get(binding.rg.getCheckedRadioButtonId()), R.string.app_name) {{
                syncState();
            }});

            // logout
            binding.layoutDrawerMenu.btnLogout.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message_logout)
                        .setPositiveButton(R.string.dialog_positive, (dialog, which) -> {
                            // 退出登录
                            logout();
                        }).setNegativeButton(R.string.dialog_cancel, (dialog, which) -> {
                }).show();
            });

            // change Fragment by check RadioButton
            binding.rg.setOnCheckedChangeListener((group, checkedId) -> {
                Log.d(TAG, "checkedChange: " + checkedId);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, fragmentSparseArray.get(checkedId))
                        .commitNow();
//                Objects.requireNonNull(getSupportActionBar()).setTitle(titleSparseArray.get(checkedId));
                binding.tb.setTitle(titleSparseArray.get(checkedId));
            });

            setSupportActionBar(binding.tb);
        }
    }

    private Intent intentWebSocketService;

    @Override
    protected void onStart() {
        super.onStart();
        // 开启WebSocket服务
        intentWebSocketService = new Intent(MainActivity.this, JWebSocketClientService.class);
        intentWebSocketService.putExtra(getString(R.string.param_username), "");
        startService(intentWebSocketService);
    }

    @Override
    protected void onStop() {
        // 关闭WebSocket服务
        stopService(intentWebSocketService);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        viewModel.logout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
