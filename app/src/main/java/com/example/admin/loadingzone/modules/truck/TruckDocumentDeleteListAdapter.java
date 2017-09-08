package com.example.admin.loadingzone.modules.truck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.global.AppController;
import com.example.admin.loadingzone.global.GloablMethods;
import com.example.admin.loadingzone.modules.home.HomeActivity;
import com.example.admin.loadingzone.retrofit.ApiClient;
import com.example.admin.loadingzone.retrofit.ApiInterface;
import com.example.admin.loadingzone.retrofit.model.Meta;
import com.example.admin.loadingzone.retrofit.model.TruckAddResponse;
import com.example.admin.loadingzone.retrofit.model.VehicleDoc;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 9/7/2017.
 */

public class TruckDocumentDeleteListAdapter extends RecyclerView.Adapter<TruckDocumentDeleteListAdapter.ImageViewHolder> {


    private List<VehicleDoc> vehicleDocArrayList = new ArrayList<>();
    private Context context;
    ApiInterface apiService;
    private ListAdapterListener mListener;

    public interface ListAdapterListener { // create an interface
        void onClickAtOKButton(int position); // create callback function
    }

    public TruckDocumentDeleteListAdapter(List<VehicleDoc> vehicleDocArrayList, Context context,ListAdapterListener mListener) {
        this.vehicleDocArrayList = vehicleDocArrayList;
        this.context = context;
        this.mListener=mListener;

    }

    @Override
    public TruckDocumentDeleteListAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TruckDocumentDeleteListAdapter.ImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_truck_document_delete, parent, false));
    }

    @Override
    public int getItemCount() {
        return vehicleDocArrayList.size();
    }

    @Override
    public void onBindViewHolder(final TruckDocumentDeleteListAdapter.ImageViewHolder holder, final int position) {
        final VehicleDoc vehicleDoc = vehicleDocArrayList.get(position);

        Picasso.with(holder.itemView.getContext())
                .load(vehicleDoc.getDocumentFile())
                .into(holder.imageViewTruckDoc);
        final int document_id = vehicleDocArrayList.get(position).getVehicleDocumentId();
        holder.textViewDocTitle.setText(vehicleDoc.getDocumentTitle());
        holder.imageViewdocmentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickAtOKButton(position);
                notifyItemChanged(position);

            }
        });

    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewTruckDoc, imageViewdocmentDelete;
        TextView textViewDocTitle;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageViewTruckDoc = (ImageView) itemView.findViewById(R.id.iv_truck_doc);
            textViewDocTitle = (TextView) itemView.findViewById(R.id.text_truckDoc_title);
            imageViewdocmentDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }


}
