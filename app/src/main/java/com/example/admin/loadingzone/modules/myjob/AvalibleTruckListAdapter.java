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

public class AvalibleTruckListAdapter extends RecyclerView.Adapter<AvalibleTruckListAdapter.ViewHolder> {

    private List<AvailableTruck> listTrckAvailble = new ArrayList<>();
    private int rowLayout;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        @BindView(R.id.textTruckName)
        TextView textTruckName;
        @NonNull
        @BindView(R.id.textTruckModel)
        TextView textTruckModel;
        @NonNull
        @BindView(R.id.textTruckType)
        TextView textTruckType;
        @NonNull
        @BindView(R.id.textTruckDimension)
        TextView textTruckDimension;
        @NonNull
        @BindView(R.id.imageDriverPic)
        ImageView ivDriverPic;
        @NonNull
        @BindView(R.id.textDrivername)
        TextView textDrivername;
        @NonNull
        @BindView(R.id.textDriverEmail)
        TextView textDriverEmail;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public AvalibleTruckListAdapter(List<AvailableTruck> listTrckAvailble, int rowLayout, Context context) {
        this.listTrckAvailble = listTrckAvailble;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public AvalibleTruckListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AvalibleTruckListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AvalibleTruckListAdapter.ViewHolder holder, int position) {
        holder.textTruckModel.setText(listTrckAvailble.get(position).getVehicle().getManufacturer().getMakerName());
        holder.textTruckName.setText(listTrckAvailble.get(position).getCustomName());
        holder.textTruckType.setText(listTrckAvailble.get(position).getVehicle().getTruckType().getTruckTypeName());
        holder.textTruckDimension.setText(listTrckAvailble.get(position).getVehicle().getDimension());

        String isDriverExist = listTrckAvailble.get(position).getDriver_exists();
        if (!isDriverExist.equals("false")) {
            holder.textDrivername.setText(listTrckAvailble.get(position).getDriver().getDriverName());
            Picasso.with(context)
                    .load(listTrckAvailble.get(position).getDriver().getDriverPic())
                    .placeholder(R.drawable.img_circle_placeholder)
                    .resize(70, 70)
                    .centerCrop()
                    .transform(new CircleTransformation())
                    .into(holder.ivDriverPic);
            holder.textDriverEmail.setText(listTrckAvailble.get(position).getDriver().getDriverPhone());

        } else {
            holder.textDrivername.setText("No Driver ");
            holder.textDriverEmail.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (listTrckAvailble != null) {
            return listTrckAvailble.size();
        }

        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}



