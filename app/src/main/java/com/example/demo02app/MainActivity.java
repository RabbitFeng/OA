package com.example.demo02app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demo02app.databinding.ActivityMainBinding;
import com.example.demo02app.model.addressbook.ui.AddressBookListFragment;
import com.example.demo02app.model.login.data.model.LoggedInUser;
import com.example.demo02app.model.login.ui.LoginActivity;
import com.example.demo02app.model.meeting.ui.MeetingListFragment;
import com.example.demo02app.model.message.ui.MessageListFragment;
import com.example.demo02app.model.mine.ui.MineFragment;
import com.example.demo02app.model.notice.ui.NoticeListFragment;
import com.example.demo02app.service.JWebSocketClientService;

public class MainActivity extends AppCompatActivity implements FragmentCallback {

    private static final String TAG = MainActivity.class.getName();

    private MainViewModel viewModel;

    private ActivityMainBinding binding;

    private final SparseArray<Fragment> fragmentSparseArray = new SparseArray<Fragment>() {
        {
            put(R.id.rb_message, MessageListFragment.newInstance());
            put(R.id.rb_address_book, AddressBookListFragment.newInstance());
            put(R.id.rb_meeting, MeetingListFragment.newInstance());
            put(R.id.rb_notice, NoticeListFragment.newInstance());
            put(R.id.rb_mine, new MineFragment(MainActivity.this));
        }
    };

    private final SparseIntArray titleSparseArray = new SparseIntArray() {
        {
            put(R.id.rb_message, R.string.ui_message);
            put(R.id.rb_address_book, R.string.ui_address_book);
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

            binding.rg.setOnCheckedChangeListener((group, checkedId) -> {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, fragmentSparseArray.get(checkedId))
                        .commitNow();
                binding.tb.setTitle(titleSparseArray.get(checkedId));
            });
            binding.rg.check(R.id.rb_message);

            binding.tb.setNavigationIcon(R.mipmap.navigation);
            setSupportActionBar(binding.tb);
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

            viewModel.getLoggedInUserLiveData().observe(this, new Observer<LoggedInUser>() {
                @Override
                public void onChanged(LoggedInUser user) {
                    if (user == null) {
                        Log.d(TAG, "onChanged: user is null");
                    } else {
                        Log.d(TAG, "onChanged: user is not null " + user.getUserId());
                    }
                }
            });
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

    @Override
    public void onFragmentNeedsFullScreen(boolean isNeed) {
        binding.llBottom.setVisibility(isNeed ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onFragmentAddToBackStack(@NonNull Fragment fragment, @NonNull String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment)
                .addToBackStack(name)
                .commit();
    }

    private void logout() {
        viewModel.logout();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public LoggedInUser getCurrentUser() {
        return viewModel.getLoggedInUserLiveData().getValue();
    }

    public void show(Fragment fragment, String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment)
                .addToBackStack(name)
                .commit();
    }
}
