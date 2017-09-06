package com.example.admin.loadingzone.modules.truck;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.retrofit.model.VehicleDoc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 9/5/2017.
 */

public class TruckDocumentListAdapter extends RecyclerView.Adapter<TruckDocumentListAdapter.ImageViewHolder> {


    private List<VehicleDoc> vehicleDocArrayList = new ArrayList<>();
    private Context context;
    public TruckDocumentListAdapter(List<VehicleDoc> vehicleDocArrayList, Context context) {
        this.vehicleDocArrayList = vehicleDocArrayList;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_truck_document, parent, false));
    }

    @Override
    public int getItemCount() {
        return vehicleDocArrayList.size();
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final VehicleDoc vehicleDoc = vehicleDocArrayList.get(position);

        Picasso.with(holder.itemView.getContext())
                .load(vehicleDoc.getDocumentFile())
                .into(holder.imageViewTruckDoc);
        holder.textViewDocTitle.setText(vehicleDoc.getDocumentTitle());

    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewTruckDoc;
        TextView textViewDocTitle;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageViewTruckDoc = (ImageView) itemView.findViewById(R.id.iv_truck_doc);
            textViewDocTitle=(TextView)itemView.findViewById(R.id.text_truckDoc_title);
        }
    }

}
