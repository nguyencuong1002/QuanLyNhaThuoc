package com.example.giuakyqlnt.NhaThuoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.giuakyqlnt.MainActivity;
import com.example.giuakyqlnt.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddNhaThuocActivity extends AppCompatActivity {
    ActivityNhaThuoc NT;
    EditText txtMaNT, txtTenNT, txtDiaChi;
    Button btnAdd, btnCancel;
    ImageView ivBack;
    boolean isAllFieldsChecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nha_thuoc);
        mapping();
        setEvent();
    }

    public void add(View view) {
        String maNT = txtMaNT.getText().toString();
        String tenNT = txtTenNT.getText().toString();
        String diaChi = txtDiaChi.getText().toString();
        //Check data
        isAllFieldsChecked = CheckAllFields();
        if (isAllFieldsChecked) {
            //Add dữ liệu
            ContentValues values = new ContentValues();
            values.put(ActivityNhaThuoc.MaNT_FIELD, maNT);
            values.put(ActivityNhaThuoc.TenNT_FIELD, tenNT);
            values.put(ActivityNhaThuoc.DiaChi_FIELD, diaChi);
            ActivityNhaThuoc.myDatabase.insert(ActivityNhaThuoc.TABLE_NAME, null, values);
            startActivity(new Intent(AddNhaThuocActivity.this, ActivityNhaThuoc.class));
        }
    }

    //Validate All Field
    private boolean CheckAllFields() {
        if (txtMaNT.length() == 0) {            //ma nha thuoc
            txtMaNT.setError("Vui lòng không để trống!");
            return false;
        } else if (!txtMaNT.getText().toString().matches("[a-zA-Z0-9]+")) {
            txtMaNT.setError("Vui lòng chỉ nhập kí tự chữ hoặc số!");
            return false;
        }else if(NT.myDatabase.checkExistID(NT.TABLE_NAME, NT.MaNT_FIELD, txtMaNT.getText().toString())){
            txtMaNT.setError("Mã nhà thuốc đã tồn tại!");
            return false;
        }
        if (txtTenNT.length() == 0) {       //ten nha thuoc
            txtTenNT.setError("Vui lòng không để trống!");
            return false;
        }
        if (txtDiaChi.length() == 0) {      //dia chi
            txtDiaChi.setError("Vui lòng không để trống!");
            return false;
        }
        return true;
    }

    public void cancle(View view){
        startActivity(new Intent(AddNhaThuocActivity.this, ActivityNhaThuoc.class));
    }

    public void setEvent(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNhaThuocActivity.this, ActivityNhaThuoc.class));
            }
        });
    }

    private void mapping(){
        txtMaNT = findViewById(R.id.txtMaNT);
        txtTenNT = findViewById(R.id.txtTenNT);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        ivBack = findViewById(R.id.ivBack);
    }
}