package com.example.contactbook.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactbook.Fragment.ContactTypeFragment;
import com.example.contactbook.Model.ContactType;
import com.example.contactbook.R;

import java.util.ArrayList;

@SuppressWarnings("UnnecessaryLocalVariable")
public class ContactTypeAdapter extends RecyclerView.Adapter<ContactTypeAdapter.ContactTypeViewHolder> {
    private ContactTypeFragment mContactTypeFragment;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ContactTypeAdapter(ContactTypeFragment contactTypeFragment) {
        mContactTypeFragment = contactTypeFragment;
    }

    public static class ContactTypeViewHolder extends RecyclerView.ViewHolder {
        public TextView mText;
        public ImageView mDeleteImage;

        public ContactTypeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mText = itemView.findViewById(R.id.rv_contactType_text_text);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ContactTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_contect_type_item, parent, false);
        ContactTypeViewHolder evh = new ContactTypeViewHolder(v, mListener);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull ContactTypeViewHolder holder, int position) {
        ContactType currentItem = mContactTypeFragment.mContactTypeList.get(position);

        holder.mText.setText(currentItem.text);
    }

    @Override
    public int getItemCount() {
        return mContactTypeFragment.mContactTypeList.size();
    }
}
