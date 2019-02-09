package com.mooz.wifi_list.RegisteredPoint;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooz.wifi_list.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RegisteredAdapter extends RecyclerView.Adapter<RegisteredAdapter.RegistViewHolder>{

    private Context mContext;
    private ArrayList<RegisteredGetterSetter> listItems;
    private RegisteredGetterSetter item;
    private LayoutInflater mInflater;

    public RegisteredAdapter(Context context, ArrayList<RegisteredGetterSetter> listItems){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public RegistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RegistViewHolder(mInflater.inflate(R.layout.registered_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RegistViewHolder viewHolder, int i) {
        item = listItems.get(i);

        String pointNm = item.getRegisteredPoint();
        viewHolder.registeredPoint.setText(pointNm);
    }

    @Override
    public int getItemCount() {
        if(listItems != null){
            return listItems.size();
        }else{
            return 0;
        }
    }

    public class RegistViewHolder extends RecyclerView.ViewHolder{
        public TextView registeredPoint;

        public RegistViewHolder(@NonNull View itemView) {
            super(itemView);

            registeredPoint = itemView.findViewById(R.id.registPoint);
        }
    }
}
