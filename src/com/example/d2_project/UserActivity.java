package com.example.d2_project;

import com.example.d2_project.data.User;

import android.app.AlertDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends BaseActivity{
	private TextView tv_name;
	private TextView tv_money;
	private TextView tv_point;
	private TextView tv_level;
	private ListView lv;
	private Button btn_add;
	private Button btn_del;
	private Button btn_talk;
	private Button btn_map;
	private String[] roads=new String[]{"һ��·","����·","����·","�ĺ�·","���·"};
	private User u;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		Intent intent=getIntent();
		u=(User)intent.getExtras().getSerializable("user");
		tv_name=(TextView)findViewById(R.id.textView2);
		tv_money=(TextView)findViewById(R.id.textView4);
		tv_point=(TextView)findViewById(R.id.textView6);
		tv_level=(TextView)findViewById(R.id.textView9);
		lv=(ListView)findViewById(R.id.listView1);
		btn_add=(Button)findViewById(R.id.button1);
		btn_del=(Button)findViewById(R.id.button2);
		btn_talk=(Button)findViewById(R.id.button3);
		btn_map=(Button)findViewById(R.id.button4);
		
		tv_name.setText(""+u.name.trim());
		tv_money.setText(""+u.money);
		tv_point.setText(""+u.point);
		tv_level.setText(""+u.level);
		
		if (u.isFriend){
			btn_add.setVisibility(View.GONE);
			btn_del.setVisibility(View.VISIBLE);
		}else{
			btn_add.setVisibility(View.VISIBLE);
			btn_del.setVisibility(View.GONE);
		}
		
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, roads));
		
		btn_add.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(UserActivity.this).setMessage("ȷ����� "+u.name.trim()+" ��Ϊ���ĺ��ѣ�")
				.setPositiveButton("��", null).setNegativeButton("��", null).show();
			}
		});
		
		btn_del.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(UserActivity.this).setMessage("ȷ���� "+u.name.trim()+" �����ĺ��Ѻ�����ɾ��")
				.setPositiveButton("��", null).setNegativeButton("��", null).show();
			}
		});
		
		btn_talk.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Toast.makeText(UserActivity.this, "��Ϊ������ "+u.name.trim(), Toast.LENGTH_SHORT).show();				
			}
		});
		
		btn_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i=new Intent(UserActivity.this,RoadMaskedMapActivity.class);
				i.putExtra("user",u);
				startActivity(i);
			}
		});
	}
}
