package com.example.contactbook.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactbook.MainActivity;
import com.example.contactbook.Model.ContactType;
import com.example.contactbook.Adapter.ContactTypeAdapter;
import com.example.contactbook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactTypeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ContactTypeAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    public ArrayList<ContactType> mContactTypeList;

    private Button btn_add, btn_viewAll;
    private EditText et_text;

    public ContactTypeFragment(Context context){
        this.context = context;
        mContactTypeList = (ArrayList<ContactType>) MainActivity.dbHelper.ContactTypeGetAll();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_type, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new ContactTypeAdapter(this);
        mAdapter.setOnItemClickListener(new ContactTypeAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
                Log.d("G", "onDeleteClick ContactType");
            }
        });


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        btn_add = view.findViewById(R.id.btn_add);
        btn_viewAll = view.findViewById(R.id.btn_view);
        et_text = view.findViewById(R.id.et_contact_type_text);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactTypeAddBtn();
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactTypeViewAll();
            }
        });

        return view;
    }

    public void removeItem(int position) {
        ContactType contactType = mContactTypeList.get(position);
        boolean success = MainActivity.dbHelper.ContactTypeDelete(contactType.id);
        if (success) {
            mContactTypeList.remove(position);
            mAdapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Not happening!", Toast.LENGTH_LONG).show();
        }
    }

    public void contactTypeAddBtn() {
        if (!et_text.getText().toString().equals("")) {
            UUID id = java.util.UUID.randomUUID();
            ContactType contactType = new ContactType(id.toString(), et_text.getText().toString());

            boolean success = MainActivity.dbHelper.ContactTypeInsert(contactType);
            mContactTypeList.add(contactType);
            mAdapter.notifyItemInserted(mContactTypeList.size());
        }
    }

    public void contactTypeViewAll() {
        List<ContactType> contactTypeList = MainActivity.dbHelper.ContactTypeGetAll();
        mContactTypeList = (ArrayList<ContactType>) contactTypeList;
    }
}
