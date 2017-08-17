package com.example.admin.loadingzone.modules.driver;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.modules.truck.TrckListAdapter;
import com.example.admin.loadingzone.retrofit.model.DriverList;
import com.example.admin.loadingzone.retrofit.model.VehicleList;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 5/22/2017.
 */

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.ViewHolder> {

    private List<DriverList> driverLists = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewDriverName)
        TextView textViewDriverName;
        @BindView(R.id.textViewDriverEmail)
        TextView textViewDriverEmail;
        @BindView(R.id.textViewDriverMobile)
        TextView textViewDriverMobile;

        @BindView(R.id.textJoinedDate)
        TextView textViewJoinedDate;

        @BindView(R.id.textCurrentlyAssignedTruck)
        TextView textViewCurrentlyAssignedTruck;


        @BindView(R.id.imageDriverPic)
        ImageView imageDriverPic;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    public DriverListAdapter(List<DriverList> driverLists, int rowLayout, Context context) {
        this. driverLists = driverLists;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public DriverListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new DriverListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DriverListAdapter.ViewHolder holder, int position) {

        holder.textViewDriverName.setText(driverLists.get(position).getDriverName());
        holder.textViewDriverEmail.setText(driverLists.get(position).getDriverEmail());
        holder.textViewDriverMobile.setText(driverLists.get(position).getDriverPhone());

        holder.textViewJoinedDate.setText(driverLists.get(position).getCreatedDate());
        holder.textViewCurrentlyAssignedTruck.setText(driverLists.get(position).getDriverTruck().getCustomName());


        Picasso.with(context)
                .load(driverLists.get(position).getDriverPic())
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(70, 70)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(holder.imageDriverPic);

    }

    @Override
    public int getItemCount() {
        if (driverLists != null) {
            return driverLists.size();
        }

        return 0;
    }
}

