package com.example.sheba_mental_health_project.model;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatAdapter extends FirestoreRecyclerAdapter<ChatMessage, ChatAdapter.ChatViewHolder> {

    private final String mUserEmail;

    private final int TYPE_MESSAGE_SENT = 1;
    private final int TYPE_MESSAGE_RECEIVED = 2;

    private final String TAG = "ChatAdapter";


    public ChatAdapter(@NonNull FirestoreRecyclerOptions<ChatMessage> options,
                       final String userEmail) {
        super(options);

        this.mUserEmail = userEmail;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        private final TextView contentTv;
        private final TextView timeTv;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            this.contentTv = itemView.findViewById(R.id.message_body_tv);
            this.timeTv = itemView.findViewById(R.id.message_time_tv);
        }

        public void bind(final ChatMessage message) {
            this.contentTv.setText(message.getContent());
            this.timeTv.setText(dateToFormatDate(message.getTime()));
        }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
        }

        return new ChatViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder,
                                    int position,@NonNull ChatMessage message) {
        holder.bind(message);
    }

    @Override
    public int getItemViewType(int position) {
        final ChatMessage message = getItem(position);
        int itemViewType;

        if (message.getRecipientEmail().equals(mUserEmail)) {
            itemViewType = TYPE_MESSAGE_RECEIVED;
        } else {
            itemViewType = TYPE_MESSAGE_SENT;
        }

        return itemViewType;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @SuppressLint("SimpleDateFormat")
    private String dateToFormatDate(final Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm | dd/MM");
        return simpleDateFormat.format(date);
    }
}
