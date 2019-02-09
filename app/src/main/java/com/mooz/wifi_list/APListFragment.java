package com.mooz.wifi_list;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class APListFragment extends Fragment{

    private Context mContext;
    private View mView;
    private ArrayList<ItemGetterSetter> listItems;

    private RecyclerView mRecyclerView;
    private APListAdapter mAdapter;

    public static APListFragment newInstance(){
        APListFragment fragment = new APListFragment();
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

        Log.d("Wifi_List", "APListFragment : onAttach is called !!");

        if(!(context instanceof MainActivity)){
            throw new UnsupportedOperationException(
                    "Listener is not Implementation.");
        }else{
            mContext = context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.d("Wifi_List", "APListFragment : onCreate is called !!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        Log.d("Wifi_List", "APListFragment : onCreateView is called !!");

        mView = inflater.inflate(R.layout.ap_list_fragment, container, false);
        final SwipeRefreshLayout mSwipeView = mView.findViewById(R.id.swipeWidget);

        listItems = new ArrayList<>();
        listItems.clear();

        mRecyclerView = mView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // Get to access point.
        getWifiList();
        mAdapter = new APListAdapter(mContext, listItems);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        // Reload wifi list then you refresh view
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                listItems = new ArrayList<>();
                listItems.clear();

                getWifiList();
                mAdapter = new APListAdapter(mContext, listItems);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

                if(mSwipeView.isRefreshing()){
                    mSwipeView.setRefreshing(false);
                }
            }
        });


        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("Wifi_List", "APListFragment : onStart is called !!");
    }


    /**
     * Get to access point.
     * @return
     */
    private List<ItemGetterSetter> getWifiList(){

        List<ScanResult> results;

        WifiManager manager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        if(manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED){
            results = manager.getScanResults();
            String[] strPoints = new String[results.size()];

            for(int i = 0; i < results.size(); i++){
                ItemGetterSetter item = new ItemGetterSetter();
                if(results.get(i).SSID.equals("")){
                    Log.d("Wifi_List", "Access Point Name is null.");

                }else{
                    strPoints[i] = results.get(i).SSID;
                    item.setTxtWifiPoint(strPoints[i]);

                }

                listItems.add(item);
            }
        }

        return listItems;
    }

}
