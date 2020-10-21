package com.example.contactbook.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactbook.Fragment.ContactFragment;
import com.example.contactbook.Model.Contact;
import com.example.contactbook.R;

import java.util.ArrayList;

@SuppressWarnings("UnnecessaryLocalVariable")
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private OnItemClickListener mListener;
    private ContactFragment fragment;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ContactAdapter(ContactFragment fragment) {
        this.fragment = fragment;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView mFirstName;
        public TextView mLastName;
        public TextView mContactType;
        public TextView mContactText;
        public ImageView mDeleteImage;

        public ContactViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mFirstName = itemView.findViewById(R.id.rv_person_firstName_text);
            mLastName = itemView.findViewById(R.id.rv_person_lastName_text);
            mContactType = itemView.findViewById(R.id.rv_contactType_text);
            mContactText = itemView.findViewById(R.id.rv_contact_text);
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
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_contact_item, parent, false);
        ContactViewHolder evh = new ContactViewHolder(v, mListener);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact currentItem = fragment.mContactList.get(position);

        holder.mFirstName.setText(currentItem.personFirstName);
        holder.mLastName.setText(currentItem.personLastName);
        holder.mContactType.setText(currentItem.contactTypeName);
        holder.mContactText.setText(currentItem.text);
    }

    @Override
    public int getItemCount() {
        return fragment.mContactList.size();
    }
}
