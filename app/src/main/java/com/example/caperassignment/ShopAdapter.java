package com.example.caperassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder> {


    private List<CaperData> mGoods;
    private Context mContext;
    private int total;


    public ShopAdapter(List<CaperData> mGoods, Context mContext) {
        this.mGoods = mGoods;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ShopAdapter.ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.caper_list, parent, false);
        ShopViewHolder viewHolder = new ShopViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, final int position) {

        holder.bindGood(mGoods.get(position));
        calculateTotal();

    }

    public int calculateTotal() {
        for(int i=0;i<getItemCount();i++){
            total=total+mGoods.get(i).getPrice();

        }
        return total;
    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }



    public class ShopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView viewName;
        TextView viewPrice;

        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            viewName = (TextView) itemView.findViewById(R.id.itemName);
            viewPrice = (TextView) itemView.findViewById(R.id.itemPrice);


            //itemView.setOnClickListener(this);
        }
        public void bindGood(CaperData good) {
            viewName.setText(good.getName());
            viewPrice.setText(good.getPrice().toString());
        }

        @Override
        public void onClick(View v) {
            int currentPosition=getLayoutPosition();


        }
    }
}
