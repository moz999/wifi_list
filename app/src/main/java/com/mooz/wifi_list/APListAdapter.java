package com.mooz.wifi_list;

import android.content.ClipData;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class APListAdapter extends RecyclerView.Adapter<APListAdapter.ViewHolder>{

    private ArrayList<ItemGetterSetter> listItems;
    private ItemGetterSetter item;
    private Context mContext;
    private LayoutInflater mInflater;

    public APListAdapter(Context context, ArrayList<ItemGetterSetter> listItems){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.listItems = listItems;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("Wifi_List", "APListFragment : onCreateViewHolder() is called !!");

        return new ViewHolder(mInflater.inflate(R.layout.ap_list_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("Wifi_List", "APListFragment onBindViewHolder() is called !!");

        item = listItems.get(i);
        String apName = item.getTxtWifiPoint();
        viewHolder.txtWifiTitle.setText(apName);
    }


    @Override
    public int getItemCount() {
        Log.d("Wifi_List", "APListFragment : getItemCount() is called !!");
        if(listItems != null){
            return listItems.size();
        }else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtWifiTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtWifiTitle = itemView.findViewById(R.id.apitem);
        }
    }
}
