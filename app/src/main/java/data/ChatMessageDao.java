package data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDao {
    @Insert
     void insertMessage(ChatMessage m);

    @Query("Select * from ChatMessage")
     List<ChatMessage> getAllMessages();

    @Delete
    void deleteMessage(ChatMessage m);
}