package com.example.chatbot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageEditText;
    private Button sendButton;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Get the username from the intent
        String username = getIntent().getStringExtra("USERNAME");

        // Set the activity title and enable back button if action bar is not null
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Chat with Llama");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Display welcome message with username
        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Welcome, " + username + "!");

        // Initialize views
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // Initialize message list and adapter
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList, username);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);

        // Set click listener for send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }


    // Method to send message
    private void sendMessage() {
        String message = messageEditText.getText().toString().trim();
        if (!message.isEmpty()) {
            // Add user message to RecyclerView
            addMessage(new Message(message, true));

            // TODO: Call chatbot service or logic to generate llama response
            String llamaResponse = generateLlamaResponse(message);

            // Add llama response to RecyclerView after a delay (simulating response time)
            addLlamaResponse(llamaResponse);

            // Clear message input field
            messageEditText.setText("");
        }
    }

    // Method to add user or llama message to RecyclerView
    private void addMessage(Message message) {
        messageList.add(message);
        chatAdapter.notifyItemInserted(messageList.size() - 1);
        chatRecyclerView.scrollToPosition(messageList.size() - 1);
    }

    // Method to generate llama response
    private String generateLlamaResponse(String message) {
        // Dummy logic: Echo the user's message
        return "Llama says: " + message;
    }

    // Method to add llama response to RecyclerView after a delay
    private void addLlamaResponse(final String response) {
        // Simulate response delay using a Handler
        chatRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                addMessage(new Message(response, false));
            }
        }, 1000); // Delay in milliseconds
    }
}
