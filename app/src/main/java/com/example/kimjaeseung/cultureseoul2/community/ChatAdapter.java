package com.example.kimjaeseung.cultureseoul2.community;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("a h:mm", Locale.getDefault());
    private Context mContext;
    private List<ChatData> chatDataList = new ArrayList<>();
    private ChatAdapterOnClickHandler chatAdapterOnClickHandler;
    private ChatData chatData;
    private int mPosition;

    public interface ChatAdapterOnClickHandler {
        void onClick(ChatData chatData);
    }

    public ChatAdapter(Context context, ChatAdapterOnClickHandler handler) {
        mContext = context;
        chatAdapterOnClickHandler = handler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.community_listitem_chat;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(chatDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatDataList.size();
    }

    public void setItemList(List<ChatData> itemList) {
        chatDataList.clear();
        chatDataList.addAll(itemList);
    }

    public void addItem(ChatData chatData) {
        chatDataList.add(chatData);
    }

    public void removeItem(int position) {
        chatDataList.remove(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView userPhoto;
        private TextView userName;
        private TextView userMessage;
        private TextView userTime;

        public ViewHolder(View itemView) {
            super(itemView);

            userPhoto = (ImageView) itemView.findViewById(R.id.community_iv_userphoto);
            userName = (TextView) itemView.findViewById(R.id.community_tv_username);
            userMessage = (TextView) itemView.findViewById(R.id.community_tv_message);
            userTime = (TextView) itemView.findViewById(R.id.community_tv_time);

            itemView.setOnClickListener(this);
        }

        public void bind(ChatData chatData) {
            userPhoto.setImageResource(chatData.userPhoto);
            userName.setText(chatData.userName);
            userMessage.setText(chatData.message);
            userTime.setText(mSimpleDateFormat.format(chatData.time));
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            chatData = chatDataList.get(position);
            chatAdapterOnClickHandler.onClick(chatData);
        }
    }
}

