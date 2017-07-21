package com.example.admin.loadingzone.modules.myqutation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.modules.driver.DriverListAdapter;
import com.example.admin.loadingzone.retrofit.model.DriverList;
import com.example.admin.loadingzone.retrofit.model.Item;
import com.example.admin.loadingzone.retrofit.model.JobList;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 6/20/2017.
 */

public class PendingQutationListAdapter extends RecyclerView.Adapter<PendingQutationListAdapter.ViewHolder> {

    List<Item> itemsArrayList = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        @BindView(R.id.textJobTitle)
        TextView textViewJobTitle;
        @NonNull
        @BindView(R.id.textDateSubmited)
        TextView textViewDateSubmited;
        @NonNull
        @BindView(R.id.textQutationAmount)
        TextView textViewQutationAmount;
        @NonNull
        @BindView(R.id.textQutationStatus)
        TextView textViewQutationSttaus;

        @NonNull
        @BindView(R.id.textCustomerName)
        TextView textViewcustName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public PendingQutationListAdapter(List<Item> itemsArrayList, int rowLayout, Context context) {
        this.itemsArrayList = itemsArrayList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public PendingQutationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new PendingQutationListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PendingQutationListAdapter.ViewHolder holder, int position) {

        holder.textViewJobTitle.setText(itemsArrayList.get(position).getJobTitle());
        holder.textViewDateSubmited.setText(itemsArrayList.get(position).getDateSubmitted());
        holder.textViewQutationAmount.setText(itemsArrayList.get(position).getDateAccepted());

        holder.textViewcustName.setText(itemsArrayList.get(position).getCustomer().getName());


     //  holder.textViewcustName.setText(itemsArrayList.get(position).getJobDetails().);
      //  holder.textViewQutationSttaus.setText(itemsArrayList.get(position).getQuotationStatus());


    }

    @Override
    public int getItemCount() {
        if (itemsArrayList != null) {
            return itemsArrayList.size();
        }

        return 0;
    }
}


