package com.example.caperassignment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ShopFragment extends Fragment {

    FloatingActionButton btnScan;
    RecyclerView cartView;
    TextView totalPrice;
    private Class fragmentClass;
    private ShopAdapter mAdapter;
    private CaperData bundle;
    private Fragment fragment = null;
    private ArrayList<String> codes = new ArrayList<>();
    private List<CaperData> items = new ArrayList<>();
    private int total = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.shop_fragment, container, false);
        btnScan = view.findViewById(R.id.btnScan);
        cartView = view.findViewById(R.id.cartView);
        totalPrice = view.findViewById(R.id.textTotal);

        Bundle getCode=this.getArguments();
        Log.i(TAG, "Code size : "+ codes.size());
        if(getCode!=null){
            if(codes.size()==0){
                codes=getCode.getStringArrayList("qrUrl");
            }
            else {
                codes.addAll(getCode.getStringArrayList("qrUrl"));

            }

        }
        if(codes.size()>0){
            for(String code : codes){
                CaperApi client=  CaperApiClient.getItem();
                Call<CaperData> call=client.getItem(code);
                call.enqueue(new Callback<CaperData>(){

                    @Override
                    public void onResponse(Call<CaperData> call, Response<CaperData> response) {
                        //showSuccessfulMessage();
                        if(response.isSuccessful()&&response.body()!=null){

                            bundle=new CaperData();
                            bundle.setId(response.body().getId());
                            bundle.setPrice(response.body().getPrice());
                            bundle.setName(response.body().getName());
                            bundle.setqrUrl(response.body().getqrUrl());
                            items.add(bundle);
                            mAdapter=new ShopAdapter(items,getContext());
                            cartView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
                            cartView.setLayoutManager(layoutManager);
                            cartView.setHasFixedSize(true);

                        }
                        else {
                        }
                    }

                    @Override
                    public void onFailure(Call<CaperData> call, Throwable t) {

                    }
                });

            }

            showItems();

        }


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentClass=ScanningFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frament1, fragment).commit();

            }
        });


        return view;
    }

    private void showItems() {
        Log.i(TAG, "showSuccessfulMessage: "+codes.size());
        cartView.setVisibility(View.VISIBLE);
    }
}


