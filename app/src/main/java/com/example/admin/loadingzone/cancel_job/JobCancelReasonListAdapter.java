package com.example.admin.loadingzone.cancel_job;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.retrofit.model.QuotationCancelReason;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 8/3/2017.
 */

public class JobCancelReasonListAdapter extends RecyclerView.Adapter<JobCancelReasonListAdapter.ViewHolder> {

    List<QuotationCancelReason> quotationCancelReasonList = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        @BindView(R.id.textCancelReason)
        TextView textCancelReason;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }


    }

    public JobCancelReasonListAdapter(List<QuotationCancelReason> quotationCancelReasonList, int rowLayout, Context context) {
        this.quotationCancelReasonList = quotationCancelReasonList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public JobCancelReasonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new JobCancelReasonListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobCancelReasonListAdapter.ViewHolder holder, int position) {
        holder.textCancelReason.setText(quotationCancelReasonList.get(position).getQuotationCancelReason());
    }

    @Override
    public int getItemCount() {
        if (quotationCancelReasonList != null) {
            return quotationCancelReasonList.size();
        }
        return 0;
    }
}


