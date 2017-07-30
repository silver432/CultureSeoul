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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kimjaeseung on 2017. 7. 23..
 */

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>{
    private Context mContext;
    private List<ChatRoomData> chatRoomDataList=new ArrayList<>();
    private ChatRoomAdapterOnClickHandler chatRoomAdapterOnClickHandler;
    private ChatRoomData chatRoomData;
    private int mPosition;

    public interface ChatRoomAdapterOnClickHandler{
        void onClick(ChatRoomData chatRoomData);
    }

    public ChatRoomAdapter(Context context,ChatRoomAdapterOnClickHandler handler){
        mContext=context;
        chatRoomAdapterOnClickHandler=handler;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.community_listitem_chatroom;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(chatRoomDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatRoomDataList.size();
    }

    public void setItemList(List<ChatRoomData> itemList){
        chatRoomDataList.clear();
        chatRoomDataList.addAll(itemList);
    }
    public void addItem(ChatRoomData chatRoomData){
        chatRoomDataList.add(chatRoomData);
    }
    public void removeItem(int position){
        chatRoomDataList.remove(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mPerformanceImage;
        private TextView mRoomName;
        private TextView mRoomLocation;
        private TextView mRoomTime;
        private TextView mRoomState;
        private TextView mRoomPeople;

        public ViewHolder(View itemView) {
            super(itemView);

            mPerformanceImage=(ImageView)itemView.findViewById(R.id.community_iv_chatroom);
            mRoomName=(TextView)itemView.findViewById(R.id.community_tv_roomname);
            mRoomLocation=(TextView)itemView.findViewById(R.id.community_tv_roomlocation);
            mRoomTime=(TextView)itemView.findViewById(R.id.community_tv_roomtime);
            mRoomState=(TextView)itemView.findViewById(R.id.community_tv_roomstate);
            mRoomPeople=(TextView)itemView.findViewById(R.id.community_tv_roompeople);

            itemView.setOnClickListener(this);
        }
        public void bind(ChatRoomData chatRoomData){
            mPerformanceImage.setImageResource(chatRoomData.getPerformanceImage());
            mRoomName.setText(chatRoomData.getRoomName());
            mRoomPeople.setText(chatRoomData.getRoomPeople());
            mRoomState.setText(chatRoomData.getRoomState());
            mRoomTime.setText(chatRoomData.getRoomTime());
            mRoomLocation.setText(chatRoomData.getRoomLocation());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            chatRoomData = chatRoomDataList.get(position);
            chatRoomAdapterOnClickHandler.onClick(chatRoomData);
        }
    }

//    public ChatRoomAdapter(Context context, int resource) {
//        super(context, resource);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.community_listitem_chatroom, null);
//
//            viewHolder = new ViewHolder();
//            viewHolder.mPerformanceImage=(ImageView)convertView.findViewById(R.id.community_iv_chatroom);
//            viewHolder.mRoomName=(TextView)convertView.findViewById(R.id.community_tv_roomname);
//            viewHolder.mRoomLocation=(TextView)convertView.findViewById(R.id.community_tv_roomlocation);
//            viewHolder.mRoomTime=(TextView)convertView.findViewById(R.id.community_tv_roomtime);
//            viewHolder.mRoomState=(TextView)convertView.findViewById(R.id.community_tv_roomstate);
//            viewHolder.mRoomPeople=(TextView)convertView.findViewById(R.id.community_tv_roompeople);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        ChatRoomData chatRoomData = getItem(position);
//        viewHolder.mPerformanceImage.setImageResource(chatRoomData.getPerformanceImage());
//        viewHolder.mRoomName.setText(chatRoomData.getRoomName());
//        viewHolder.mRoomLocation.setText(chatRoomData.getRoomLocation());
//        viewHolder.mRoomTime.setText(chatRoomData.getRoomTime());
//        viewHolder.mRoomState.setText(chatRoomData.getRoomState());
//        viewHolder.mRoomPeople.setText(chatRoomData.getRoomPeople());
//
//        return convertView;
//    }
//
//    private class ViewHolder {
//        private ImageView mPerformanceImage;
//        private TextView mRoomName;
//        private TextView mRoomLocation;
//        private TextView mRoomTime;
//        private TextView mRoomState;
//        private TextView mRoomPeople;
//    }

}
