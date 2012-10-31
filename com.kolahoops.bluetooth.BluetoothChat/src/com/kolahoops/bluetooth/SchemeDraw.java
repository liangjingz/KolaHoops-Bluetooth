package com.kolahoops.bluetooth;

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

public class SchemeDraw extends Activity{
	private Bitmap schemebitmap;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.schemecreator);
        setSchemebitmap(Bitmap.createBitmap( 1, 8, Config.RGB_565));
        setupbuttons();
        
	}
	
	public void setupbuttons(){
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setBackgroundColor(schemebitmap.getPixel(1,1));
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
      //         sendMessage("A");
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
		button2.setBackgroundColor(schemebitmap.getPixel(1,2));
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
      //         sendMessage("A");
            }
        });
        Button button3 = (Button) findViewById(R.id.button3);
		button3.setBackgroundColor(schemebitmap.getPixel(1,3));
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
      //         sendMessage("A");
            }
        });
        Button button4 = (Button) findViewById(R.id.button4);
		button4.setBackgroundColor(schemebitmap.getPixel(1,4));
        button4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
      //         sendMessage("A");
            }
        });
        Button button5 = (Button) findViewById(R.id.button5);
		button5.setBackgroundColor(schemebitmap.getPixel(1,5));
        button5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
      //         sendMessage("A");
            }
        });
        Button button6 = (Button) findViewById(R.id.button6);
		button6.setBackgroundColor(schemebitmap.getPixel(1,6));
        button6.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
      //         sendMessage("A");
            }
        });
        Button button7 = (Button) findViewById(R.id.button7);
		button7.setBackgroundColor(schemebitmap.getPixel(1,7));
        button7.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
      //         sendMessage("A");
            }
        });
        Button button8 = (Button) findViewById(R.id.button8);
		button8.setBackgroundColor(schemebitmap.getPixel(1,8));
        button8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
      //         sendMessage("A");
            }
        });
	
	}
	
	
	
	public Bitmap getSchemebitmap() {
		return schemebitmap;
	}

	public void setSchemebitmap(Bitmap schemebitmap) {
		this.schemebitmap = schemebitmap;
	}

}