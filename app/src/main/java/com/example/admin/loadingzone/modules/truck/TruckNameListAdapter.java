package com.example.admin.loadingzone.modules.truck;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.example.admin.loadingzone.modules.view.ColorGenerator;
import com.example.admin.loadingzone.modules.view.TextDrawable;
import com.example.admin.loadingzone.retrofit.model.VehicleMaker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/20/2017.
 */

public class TruckNameListAdapter extends RecyclerView.Adapter<TruckNameListAdapter.ViewHolder> {
    private List<VehicleMaker> truckNameList = new ArrayList<>();
    private Context context;

    public TruckNameListAdapter(Context context, List<VehicleMaker> truckNameList) {

        this.context = context;
        this.truckNameList = truckNameList;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trck_name, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        VehicleMaker data = truckNameList.get(position);
        holder.textTruckName.setText(data.getMakerName());

        // For setting the material Textdrawable
        ColorGenerator generator = ColorGenerator.MATERIAL;
        generator = ColorGenerator.MATERIAL;
        String title = truckNameList.get(position).getMakerName();
        int color = generator.getRandomColor();
        if (title != null) {
            String firstLetter = String.valueOf(title.charAt(0));

            TextDrawable drawable1 = TextDrawable.builder().beginConfig().fontSize(25)
                    .useFont(Typeface.defaultFromStyle(Typeface.NORMAL))
                    .withBorder(0).endConfig()
                    .buildRound(firstLetter.toUpperCase(), color
                    );

            holder.imageViewLogo.setImageDrawable(drawable1);
        }
    }

    @Override
    public int getItemCount() {
        if (truckNameList != null) {
            return truckNameList.size();
        }

        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTruckName;
        ImageView imageViewLogo;

        public ViewHolder(View itemView) {
            super(itemView);
            textTruckName = (TextView) itemView.findViewById(R.id.textTruckName);
            imageViewLogo = (ImageView) itemView.findViewById(R.id.imageLogo);
        }

    }
}