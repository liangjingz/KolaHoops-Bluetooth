package com.kolahoops.bluetooth;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SchemeDraw extends Activity
{
	
	 int selectedcolor = 0;
	  private Button button1;
	  private Button button2;
	  private Button button3;
	  private Button button4;
	  private Button button5;
	  private Button button6;
	  private Button button7;
	  private Button button8;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schemecreator);
    setSchemebitmap(Bitmap.createBitmap( 1, 8, Config.ARGB_8888));

   
    
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
    	   colorpicker();
       	
        }
   });
    button2.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
     	   colorpicker();
        	
         }
    });
    button3.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
     	   colorpicker();
        	
         }
    });    button4.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
     	   colorpicker();
        	
         }
    });    button5.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
     	   colorpicker();
        	
         }
    });    button6.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
     	   colorpicker();
        	
         }
    });    button7.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
     	   colorpicker();
        	
         }
    });    button8.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
     	   colorpicker();
        	
         }
    });
    button1.setBackgroundResource(R.color.schemecolor1);
    button2.setBackgroundResource(R.color.schemecolor2);
    button3.setBackgroundResource(R.color.schemecolor3);
    button4.setBackgroundResource(R.color.schemecolor4);
    button5.setBackgroundResource(R.color.schemecolor5);
    button6.setBackgroundResource(R.color.schemecolor6);
    button7.setBackgroundResource(R.color.schemecolor7);
    button8.setBackgroundResource(R.color.schemecolor8);
    
    
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
	            }
	        });
	        dialog.show();
	    }
    
	}
	

