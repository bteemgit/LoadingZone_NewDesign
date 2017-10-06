package com.example.admin.loadingzone.modules.truck;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.loadingzone.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TruckDocumentDetailActivity extends AppCompatActivity {
    @NonNull
    @BindView(R.id.text_DocDate)
    TextView textDocDate;

    @NonNull
    @BindView(R.id.text_DocTitle)
    TextView textDocTitle;

    @NonNull
    @BindView(R.id.ivtruckDoc)
    SimpleDraweeView ivtruckDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_document_detail);
        ButterKnife.bind(this);
        Fresco.initialize(this);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Document Details");
        String image_url = getIntent().getStringExtra("image_url");
        String doc_date = getIntent().getStringExtra("doc_date");
        String doc_title = getIntent().getStringExtra("doc_title");
        Picasso.with(getApplicationContext())
                .load(image_url)
                .into(ivtruckDoc);
        textDocDate.setText(doc_date);
        textDocTitle.setText(doc_title);

    }

    // back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

