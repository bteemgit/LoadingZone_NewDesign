package com.example.admin.loadingzone.modules.myjob;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.modules.home.PostedJobListAdapter;
import com.example.admin.loadingzone.retrofit.model.JobList;
import com.example.admin.loadingzone.view.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 6/29/2017.
 */

public class AssignedJobListAdapter extends RecyclerView.Adapter<MyJobListAdapter.ViewHolder> {

    private List<JobList> jobList = new ArrayList<>();
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
        @BindView(R.id.textTruckType)
        TextView textViewTruckType;
//        @NonNull
//        @BindView(R.id.textTruckDimension)
//        TextView textViewTruckDimension;
        @NonNull
        @BindView(R.id.textTruckDate)
        TextView textViewTruckDate;
        @NonNull
        @BindView(R.id.textTruckBudget)
        TextView textViewTruckBudget;
        @NonNull
        @BindView(R.id.imageViewCustomPic)
        ImageView imageViewCustomPic;

        @NonNull
        @BindView(R.id.id_txt_daterequestedrelative)
        TextView textViewDateRequestedRelative;



        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public AssignedJobListAdapter(List<JobList> jobList, int rowLayout, Context context) {
        this.jobList = jobList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MyJobListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MyJobListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyJobListAdapter.ViewHolder holder, int position) {



        holder.textViewCustomerName.setText(jobList.get(position).getCustomer().getName());
        holder.textViewDateRequestedRelative.setText(jobList.get(position).getDateRequestedRelative());
        holder.textViewLocationTo.setText(jobList.get(position).getToLocation().getName());
        holder.textViewLocationFrom.setText(jobList.get(position).getFromLocation().getName());
        holder.textViewTruckType.setText(jobList.get(position).getMaterial().getMaterialName());
       // holder.textViewTruckDimension.setText(jobList.get(position).getQuotationCount());
        holder.textViewTruckDate.setText((CharSequence) jobList.get(position).getJobdates().getExpectedStartDate()+" "+jobList.get(position).getJobdates().getExpectedStartTime());

        holder.textViewJobCode.setText(jobList.get(position).getJob_code());



        Picasso.with(context)
                .load(jobList.get(position).getCustomer().getProfilePic())
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(70, 70)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(holder.imageViewCustomPic);
    }

    @Override
    public int getItemCount() {
        if (jobList != null) {
            return jobList.size();
        }
        return 0;
    }
}

