package data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatMessageDao {
    @Insert
    public long insertMessage(ChatMessage m);

    @Query("SELECT * FROM ChatMessage")
    public List<ChatMessage> getAllMessages();

    @Delete
    public void deleteMessage(ChatMessage m);
}
