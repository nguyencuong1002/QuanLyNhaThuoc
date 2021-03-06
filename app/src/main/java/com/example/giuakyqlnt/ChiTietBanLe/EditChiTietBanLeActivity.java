package com.example.giuakyqlnt.ChiTietBanLe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.giuakyqlnt.NhaThuoc.ActivityNhaThuoc;
import com.example.giuakyqlnt.NhaThuoc.EditNhaThuocActivity;
import com.example.giuakyqlnt.R;

public class EditChiTietBanLeActivity extends AppCompatActivity {
    TextView tvSOHD, tvMATHUOC;
    EditText txtSOLUONG;
    Button btnAdd, btnCancel;
    ImageView ivBack;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chi_tiet_ban_le);
        mapping();
        setEvent();
        getData();
    }

    public void getData(){
        ChiTietBanLe chiTietBanLe = (ChiTietBanLe) getIntent().getSerializableExtra("item");
        tvSOHD.setText(chiTietBanLe.getSOHD());
        tvMATHUOC.setText(chiTietBanLe.getMATHUOC());
        txtSOLUONG.setText(chiTietBanLe.getSOLUONG());
    }

    public void save(View view){
        String SOHD = tvSOHD.getText().toString();
        String MATHUOC = tvMATHUOC.getText().toString();
        String SOLUONG = txtSOLUONG.getText().toString();
        //Check data
        isAllFieldsChecked = CheckAllFields();
        if (isAllFieldsChecked) {
            //Update dữ liệu
            ContentValues values = new ContentValues();
            values.put(ActivityChiTietBanLe.SOLUONG_FIELD, SOLUONG);
            String whereClause = ""+ActivityChiTietBanLe.SOHD_FIELD+" = ? AND "+ActivityChiTietBanLe.MATHUOC_FIELD+" = ?";
            String[] whereArgs = {SOHD,MATHUOC};
            ActivityChiTietBanLe.myDatabase.update(ActivityChiTietBanLe.TABLE_NAME, values, whereClause, whereArgs);
            startActivity(new Intent(com.example.giuakyqlnt.ChiTietBanLe.EditChiTietBanLeActivity.this, ActivityChiTietBanLe.class));
        }
    }

    private boolean CheckAllFields() {
        if (txtSOLUONG.length() == 0) {
            txtSOLUONG.setError("Vui lòng không để trống!");
            return false;
        } else if (!txtSOLUONG.getText().toString().matches("[0-9]+")) {
            txtSOLUONG.setError("Vui lòng chỉ nhập kí tự số!");
            return false;
        }
        return true;
    }

    public void cancle(View view){
        startActivity(new Intent(com.example.giuakyqlnt.ChiTietBanLe.EditChiTietBanLeActivity.this, ActivityChiTietBanLe.class));
    }

    public void setEvent(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditChiTietBanLeActivity.this, ActivityChiTietBanLe.class));
            }
        });
    }

    private void mapping(){
        tvSOHD = findViewById(R.id.tvSOHD);
        tvMATHUOC = findViewById(R.id.tvMATHUOC);
        txtSOLUONG = findViewById(R.id.txtSOLUONG);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        ivBack = findViewById(R.id.ivBack);

    }
}