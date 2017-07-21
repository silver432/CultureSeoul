package com.example.kimjaeseung.cultureseoul2.performance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kimjaeseung.cultureseoul2.R;
import com.example.kimjaeseung.cultureseoul2.domain.CultureEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heo04 on 2017-07-12.
 */

public class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.ViewHolder>
{
    private static final String TAG = PerformanceAdapter.class.getSimpleName();

    private int mNumberItems;

    private List<CultureEvent> cultureEventList = new ArrayList<>();

    public PerformanceAdapter(int numberOfItems)
    {
        mNumberItems = numberOfItems;
    }

    /* RecyclerView를 ViewHolder객체로 인스턴스화 */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.perform_list_item;
        LayoutInflater inflater = LayoutInflater.from(context); // itemview를 inflate
        boolean shouldAttachToParentImmediately = false;

        /* 레이아웃을 뷰그룹과 뷰(자바 코드)의 집합으로 바꾸어줌 */
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    /* RecyclerView가 데이터 소스에서 가져온 정보를 뷰에 넣을 때 사용 */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Log.d(TAG, "#" + position);
        holder.bind(cultureEventList.get(position));
    }

    /* 데이터 소스의 아이템 개수를 반환 */
    @Override
    public int getItemCount()
    {
        return cultureEventList.size();
    }

    public void setItemList(List<CultureEvent> list)
    {
        cultureEventList.clear();
        cultureEventList.addAll(list);
    }

    public void notifyAdapter()
    {
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView listItemNumberView;

        public ViewHolder(View itemView)
        {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        public void bind(CultureEvent cultureEvent)
        {
            listItemNumberView.setText(cultureEvent.getTitle());
        }
    }
}
