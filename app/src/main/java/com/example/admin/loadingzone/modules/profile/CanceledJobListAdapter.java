package com.example.admin.loadingzone.modules.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.modules.myjob.MyJobListAdapter;
import com.example.admin.loadingzone.retrofit.model.CancelledJob;
import com.example.admin.loadingzone.retrofit.model.JobList;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 8/3/2017.
 */

public class CanceledJobListAdapter extends RecyclerView.Adapter<CanceledJobListAdapter.ViewHolder> {

    List<CancelledJob> joblistCanceled = new ArrayList<>();
    private int rowLayout;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        @BindView(R.id.textCustomerName)
        TextView textViewCustomerName;
        @NonNull
        @BindView(R.id.textLocationFrom)
        TextView textViewLocationFrom;
        @NonNull
        @BindView(R.id.textLocationTo)
        TextView textViewLocationTo;
        @NonNull
        @BindView(R.id.textLoadingMaterial)
        TextView textLoadingMaterial;
        @NonNull
        @BindView(R.id.textCancelReason)
        TextView textCancelReason;
        @NonNull
        @BindView(R.id.textTruckDate)
        TextView textViewTruckDate;
        @NonNull
        @BindView(R.id.imageViewCustomPic)
        ImageView imageViewCustomPic;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public CanceledJobListAdapter(List<CancelledJob> joblistCanceled, int rowLayout, Context context) {
        this.joblistCanceled = joblistCanceled;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public CanceledJobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new CanceledJobListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CanceledJobListAdapter.ViewHolder holder, int position) {

        holder.textViewCustomerName.setText(joblistCanceled.get(position).getCustomer().getName());
        holder.textViewLocationTo.setText(joblistCanceled.get(position).getToLocation().getName());
        holder.textViewLocationFrom.setText(joblistCanceled.get(position).getFromLocation().getName());
        holder.textLoadingMaterial.setText(joblistCanceled.get(position).getMaterial().getMaterialName());
        holder.textCancelReason.setText(joblistCanceled.get(position).getCancelReason().getCancelReasonId().getQuotationCancelReason());
        holder.textViewTruckDate.setText(joblistCanceled.get(position).getLoadingDate());
        Picasso.with(context)
                .load(joblistCanceled.get(position).getCustomer().getProfilePic())
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(70, 70)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(holder.imageViewCustomPic);
    }

    @Override
    public int getItemCount() {
        if (joblistCanceled != null) {
            return joblistCanceled.size();
        }
        return 0;
    }
}

