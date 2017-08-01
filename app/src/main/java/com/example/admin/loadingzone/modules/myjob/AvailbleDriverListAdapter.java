package com.example.admin.loadingzone.modules.myjob;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.modules.view.ColorGenerator;
import com.example.admin.loadingzone.retrofit.model.AvailableDriver;
import com.example.admin.loadingzone.retrofit.model.AvailableTruck;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 7/27/2017.
 */

public class AvailbleDriverListAdapter extends RecyclerView.Adapter<AvailbleDriverListAdapter.ViewHolder> {

    private  List<AvailableDriver> listAvailableDriver=new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        @BindView(R.id.textViewDriverName)
        TextView textViewDriverName;
        @NonNull
        @BindView(R.id.textDriverEmail)
        TextView textDriverEmail;
        @NonNull
        @BindView(R.id.textDriverMobile)
        TextView textDriverMobile;
        @BindView(R.id.imageDriverPic)
        ImageView imageDriverPic;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public AvailbleDriverListAdapter(List<AvailableDriver> listAvailableDriver, int rowLayout, Context context) {
        this.listAvailableDriver = listAvailableDriver;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public AvailbleDriverListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AvailbleDriverListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvailbleDriverListAdapter.ViewHolder holder, int position) {


            holder.textViewDriverName.setText(listAvailableDriver.get(position).getDriverName());
            Picasso.with(context)
                    .load(listAvailableDriver.get(position).getDriverPic())
                    .placeholder(R.drawable.img_circle_placeholder)
                    .resize(70, 70)
                    .centerCrop()
                    .transform(new CircleTransformation())
                    .into(holder.imageDriverPic);


        holder.textDriverMobile.setText(listAvailableDriver.get(position).getDriverPhone());
        holder.textDriverEmail.setText(listAvailableDriver.get(position).getDriverEmail());


    }

    @Override
    public int getItemCount() {
        if (listAvailableDriver != null) {
            return listAvailableDriver.size();
        }

        return 0;
    }
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}



