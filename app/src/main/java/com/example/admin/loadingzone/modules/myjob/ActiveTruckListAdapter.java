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
import com.example.admin.loadingzone.modules.driver.DriverListAdapter;
import com.example.admin.loadingzone.modules.view.ColorGenerator;
import com.example.admin.loadingzone.retrofit.model.AvailableTruck;
import com.example.admin.loadingzone.retrofit.model.DriverList;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by admin on 7/3/2017.
 */

public class ActiveTruckListAdapter extends RecyclerView.Adapter<ActiveTruckListAdapter.ViewHolder> {

    private List<AvailableTruck> listTrckAvailble = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        @BindView(R.id.relativeDriverHead)
        RelativeLayout relativeDriverHead;
        @NonNull
        @BindView(R.id.textViewDriverName)
        TextView textViewDriverName;
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
        ImageView imageDriverPic;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ActiveTruckListAdapter(List<AvailableTruck> listTrckAvailble, int rowLayout, Context context) {
        this.listTrckAvailble = listTrckAvailble;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ActiveTruckListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ActiveTruckListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActiveTruckListAdapter.ViewHolder holder, int position) {
        String driver_exist = listTrckAvailble.get(position).getDriver_exists();
        if (driver_exist.equals("false")) {
            holder.textViewDriverName.setText("No Driver");
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor("No Driver");
            holder.relativeDriverHead.setBackgroundColor(color);
        }
      else  {
            holder.textViewDriverName.setText(listTrckAvailble.get(position).getDriver().getDriverName());
            Picasso.with(context)
                    .load(listTrckAvailble.get(position).getDriver().getDriverPic())
                    .placeholder(R.drawable.img_circle_placeholder)
                    .resize(70, 70)
                    .centerCrop()
                    .transform(new CircleTransformation())
                    .into(holder.imageDriverPic);

            ColorGenerator generator = ColorGenerator.MATERIAL;
            String drivername=listTrckAvailble.get(position).getDriver().getDriverName();
            int color = generator.getColor(drivername);
            holder.relativeDriverHead.setBackgroundColor(color);
        }

        holder.textTruckModel.setText(listTrckAvailble.get(position).getVehicle().getManufacturer().getMakerName());
        holder.textTruckName.setText(listTrckAvailble.get(position).getCustomName());
        holder.textTruckType.setText(listTrckAvailble.get(position).getVehicle().getTruckType().getTruckTypeName());
        holder.textTruckDimension.setText(listTrckAvailble.get(position).getVehicle().getDimension());


    }

    @Override
    public int getItemCount() {
        if (listTrckAvailble != null) {
            return listTrckAvailble.size();
        }

        return 0;
    }
}


