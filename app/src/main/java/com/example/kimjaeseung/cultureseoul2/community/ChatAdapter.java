package com.example.kimjaeseung.cultureseoul2.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by kimjaeseung on 2017. 7. 22..
 */

public class ChatAdapter extends ArrayAdapter<ChatData> {
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("a h:mm", Locale.getDefault());

    public ChatAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.community_listitem_chat, null);

            viewHolder = new ViewHolder();
            viewHolder.userPhoto = (ImageView) convertView.findViewById(R.id.community_iv_userphoto);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.community_tv_username);
            viewHolder.userMessage = (TextView) convertView.findViewById(R.id.community_tv_message);
            viewHolder.userTime = (TextView) convertView.findViewById(R.id.community_tv_time);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ChatData chatData = getItem(position);
        viewHolder.userPhoto.setImageResource(chatData.userPhoto);
        viewHolder.userName.setText(chatData.userName);
        viewHolder.userMessage.setText(chatData.message);
        viewHolder.userTime.setText(mSimpleDateFormat.format(chatData.time));

        return convertView;
    }

    private class ViewHolder {
        private ImageView userPhoto;
        private TextView userName;
        private TextView userMessage;
        private TextView userTime;
    }
}

