package com.kolahoops.bluetooth;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SchemeDraw extends Activity{

	
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
	//  private final Handler mHandler;
	  public static final int UPDATE_COLORS = 6;
	  private final Handler mHandler = new Handler();

	  
		public void getcolors(){
			SharedPreferences settings = getSharedPreferences("colors",Activity.MODE_WORLD_READABLE);
	        color1 = settings.getInt("1", -1);
	        color2 = settings.getInt("2", -1);
	        color3 = settings.getInt("3", -1);
	        color4 = settings.getInt("4", -1);
	        color5 = settings.getInt("5", -1);
	        color6 = settings.getInt("6", -1);
	        color7 = settings.getInt("7", -1);
	        color8 = settings.getInt("8", -1);
	        }
		public void setcolors(){
			SharedPreferences settings = getSharedPreferences("colors", Activity.MODE_WORLD_READABLE);
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putInt("1",color1);
	        editor.putInt("2",color2);
	        editor.putInt("3",color3);
	        editor.putInt("4",color4);
	        editor.putInt("5",color5);
	        editor.putInt("6",color6);
	        editor.putInt("7",color7);
	        editor.putInt("8",color8);
	        editor.commit();	
		}
	  
	 @Override
	    public void onDestroy() {
	        super.onDestroy();
	         // setcolors();
     	 
	    }
	  public void onResume (Bundle savedInstanceState){
		  super.onResume();
		  getcolors();
		  
	  }
	  public void onPause (Bundle savedInstanceState){
		  super.onPause();
		//  setcolors();
		  
	  }
	  @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schemecreator);
        
        
    
    button1 = (Button) findViewById(R.id.button1); 
    button2 = (Button) findViewById(R.id.button2); 
    button3 = (Button) findViewById(R.id.button3); 
    button4 = (Button) findViewById(R.id.button4); 
    button5 = (Button) findViewById(R.id.button5); 
    button6 = (Button) findViewById(R.id.button6); 
    button7 = (Button) findViewById(R.id.button7); 
    button8 = (Button) findViewById(R.id.button8); 
    
    refreshbuttons();
    
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
	                setcolors();
	                refreshbuttons();
	                //send color to hoop
	                updatehoop();
	               // Intent intent=new Intent(getIntent() ,BluetoothChat.class);
	               // sendMessage("J "+color1+" "+color2+" "+color3+" "+color4+" "+color5+" "+color6+" "+color7+" "+color8)
	        //        mUserText = (EditText) textEntryView.findViewById(R.id.txt_password);
	         //       String strpwd = mUserText.getText().toString();
                   // intent.putExtra("my_password",strpwd);
	           //     intent.putExtra("my_password",strpwd);
	             //   startActivity(intent); 
	            	getcolors();
	            	
	            }
	        });
	        dialog.show();
	    }
	 public void refreshbuttons(){
		 getcolors();
	     	button1.setBackgroundColor(color1);
		    button2.setBackgroundColor(color2);
		    button3.setBackgroundColor(color3);
		    button4.setBackgroundColor(color4);
		    button5.setBackgroundColor(color5);
		    button6.setBackgroundColor(color6);
		    button7.setBackgroundColor(color7);
		    button8.setBackgroundColor(color8);
	 }
	
public void updatehoop(){
	
}

}
	

