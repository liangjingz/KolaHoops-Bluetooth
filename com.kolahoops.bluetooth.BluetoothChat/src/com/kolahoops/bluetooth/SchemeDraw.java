package com.kolahoops.bluetooth;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SchemeDraw extends Activity
{
	
	 int selectedcolor = 0;
	 int selectedslot = 0;
	public int color1;
	public int color2;
	public int color3;
	public int color4;
	public int color5;
	public int color6;
	public int color7;
	public int color8;
	  private Button button1;
	  private Button button2;
	  private Button button3;
	  private Button button4;
	  private Button button5;
	  private Button button6;
	  private Button button7;
	  private Button button8;
	  

	  @Override
	    public void onDestroy() {
	        super.onDestroy();
	     Intent BluetoothChats = new Intent(this,BluetoothChat.class);
	     BluetoothChats.putExtra("color1",color1);
     	 BluetoothChats.putExtra("color2",color2);
     	 BluetoothChats.putExtra("color3",color3);
     	 BluetoothChats.putExtra("color4",color4);
     	 BluetoothChats.putExtra("color5",color5);
     	 BluetoothChats.putExtra("color6",color6);
     	 BluetoothChats.putExtra("color7",color7);
     	 BluetoothChats.putExtra("color8",color8);
     	 startActivity(BluetoothChats);
	    }
	  
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schemecreator);
   // setSchemebitmap(Bitmap.createBitmap( 1, 8, Config.ARGB_8888));

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
        color1=extras.getInt("color1");
        color2=extras.getInt("color2");
        color3=extras.getInt("color3");
        color4=extras.getInt("color4");
        color5=extras.getInt("color5");
        color6=extras.getInt("color6");
        color7=extras.getInt("color7");
        color8=extras.getInt("color8");
        }
        
    
    button1 = (Button) findViewById(R.id.button1); 
    button2 = (Button) findViewById(R.id.button2); 
    button3 = (Button) findViewById(R.id.button3); 
    button4 = (Button) findViewById(R.id.button4); 
    button5 = (Button) findViewById(R.id.button5); 
    button6 = (Button) findViewById(R.id.button6); 
    button7 = (Button) findViewById(R.id.button7); 
    button8 = (Button) findViewById(R.id.button8); 
    
    button1.setOnClickListener(new OnClickListener() {
       public void onClick(View v) {
    	   selectedslot=1;
    	   colorpicker();
       	
        }
   });
    button2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	selectedslot=2;
        	colorpicker();
     	           	
         }
    });
    button3.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	selectedslot=3;
        	colorpicker();
        	
         }
    });    button4.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	selectedslot=4;
        	colorpicker();
        	
         }
    });    button5.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	selectedslot=5;
        	colorpicker();
        	
         }
    });    button6.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	selectedslot=6;
        	colorpicker();
        	
         }
    });    button7.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	selectedslot=7;
        	colorpicker();
        	
         }
    });    button8.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	selectedslot=8;
        	colorpicker();
        	
         }
    });
     color1 = Color.WHITE;
	 color2 = Color.RED;
	 color3 = Color.GREEN;
	 color4 = Color.BLUE;
	 color5 = Color.BLACK;
	 color6 = Color.CYAN;
	 color7 = Color.MAGENTA;
	 color8 = Color.YELLOW;
    refreshbuttons();
    
	}
	
	private int getColor(int schemecolor1) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void setSchemebitmap(Bitmap createBitmap) {
		// TODO Auto-generated method stub
		
	}

	 public void colorpicker() {
		 
	        //     initialColor is the initially-selected color to be shown in the rectangle on the left of the arrow.
	        //     for example, 0xff000000 is black, 0xff0000ff is blue. Please be aware of the initial 0xff which is the alpha.
	 
	        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, R.color.schemecolor1, new OnAmbilWarnaListener() {
	 
	            // Executes, when user click Cancel button
	    
	            public void onCancel(AmbilWarnaDialog dialog){
	            }
	 
	            // Executes, when user click OK button
	        
	            public void onOk(AmbilWarnaDialog dialog, int color) {
	            
	            	Toast.makeText(getBaseContext(), "Selected Color : " + color, Toast.LENGTH_LONG).show();
	                selectedcolor = color;
	                if(selectedslot==1){
	                	color1=color;
	                	}
	                if(selectedslot==2){
	                	color2=color;
	                	}
	                if(selectedslot==3){
	                	color3=color;
	                	}
	                if(selectedslot==4){
	                	color4=color;
	                	}
	                if(selectedslot==5){
	                	color5=color;
	                	}
	                if(selectedslot==6){
	                	color6=color;
	                	}
	                if(selectedslot==7){
	                	color7=color;
	                	}
	                if(selectedslot==8){
	                	color8=color;
	                	}
	                refreshbuttons();
	            }
	        });
	        dialog.show();
	    }
	 public void refreshbuttons(){
	     	button1.setBackgroundColor(color1);
		    button2.setBackgroundColor(color2);
		    button3.setBackgroundColor(color3);
		    button4.setBackgroundColor(color4);
		    button5.setBackgroundColor(color5);
		    button6.setBackgroundColor(color6);
		    button7.setBackgroundColor(color7);
		    button8.setBackgroundColor(color8);
	 }
	 public void refreshbuttonsinit(){
		    button1.setBackgroundResource(color1);
		    button2.setBackgroundResource(color2);
		    button3.setBackgroundResource(color3);
		    button4.setBackgroundResource(color4);
		    button5.setBackgroundResource(color5);
		    button6.setBackgroundResource(color6);
		    button7.setBackgroundResource(color7);
		    button8.setBackgroundResource(color8);
	 }


    
	}
	

