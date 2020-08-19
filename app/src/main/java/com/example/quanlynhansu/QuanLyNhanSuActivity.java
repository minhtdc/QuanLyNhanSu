package com.example.quanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlynhansu.adapter.MyAdapter;
import com.example.quanlynhansu.data_models.NhanSu;
import com.example.quanlynhansu.database_layer.MyDatabaseLayer;
import com.example.quanlynhansu.view_models.MyRadioButtonGroup;

import java.util.ArrayList;

public class QuanLyNhanSuActivity extends AppCompatActivity {
    public static String TRUNGCAP = "Trung Cap";
    public static String CAODANG = "Cao dang";
    public static String DAIHOC = "Dai Hoc";
    public static String KHONGBANGCAP = "Khong Bang Cap";

    //database
    private MyDatabaseLayer DAO;

    RadioGroup rdoGroup;
    EditText edtName, edtKhac;
    CheckBox chkA, chkB;
    TextView lblName, lblSoThich, lblBangCap, lblKhac;
    ListView lstNhanSu;
    Button btnThem, btnThoat;
    private MyAdapter adapter;
    private ArrayList<NhanSu> listMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quan_ly_nhan_su_layout);

        DAO = new MyDatabaseLayer(this);

        edtName = findViewById(R.id.edtName);
        edtKhac = findViewById(R.id.edtKhac);
        chkA = findViewById(R.id.chkA);
        chkB = findViewById(R.id.chkB);
        lblName = findViewById(R.id.lblName);
        lblBangCap = findViewById(R.id.lblBangCap);
        lblSoThich = findViewById(R.id.lblSoThich);
        lblKhac = findViewById(R.id.lblKhac);
        lstNhanSu = findViewById(R.id.lstNhanSu);
        btnThem = findViewById(R.id.btnThem);
        btnThoat = findViewById(R.id.btnThoat);
        rdoGroup = findViewById(R.id.rdoGroup);

        //tạo list view
        listMembers = new ArrayList<NhanSu>();
        //đưa dữ liệu từu database lên listview
        DAO.getMembers(listMembers);
        //MyRadioButtonGroup myRadioButtonGroup = new MyRadioButtonGroup(rdoGr,rad2,rad3);
        adapter = new MyAdapter(this, R.layout.list_item_layout,listMembers);
        //adapter = new ArrayAdapter<NhanSu>(this,android.R.layout.simple_list_item_1, arrNhanSu);
        lstNhanSu.setAdapter(adapter);


        //button thoat
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Button Them
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edtName.getText().toString();
                String hoppies = "";
                String degree = "";


                if(!name.isEmpty()){
                    if(chkA.isChecked()){
                        hoppies += chkA.getText().toString();
                        if(chkB.isChecked() || !edtKhac.getText().toString().isEmpty()){
                            hoppies+="; ";
                        }
                    }
                    if(chkB.isChecked()){
                        hoppies += chkB.getText().toString();
                        if(!edtKhac.getText().toString().isEmpty()){
                            hoppies +="; ";
                        }
                    }
                    if(!edtKhac.getText().toString().isEmpty()){
                        hoppies += edtKhac.getText().toString();
                    }
                }

                int radID = rdoGroup.getCheckedRadioButtonId();
                switch (radID){
                    case R.id.rad1:
                        degree = TRUNGCAP;
                        break;
                    case R.id.rad2:
                        degree = CAODANG;
                        break;
                    case R.id.rad3:
                        degree = DAIHOC;
                        break;
                    default:
                        degree = KHONGBANGCAP;
                }

                NhanSu nhanSu = new NhanSu(name,degree,hoppies);
                listMembers.add(nhanSu);
                adapter.notifyDataSetChanged();


                edtName.setText("");
                rdoGroup.clearCheck();
                chkA.setChecked(false);
                chkB.setChecked(false);
                edtKhac.setText("");
                edtName.requestFocus();
            }
        });
    }

    //hàm tạo option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int menuID = item.getItemId();
        switch (menuID){
            case R.id.menuItemRemove:
                remove();
                break;
            case R.id.menuItemUpdate:
                update();
                break;
            case R.id.menuItemSave:
                save();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void remove(){

        //Toast.makeText(this,"remove",Toast.LENGTH_SHORT).show();
        for(int i = 0; i < listMembers.size();i++)
        {
            if(listMembers.get(i).isCheck()){
                DAO.removeMember(listMembers.get(i));
                listMembers.remove(i);
                i--;
            }
        }
        adapter.notifyDataSetChanged();
    }
    private void update(){

        Toast.makeText(this,"update",Toast.LENGTH_SHORT).show();
    }
    private void save(){
        Toast.makeText(this,"save",Toast.LENGTH_SHORT).show();
        DAO.saveMembers(listMembers);
    }
}


