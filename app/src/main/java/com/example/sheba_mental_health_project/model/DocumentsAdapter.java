package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sheba_mental_health_project.R;

import java.util.List;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsViewHolder> {

    private final Context mContext;

    private final List<String> mDocuments;

    private final String TAG = "DocumentsAdapter";


    public DocumentsAdapter(Context mContext, List<String> documents) {
        this.mContext = mContext;
        this.mDocuments = documents;
    }

    public class DocumentsViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardLayout;
        private final ImageView documentIv;

        public DocumentsViewHolder(@NonNull View itemView) {
            super(itemView);

            cardLayout = itemView.findViewById(R.id.card_layout);
            documentIv = itemView.findViewById(R.id.document_iv);

            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager
                    .LayoutParams) cardLayout.getLayoutParams();
            layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels / 2;
            cardLayout.setLayoutParams(layoutParams);
        }
    }

    @NonNull
    @Override
    public DocumentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_cell, parent, false);

        return new DocumentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocumentsViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mDocuments.size();
    }
}
