package data;
import androidx.lifecycle.MutableLiveData;


import java.util.ArrayList;

public class ChatRoomViewModel extends MainViewModel {
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();
    public static MutableLiveData<ChatMessage> selectedMessage = new MutableLiveData<>();
}
