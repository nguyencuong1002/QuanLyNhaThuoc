package com.example.giuakyqlnt.ChiTietBanLe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuakyqlnt.ActivityThongTinBanLe;
import com.example.giuakyqlnt.MainActivity;
import com.example.giuakyqlnt.MyDatabase;
import com.example.giuakyqlnt.R;

import java.util.ArrayList;

public class ActivityChiTietBanLe extends AppCompatActivity {
    public static MyDatabase myDatabase;
    static final String DB_NAME = "qlnhathuoc.sqlite";
    static final String TABLE_NAME = "tbl_ChiTietBanLe";
    static final String SOHD_FIELD = "SoHD";
    static final String MATHUOC_FIELD = "MATHUOC";
    static final String SOLUONG_FIELD = "SOLUONG";

    ChiTietBanLeAdapter chiTietBanLeAdapter;
    ListView lvChiTietBanLe;
    ImageView ivAdd, ivBack;
    TextView tvXemTTBL;
    ArrayList<ChiTietBanLe> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ban_le);
        mapping();

        myDatabase = new MyDatabase(ActivityChiTietBanLe.this, DB_NAME, null, 1);
        String sql_create_table = "create table if not exists "+TABLE_NAME+" ("+SOHD_FIELD+" varchar(10) , "+MATHUOC_FIELD+" varchar(10), "+SOLUONG_FIELD+" varchar(50),primary key("+SOHD_FIELD+","+MATHUOC_FIELD+"))";
        //Tạo bảng
        myDatabase.ExecuteSQL(sql_create_table);
        loadData();
        setEvent();
    }

    public void loadData() {
        list = getAll();
        chiTietBanLeAdapter = new ChiTietBanLeAdapter(ActivityChiTietBanLe.this, R.layout.dong_chi_tiet_ban_le, list);
        lvChiTietBanLe.setAdapter(chiTietBanLeAdapter);
    }

    public void setEvent(){
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityChiTietBanLe.this, AddChiTietBanLeActivity.class));

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityChiTietBanLe.this, MainActivity.class));
            }
        });

        tvXemTTBL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityChiTietBanLe.this, ActivityThongTinBanLe.class));
            }
        });

    }

    //Lấy danh sách
    public ArrayList<ChiTietBanLe> getAll() {
        ArrayList<ChiTietBanLe> list = new ArrayList<>();
        String sql_select = "select * from " + TABLE_NAME;
        Cursor c = myDatabase.SelectData(sql_select);
        while (c.moveToNext()) {
            String SOHD = c.getString(0);
            String MATHUOC = c.getString(1);
            String SOLUONG = c.getString(2);

            ChiTietBanLe chiTietBanLe = new ChiTietBanLe(SOHD, MATHUOC, SOLUONG);
            list.add(chiTietBanLe);
        }
        return list;
    }


    //Show dialog Xóa Dữ liệu
    public void DialogXoaCV(String SOHD, String MATHUOC) {
        String whereClause = ""+SOHD_FIELD+" = ? AND "+MATHUOC_FIELD+" = ?";
        String[] whereArgs = {SOHD,MATHUOC};
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa chi tiết hóa đơn có số hóa đơn:  " +SOHD+ " và mã thuốc: "+MATHUOC+ " không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDatabase.delete(TABLE_NAME, whereClause, whereArgs);
                Toast.makeText(ActivityChiTietBanLe.this, "Đã xóa " + SOHD +" "+ MATHUOC, Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
        chiTietBanLeAdapter.notifyDataSetChanged();
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();
    }


    private void mapping() {
        lvChiTietBanLe = findViewById(R.id.lvChiTietBanLe);
        ivAdd = findViewById(R.id.ivAdd);
        ivBack = findViewById(R.id.ivBack);
        tvXemTTBL = findViewById(R.id.tvXemTTBL);
//        ivBackToBanThuoc = findViewById(R.id.ivBackToBanThuoc);
    }
}
