package com.example.demo02app.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.demo02app.MyExecutors;
import com.example.demo02app.db.dao.AddressBookDao;
import com.example.demo02app.db.dao.ChatMessageDao;
import com.example.demo02app.db.dao.MeetingDAO;
import com.example.demo02app.db.dao.NoticeDAO;
import com.example.demo02app.db.data.AddressBookDO;
import com.example.demo02app.db.data.ChatListDO;
import com.example.demo02app.db.data.ChatMessageDO;
import com.example.demo02app.db.data.MeetingDO;
import com.example.demo02app.db.data.NoticeDO;

@Database(entities = {ChatListDO.class, ChatMessageDO.class, AddressBookDO.class, MeetingDO.class, NoticeDO.class},
        exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private volatile static AppDatabase sInstance;

    private final static MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static final String DATABASE_NAME = "my_app_db";

    public abstract AddressBookDao addressBookDao();

    public abstract ChatMessageDao chatMessageDao();

    public abstract MeetingDAO meetingDAO();

    public abstract NoticeDAO noticeDAO();

    public static AppDatabase getInstance(final Context context, final MyExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext, final MyExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AppDatabase database = getInstance(appContext, executors);
                        database.setDatabaseCreated();
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }
}
