package com.mooz.wifi_list.RegisteredPoint;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooz.wifi_list.MainActivity;
import com.mooz.wifi_list.R;

import java.util.ArrayList;
import java.util.List;

public class RegisteredFragment extends Fragment{

    private Context mContext;
    private View mView;
    private RegisteredGetterSetter item;
    private ArrayList<RegisteredGetterSetter> listItems;
    private RegisteredAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static RegisteredFragment newInstance(){
        RegisteredFragment fragment = new RegisteredFragment();
        Bundle bundle = fragment.getArguments();
        if(bundle == null){
            bundle = new Bundle();
        }
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if(!(context instanceof MainActivity)){
            throw new UnsupportedOperationException(
                    "Listener is not Implementation.");
        }else{
            mContext = context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView = inflater.inflate(R.layout.registered_list_fragment, container, false);
        final SwipeRefreshLayout registered_Swipe = mView.findViewById(R.id.registered_swipe);

        listItems = new ArrayList<>();
        listItems.clear();

        mRecyclerView = mView.findViewById(R.id.registered_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        getWifiRegistered();
        mAdapter = new RegisteredAdapter(mContext, listItems);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        // Refresh listener
        registered_Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                listItems = new ArrayList<>();
                listItems.clear();

                getWifiRegistered();
                mAdapter = new RegisteredAdapter(mContext, listItems);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                if(registered_Swipe.isRefreshing()){
                    registered_Swipe.setRefreshing(false);
                }
            }
        });


        return mView;
    }

    private ArrayList<RegisteredGetterSetter> getWifiRegistered(){

        WifiManager manager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        //Wifi設定の一覧を取得
        List<WifiConfiguration> cfgList = manager.getConfiguredNetworks();

        if(cfgList != null){
            String[] nets = new String[cfgList.size()];
            for(int i = 0; i < cfgList.size(); i++){
                item = new RegisteredGetterSetter();
                nets[i] = String.format(cfgList.get(i).SSID);

                if(nets[i].equals("")){
                    Log.d("Wifi_List", "Registered Point Name is null.");
                }else {
                    String tmpPoint = nets[i].replace("\"", "");
                    item.setRegisteredPoint(tmpPoint);
                }

                listItems.add(item);
            }
        }
        return listItems;
    }

}
