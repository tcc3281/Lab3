package com.example.lab3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    protected EditText editTextFullName;
    protected ListView listView;
    protected Button btnAdd;
    protected Button btnDelete;
    protected ArrayList<Contact> contacts;
    protected ContactAdapter adapter;
    protected int selectedIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initData();
        setBtnAdd();
        setBtnDelete();
    }
    protected void initData(){
        contacts.add(new Contact(1,"Chien Tran","093738943",false));
        contacts.add(new Contact(2,"Vi Than","033738941",false));
        adapter.notifyDataSetChanged();
    }
    protected void init(){
        editTextFullName=findViewById(R.id.main_editext_fullname);
        listView=findViewById(R.id.main_listview);
        btnAdd=findViewById(R.id.main_btn_add);
        btnDelete=findViewById(R.id.main_btn_delete);
        contacts=new ArrayList<>();
        //set adapter
        adapter = new ContactAdapter(contacts,this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex =position;
                return false;
            }
        });
        //set context menu
        registerForContextMenu(listView);
    }
    protected void setBtnAdd(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,126);
            }
        });
    }
    protected void setBtnDelete(){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stack<Contact> st=new Stack<>();
                for(int i =0;i<contacts.size();i++){
                    Contact temp=contacts.get(i);
                    if(temp.isStatus()){
                        st.push(temp);
                    }
                }
                while(!st.empty()){
                    contacts.remove(st.pop());
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==126 && resultCode==118){
            Bundle bundle=data.getExtras();
            int id = bundle.getInt("id");
            String name = bundle.getString("name");
            String phone = bundle.getString("phone");
            String uriImage = bundle.getString("image");

            Contact newContact = new Contact(id,name,phone,false,uriImage);
            contacts.add(newContact);
            adapter.notifyDataSetChanged();
        }
        if(requestCode==103 && resultCode==118){
            Bundle bundle=data.getExtras();
            int id = bundle.getInt("id");
            String name = bundle.getString("name");
            String phone = bundle.getString("phone");
            String uriImage = bundle.getString("image");

            Contact newContact = new Contact(id,name,phone,false,uriImage);
            contacts.set(selectedIndex,newContact);
            adapter.notifyDataSetChanged();//error
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu,menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Contact contact=contacts.get(selectedIndex);
        if(item.getItemId()==R.id.menu_context_edit){
            Intent intent = new Intent(MainActivity.this,AddActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id",contact.getId());
            bundle.putString("name",contact.getFullname());
            bundle.putString("phone",contact.getPhone());
            bundle.putString("image",contact.uriImage);
            intent.putExtras(bundle);
            startActivityForResult(intent,103);
        } else if(item.getItemId()==R.id.menu_context_delete){
            contacts.remove(selectedIndex);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId()==R.id.menu_context_call) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contact.getPhone()));
            startActivity(intent);
        } else if (item.getItemId()==R.id.menu_context_sms) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:"+contact.getPhone()));
            startActivity(intent);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.actionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_action_id){
            Collections.sort(contacts,(u,v)->{
                if(u.getId()>v.getId()) return 1;
                else if (u.getId()==v.getId()) return 0;
                else return -1;
            });
            adapter.notifyDataSetChanged();
        } else if (item.getItemId()==R.id.menu_action_name) {
            Collections.sort(contacts,(u,v)->{
                return u.getFullname().compareTo(v.getFullname());
            });
            adapter.notifyDataSetChanged();
        } else if (item.getItemId()==R.id.menu_action_phone) {
            Collections.sort(contacts,(u,v)->{
                return u.getPhone().compareTo(v.getPhone());
            });
            adapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}