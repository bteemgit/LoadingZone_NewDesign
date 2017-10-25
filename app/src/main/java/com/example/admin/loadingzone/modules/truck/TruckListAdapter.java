package com.example.admin.loadingzone.modules.truck;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.retrofit.model.VehicleList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 5/19/2017.
 */

public class TruckListAdapter extends RecyclerView.Adapter<TruckListAdapter.ViewHolder> {

    private List<VehicleList> vehicleListList = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.textViewTrckName)
        TextView textViewTrckName;
        @BindView(R.id.textAvgSpeed)
        TextView textAvgSpeed;
        @BindView(R.id.textManufacture)
        TextView textManufacture;
        @BindView(R.id.textTruckType)
        TextView textTruckType;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);

        }

    }

    public TruckListAdapter(List<VehicleList> vehicleListList, int rowLayout, Context context) {
        this.vehicleListList = vehicleListList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public TruckListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TruckListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewTrckName.setText(vehicleListList.get(position).getCustomName()+" - "+vehicleListList.get(position).getRegistration_number());
        holder.textAvgSpeed.setText(vehicleListList.get(position).getAvgRunningSpeed()+"Km/hr");
        holder.textManufacture.setText(vehicleListList.get(position).getVehicle().getManufacturer().getMakerName());
        holder.textTruckType.setText(vehicleListList.get(position).getVehicle().getTruckType().getTruckTypeName());
    }

    @Override
    public int getItemCount() {
        if (vehicleListList != null) {
            return vehicleListList.size();
        }

        return 0;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }
}
