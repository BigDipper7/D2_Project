package com.example.d2_project;

import com.example.d2_project.data.Item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

public class StoreActivity extends BaseActivity implements OnTouchListener,OnGestureListener,OnClickListener{
	
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private Button btn;
	private ViewFlipper vf;
	private GridView gv1;
	private GridView gv2;
	private GestureDetector gd;
	private double money=500;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);
		tv1=(TextView)findViewById(R.id.textView1);
		tv2=(TextView)findViewById(R.id.textView2);
		tv3=(TextView)findViewById(R.id.textView3);
		tv1.setTextColor(Color.RED);
		btn=(Button)findViewById(R.id.button1);
		vf=(ViewFlipper)findViewById(R.id.viewFlipper1);
		gv1=(GridView)findViewById(R.id.gridView1);
		gv2=(GridView)findViewById(R.id.gridView2);
		tv2.setText(String.format("��%.2f  ", money));
		tv1.setOnClickListener(this);
		tv3.setOnClickListener(this);
		gv1.setOnTouchListener(this);
		gv2.setOnTouchListener(this);
		gd=new GestureDetector(this, this);
		
		gv1.setOnItemClickListener(gv1ItemClick);
		gv2.setOnItemClickListener(gv2ItemClick);
		gv1.setAdapter(new ItemGridAdapter(this, getStoreItems()));
		gv2.setAdapter(new ItemGridAdapter(this, getUserItems()));
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(StoreActivity.this,ItemActivity.class));
				finish();
			}
		});
		
	}
	
	private Item[] getStoreItems(){
		Item[] items=new Item[30];
		for (int i=0;i<items.length;++i){
			items[i]=new Item();
			Item t=items[i];
			t.name="����"+i;
			t.icon="@drawable/icon"+(int)(Math.random()*42);
			t.count=0;
			t.price=Math.random()*50;
			t.decription="���� "+t.name+" ��������Ϣ��";
		}
		return items;
	}
	
	private Item[] getUserItems(){
		Item[] items=new Item[30];
		for (int i=0;i<items.length;++i){
			items[i]=new Item();
			Item t=items[i];
			t.name="����"+i;
			t.icon="@drawable/icon"+(int)(Math.random()*42);
			t.count=(int) (Math.random()*10)+1;
			t.price=Math.random()*100;
			t.decription="���� "+t.name+" ��������Ϣ��";
		}
		return items;
	}
	
	OnItemClickListener gv1ItemClick=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			final Item t=(Item)parent.getAdapter().getItem(position);
			new AlertDialog.Builder(StoreActivity.this).setTitle("�Ƿ�����˵��ߣ�")
			.setMessage(t.name+"\n"+t.decription)
			.setPositiveButton("��", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (money>=t.price){
						money-=t.price;
						tv2.setText(String.format("��%.2f  ", money));
					}else{
						Toast.makeText(StoreActivity.this, "��û���㹻�Ľ�ҹ���˵���", Toast.LENGTH_SHORT).show();
					}					
				}
			}).setNegativeButton("��",null).show();		
		}
	};
	
	OnItemClickListener gv2ItemClick=new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
			final Item t=(Item)parent.getAdapter().getItem(position);
			final AdapterView<?> Parent=parent;
			final View V=v;
			new AlertDialog.Builder(StoreActivity.this).setTitle("�Ƿ�����˵��ߣ�")
			.setMessage(t.name+"\n"+t.decription)
			.setPositiveButton("��", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (money>=t.price){
						money-=t.price;
						t.count-=1;
						if (t.count==0) ((ItemGridAdapter)Parent.getAdapter()).remove(t);
						tv2.setText(String.format("��%.2f  ", money));						
						((ItemGridAdapter)Parent.getAdapter()).notifyDataSetChanged();
					}else{
						Toast.makeText(StoreActivity.this, "��û���㹻�Ľ�ҹ���˵���", Toast.LENGTH_SHORT).show();
					}					
				}
			}).setNegativeButton("��",null).show();		
		}
	};

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gd.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void show1(){
		if (vf.getCurrentView()==gv1) return;
		vf.setInAnimation(getApplicationContext(), R.anim.push_right_in);   
		vf.setOutAnimation(getApplicationContext(), R.anim.push_right_out);  
		vf.showNext();
		tv1.setTextColor(Color.RED);
		tv3.setTextColor(Color.BLACK);
	}
	
	private void show2(){
		if (vf.getCurrentView()==gv2) return;
		vf.setInAnimation(getApplicationContext(), R.anim.push_left_in);   
		vf.setOutAnimation(getApplicationContext(), R.anim.push_left_out);		
		vf.showPrevious();
		tv1.setTextColor(Color.BLACK);
		tv3.setTextColor(Color.RED);		
	}
	
	

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX()-e2.getX()>400){
			show2();
			return true;
		}else if (e2.getX()-e1.getX()>400){
			show1();	
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { return false; }

	@Override
	public void onShowPress(MotionEvent e) {}

	@Override
	public boolean onSingleTapUp(MotionEvent e) { return false;	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.textView1:show1();break;
		case R.id.textView3:show2();break;
		}
	}
}

