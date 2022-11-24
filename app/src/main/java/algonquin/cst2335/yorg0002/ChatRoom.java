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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    ChatMessageDao mDAO;
    int position;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        TextView messageText;

        messageText = binding.recycleView.findViewById(R.id.messageText);

        switch(item.getItemId())
        {
            case R.id.item_1:
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                ChatMessage m = messages.get(position);
                builder.setMessage("Do you want to delete the message: " + m)
                        .setTitle("Question:")
                        .setPositiveButton("Yes", (dialog, cl) -> {

                            Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clik ->{
                                        Executor thread = Executors.newSingleThreadExecutor();
                                        thread.execute(() -> {
                                            mDAO.insertMessage(m);
                                        });
                                        chatModel.messages.getValue().add(m);
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();
                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() -> {
                                mDAO.deleteMessage(m);
                            });
                            myAdapter.notifyItemRemoved(position);
                            chatModel.messages.getValue().remove(position);
                        })
                        .setNegativeButton("No", (dialog, cl) -> { })
                        .create()
                        .show();
                break;

            case R.id.item_2:
                Toast.makeText(this,"Version 1.0, created by Ece Selin Yorgancilar", Toast.LENGTH_SHORT).show();
                break;



        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "MessageDatabase").build();
        mDAO = db.cmDAO();

        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll(mDAO.getAllMessages());
                runOnUiThread( () -> binding.recycleView.setAdapter(myAdapter));
            });
        }

        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding sendBinding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
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
                if (messages.get(position).isSentButton()) {
                    return 0;
                } else {
                    return 1;
                }

            }
        };

        chatModel.selectedMessage.observe(this, (newMessageValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack("")
                    .replace(R.id.fragmentLocation, chatFragment)
                    .commit();
        });

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage newMessage = new ChatMessage();
            newMessage.setMessage(binding.textInput.getText().toString());
            newMessage.setTimeSent(currentDateandTime);
            newMessage.setSentButton(true);
            messages.add(newMessage);

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                newMessage.id = (int) mDAO.insertMessage(newMessage);
            });
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd-MMM-yyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage newMessage2 = new ChatMessage();
            newMessage2.setMessage(binding.textInput.getText().toString());
            newMessage2.setTimeSent(currentDateandTime);
            newMessage2.setSentButton(false);
            messages.add(newMessage2);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                newMessage2.setId((int) mDAO.insertMessage(newMessage2));
            });
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

    }



    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                position = getAbsoluteAdapterPosition();
                ChatMessage seleted = messages.get(position);
                chatModel.selectedMessage.postValue(seleted);
//                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
//                builder.setMessage("Do you want to delete the message: " + messageText.getText())
//                    .setTitle("Question:")
//                    .setPositiveButton("Yes", (dialog, cl) -> {
//                        ChatMessage m = messages.get(position);
//                        Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
//                                .setAction("Undo", clik ->{
//                                    Executor thread = Executors.newSingleThreadExecutor();
//                                    thread.execute(() -> {
//                                        mDAO.insertMessage(m);
//                                    });
//                                    chatModel.messages.getValue().add(m);
//                                    myAdapter.notifyItemInserted(position);
//                                })
//                                .show();
//                        Executor thread = Executors.newSingleThreadExecutor();
//                        thread.execute(() -> {
//                            mDAO.deleteMessage(m);
//                        });
//                        myAdapter.notifyItemRemoved(position);
//                        chatModel.messages.getValue().remove(position);
//                    })
//                    .setNegativeButton("No", (dialog, cl) -> { })
//                    .create()
//                    .show();
            });
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);

        }
    }

}