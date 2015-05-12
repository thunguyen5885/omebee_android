package com.omebee.android.layers.ui.components.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omebee.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThuNguyen on 5/11/2015.
 */
public class SimpleSpinnerAdapter<T extends Object> extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<T> mObjectList;
    public SimpleSpinnerAdapter(Context context, List<T> objectList){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mObjectList = objectList;
    }
    public SimpleSpinnerAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mObjectList = new ArrayList<T>();
    }
    @Override
    public int getCount() {
        return mObjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return mObjectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView =  mLayoutInflater.inflate(R.layout.ctr_simple_spinner_item, null);
            viewHolder.mTvContent = (TextView)convertView.findViewById(R.id.tvSpinnerItem);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        T item = (T)getItem(position);
        if(item instanceof CharSequence) {
            viewHolder.mTvContent.setText((CharSequence)item);
        }else{
            viewHolder.mTvContent.setText(item.toString());
        }
        return convertView;
    }

    private static class ViewHolder{
        private TextView mTvContent;
    }
}
