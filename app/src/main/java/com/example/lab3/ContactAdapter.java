package com.example.lab3;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    protected ArrayList<Contact> data;
    protected LayoutInflater inflater;
    protected Activity context;

    public ContactAdapter(ArrayList<Contact> data, Activity context) {
        this.data = data;
        this.context = context;
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        if(v==null){
            v=inflater.inflate(R.layout.sub_item_layout,null);
        }
        //catch components
        CheckBox status=v.findViewById(R.id.sub_item_checkbox);
        TextView name=v.findViewById(R.id.sub_item_textName);
        TextView phone=v.findViewById(R.id.sub_item_textPhone);
        TextView imageUri=v.findViewById(R.id.sub_item_textPathImage);
        ImageView imageView=v.findViewById(R.id.sub_item_ImageView);
        //Set data
        Contact cur=data.get(position);
        status.setChecked(cur.isStatus());
        name.setText(cur.getFullname());
        phone.setText(cur.getPhone());
        imageUri.setText(cur.getUriImage());
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cur.setStatus(status.isChecked());
            }
        });
        //set image
        imageView.setImageURI(Uri.parse(cur.getUriImage()));
        return v;
    }
}
