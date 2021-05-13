package com.example.sheba_mental_health_project.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sheba_mental_health_project.R;

import java.util.List;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentsViewHolder> {

    private final Context mContext;

    private final List<String> mDocuments;

    private final boolean isTherapist;

    private final String TAG = "DocumentsAdapter";

    public interface DocumentListener {
        void onDocumentClicked(int position, View view);
        void onRemoveDocumentClicked(int position, View view);
    }

    private DocumentsAdapter.DocumentListener listener;

    public void setDocumentListener(final DocumentsAdapter.DocumentListener listener) {
        this.listener = listener;
    }


    public DocumentsAdapter(Context mContext, List<String> documents, boolean isTherapist) {
        this.mContext = mContext;
        this.mDocuments = documents;
        this.isTherapist = isTherapist;
    }

    public class DocumentsViewHolder extends RecyclerView.ViewHolder {

    //    private final CardView cardLayout;
        private final RelativeLayout relativeLayout;
        private final ImageView documentIv;
        private final ImageButton removeDocumentIb;

        public DocumentsViewHolder(@NonNull View itemView) {
            super(itemView);

       //     cardLayout = itemView.findViewById(R.id.card_layout);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            documentIv = itemView.findViewById(R.id.document_iv);
            removeDocumentIb = itemView.findViewById(R.id.remove_document_ib);

            removeDocumentIb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                        listener.onRemoveDocumentClicked(getAdapterPosition(),v);
                }
            });

            documentIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                        listener.onDocumentClicked(getAdapterPosition(),v);
                }
            });

            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager
                    .LayoutParams) relativeLayout.getLayoutParams();
            final int layoutSize = mContext.getResources().getDisplayMetrics().widthPixels * 5 / 11;
            layoutParams.width = layoutParams.height = layoutSize;
            relativeLayout.setLayoutParams(layoutParams);
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
        String imageUri = mDocuments.get(position);
        if(isTherapist){
            holder.removeDocumentIb.setVisibility(View.GONE);
        }else {
            holder.removeDocumentIb.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(imageUri).into(holder.documentIv);
    }

    public String getItemAtPosition(int position){
        return mDocuments.get(position);
    }

    @Override
    public int getItemCount() {
        return mDocuments.size();
    }
}
