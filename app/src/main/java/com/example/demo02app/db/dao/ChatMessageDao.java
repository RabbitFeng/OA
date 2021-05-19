package com.example.demo02app.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.demo02app.db.data.ChatListDO;
import com.example.demo02app.db.data.ChatMessageDO;
import com.example.demo02app.model.chat.data.ChatMessageItem;
import com.example.demo02app.model.message.data.MessageItem;

import java.util.List;

@Dao
public interface ChatMessageDao {

    @Query("select * " +
            "from chat_list " +
            "where cl_user_host = :userHost")
    LiveData<List<ChatListDO>> queryAllChatList(String userHost);

    @Query("select * " +
            "from chat_message " +
            "where cm_user_host=:userHost and cm_user_other = :userOther")
    LiveData<List<ChatMessageDO>> queryAllChatMessage(String userHost, String userOther);

    @Query("select cl_user_host,cl_user_other,cl_is_send,cl_content,cl_latest_time,ad_remark_name " +
            "from chat_list cl " +
            "inner join address_book ab " +
            "on cl.cl_user_other = ab.ad_user_other " +
            "where cl_user_host = :userHost")

    LiveData<List<MessageItem>> queryMessageItem(String userHost);

    @Query("select * " +
            "from chat_message " +
            "where cm_user_host=:userHost and cm_user_other = :userOther")
    LiveData<List<ChatMessageItem>> queryChatMessage(String userHost,String userOther);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChatList(List<ChatListDO> chatLists);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChatList(ChatListDO chatLists);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertChatMessage(List<ChatMessageDO> chatMessages);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertChatMessage(ChatMessageDO chatMessages);

    @Delete
    int deleteChatList(ChatListDO chatList);

    @Delete
    int deleteChatList(List<ChatListDO> chatLists);

    @Delete
    int deleteChatMessage(ChatMessageDO chatMessage);

    @Delete
    int deleteChatMessage(List<ChatMessageDO> chatMessage);

}
