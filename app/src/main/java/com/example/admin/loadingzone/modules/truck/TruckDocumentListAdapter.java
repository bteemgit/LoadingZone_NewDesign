package com.example.admin.loadingzone.modules.truck;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 9/5/2017.
 */

public class TruckDocumentListAdapter extends RecyclerView.Adapter<TruckDocumentListAdapter.ImageViewHolder> {


    private List<VehicleDoc> vehicleDocArrayList = new ArrayList<>();
    public Context context;
    public TruckDocumentListAdapter(List<VehicleDoc> vehicleDocArrayList, Context context) {
        this.vehicleDocArrayList = vehicleDocArrayList;
        this.context = context;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Fresco.initialize(context);
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
        Uri imageUri = Uri.parse(vehicleDoc.getDocumentFile());
        holder.draweeView.setImageURI(imageUri);
        holder.textViewDocTitle.setText(vehicleDoc.getDocumentTitle());

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

       // private ImageView imageViewTruckDoc;
        TextView textViewDocTitle;
        SimpleDraweeView draweeView;
        public ImageViewHolder(View itemView) {
            super(itemView);

            //imageViewTruckDoc = (ImageView) itemView.findViewById(R.id.iv_truck_doc);
            textViewDocTitle=(TextView)itemView.findViewById(R.id.text_truckDoc_title);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdvImage);

        }
    }

}
