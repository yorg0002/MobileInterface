package algonquin.cst2335.yorg0002;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.yorg0002.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.yorg0002.databinding.ActivityReceiveMessageBinding;
import algonquin.cst2335.yorg0002.databinding.SentMessageBinding;
import data.ChatMessage;
import data.ChatMessageDao;
import data.ChatRoomViewModel;
import data.MessageDatabase;
import data.MessageDetailsFragment;

public class ChatRoom extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    ArrayList<ChatMessage> messages;
    ChatRoomViewModel chatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding sendBinding = SentMessageBinding.inflate(getLayoutInflater(),parent, false);
                    return new MyRowHolder(sendBinding.getRoot());
                } else {
                    ActivityReceiveMessageBinding receiveBinding = ActivityReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(receiveBinding.getRoot());
                }

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText("");
                holder.timeText.setText("");

                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                if (messages.get(position).isSentButton() == true) {
                    return 0;
                } else {
                    return 1;
                }

            }
        });

        chatModel.selectedMessage.observe(this, (newValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newValue);

            getSupportFragmentManager()
                    .beginTransaction()

                    .replace(R.id.fragmentLocation, chatFragment)
                    .commit();
        });
        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyy hh-mm-ss a");
            String currentDateandTIme = sdf.format(new Date());
            messages.add(new ChatMessage(binding.textInput.getText().toString(), currentDateandTIme, true));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyy hh-mm-ss a");
            String currentDateandTIme = sdf.format(new Date());
            messages.add(new ChatMessage(binding.textInput.getText().toString(), currentDateandTIme, false));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        ChatMessageDao mDAO = db.cmDAO();

        if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database
                binding.recycleView.setAdapter( myAdapter ); //You can then load the RecyclerView
            });
        }


        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

    }
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
       // MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        //ChatMessageDao mDAO = db.cmDAO();

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(click ->{
                int position =  getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);
                /*
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete the message: " +messageText.getText());
                builder.setTitle("Question: ");
                builder.setNegativeButton("No", ((dialog, cl) -> {}));
                builder.setPositiveButton("Yes", ((dialog, cl) -> {
                    ChatMessage removedMessage = messages.get(position);
                   // mDAO.deleteMessage(m);
                    messages.remove(position);
                    myAdapter.notifyItemRemoved(position);

                    Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk -> {

                                messages.add(position, removedMessage);
                                myAdapter.notifyItemInserted(position);
                            })
                            .show();

                }))
                        .create().show();*/

            });
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}