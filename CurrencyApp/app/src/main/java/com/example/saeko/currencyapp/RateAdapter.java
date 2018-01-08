package com.example.saeko.currencyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saeko.currencyapp.network.model.CurrencyInfo;
import java.util.List;

/**
 * Created by saeko on 2018-01-07.
 */

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

    private List<CurrencyInfo> currencyInfoList;
    private Context context;

    public RateAdapter(List<CurrencyInfo> currencyInfoList, Context context) {
        this.currencyInfoList = currencyInfoList;
        this.context = context;
    }

    class RateViewHolder extends RecyclerView.ViewHolder {
        TextView currencyTitle;
        TextView currencyRate;

        public RateViewHolder(View itemViews) {
            super(itemViews);
            currencyTitle = (TextView) itemView.findViewById(R.id.currency_title);
            currencyRate = (TextView) itemView.findViewById(R.id.currency_rate);
        }

        public void bind(int index) {
            currencyTitle.setText(String.valueOf(currencyInfoList.get(index).getName()));
            currencyRate.setText(String.valueOf(currencyInfoList.get(index).getValue()));
        }

    }

    @Override
    public RateAdapter.RateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_list_rows, parent, false);
        RateViewHolder viewHolder = new RateViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RateAdapter.RateViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (currencyInfoList.isEmpty()) {
            return 0;
        } else {
            return currencyInfoList.size();
        }
    }
}
