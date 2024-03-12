package com.example.lab3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddActivity extends AppCompatActivity {
    protected EditText editTextId;
    protected EditText editTextName;
    protected EditText editTextPhone;
    protected Button btnOk;
    protected Button btnCancel;
    protected ImageView imageView;
    protected String uriImage;
    // Một hằng số nguyên dương để xác định kết quả trả về từ Intent
    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        setBtnOk();
        setBtnCancel();
        setImageView();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            int id=bundle.getInt("id");
            String name = bundle.getString("name");
            String phone = bundle.getString("phone");
            uriImage = bundle.getString("image");
//
            editTextId.setText(Integer.toString(id));//error - chuyển in thanh string
            editTextName.setText(name);
            editTextPhone.setText(phone);
//
            imageView.setImageURI(Uri.parse(uriImage));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_PICK_IMAGE && resultCode==RESULT_OK){
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uriImage=imageUri.toString();
        }
    }

    protected void setImageView(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_PICK_IMAGE);
            }
        });
    }
    protected void setBtnOk(){
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                int id;
                String name;
                String phone;
                id=Integer.parseInt(editTextId.getText().toString());
                name=editTextName.getText().toString();
                phone=editTextPhone.getText().toString();
                bundle.putInt("id",id);
                bundle.putString("name",name);
                bundle.putString("phone",phone);
                bundle.putString("image",uriImage);

                intent.putExtras(bundle);
                setResult(118,intent);//nho tra ca intent
                finish();
            }
        });
    }
    protected void setBtnCancel(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    protected void init(){
        editTextId = findViewById(R.id.add_edit_id);
        editTextName = findViewById(R.id.add_edit_name);
        editTextPhone = findViewById(R.id.add_edit_phone);
        btnOk = findViewById(R.id.add_btn_ok);
        btnCancel = findViewById(R.id.add_btn_cancel);
        imageView = findViewById(R.id.add_imageview);
    }
}