/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kolahoops.bluetooth;




import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the main Activity that displays the current chat session.
 */
@TargetApi(5)

public class BluetoothChat extends Activity {
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int UPDATE_COLORS = 6;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private TextView mTitle;
    private ListView mConversationView;
    private Button Button00;
    private Button Button01;
    private Button Button02;
    private Button Button03;
    private SeekBar seekBar1;
  
    private Spinner spinner1;
    private Spinner spinner2;

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
	public byte chksum;
    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");
        
        // Set up the window layout
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
      
        getcolors();
      
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
        refreshbuttons();

 /*       if(color1==0&&color2==0&&color3==0&&color4==0&&color5==0&&color6==0&&color7==0&&color8==0){
          	     color1 = Color.WHITE;
        		 color2 = Color.RED;
        		 color3 = Color.GREEN;
        		 color4 = Color.BLUE;
        		 color5 = Color.BLACK;
        		 color6 = Color.CYAN;
        		 color7 = Color.MAGENTA;
        		 color8 = Color.YELLOW;
        }
   */     
        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.app_name);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }
   
	//get the selected dropdown list value
	
    public void getcolors(){
		SharedPreferences settings = getSharedPreferences("colors", Activity.MODE_WORLD_READABLE);
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
		 sendMessage("J "+color1+" "+color2+" "+color3+" "+color4+" "+color5+" "+color6+" "+color7+" "+color8);
	 }

	
    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");
        
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }
    

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);
        
    		spinner1 = (Spinner) findViewById(R.id.spinner1);
    		spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){
    			
    			public void onItemSelected(AdapterView<?> parent, View view, int pos,
    					long id) {
    				Toast.makeText(parent.getContext(), 
    						"Selecting Pattern: " + parent.getItemAtPosition(pos).toString(),
    						Toast.LENGTH_SHORT).show();
    				sendMessage("P "+pos);
    			}

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
    		});
    	
    		spinner2 = (Spinner) findViewById(R.id.spinner2);
    		spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){
    			public void onItemSelected(AdapterView<?> parent, View view, int pos,
    					long id) {
    				Toast.makeText(parent.getContext(), 
    						"Selecting Pattern: " + parent.getItemAtPosition(pos).toString(),
    						Toast.LENGTH_SHORT).show();
    				sendMessage("C "+pos);
    			}

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}	;
    		});
    		
    		seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
    		seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
    			
    	//		
    			
    			 public void onStopTrackingTouch(SeekBar arg0) {
    		        }
    		 
    		   public void onStartTrackingTouch(SeekBar arg0) {
    		        }
    		  
    		   public void onProgressChanged(SeekBar arg0,
    		            int progress, boolean arg2) {
    			   sendMessage("B "+progress);
    		    }
    		 });
    		
    		
    		//this will pass data to bluetooth on color choose.
    		
    		
 
    			             
    			
        
        Button00 = (Button) findViewById(R.id.Button00);
        Button00.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
          
            	sendMessage("R");
          
            }
        });
        Button01 = (Button) findViewById(R.id.Button01);
        Button01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("E");
            }
        });
        Button02 = (Button) findViewById(R.id.Button02);
        Button02.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("D");
            }
        });
        Button03 = (Button) findViewById(R.id.Button03);
        Button03.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("I");
            }
        });
 
            

        /*     Button04 = (Button) findViewById(R.id.Button04);
        Button04.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("4");
            }
        });
       Button05 = (Button) findViewById(R.id.Button05);
        Button05.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("5");
            }
        });
        Button06 = (Button) findViewById(R.id.Button06);
        Button06.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("6");
            }
        });
        Button07 = (Button) findViewById(R.id.Button07);
        Button07.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("7");
            }
        });
        Button08 = (Button) findViewById(R.id.Button08);
        Button08.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("8");
            }
        });
        Button09 = (Button) findViewById(R.id.Button09);
        Button09.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("9");
            }
        });
        Button10 = (Button) findViewById(R.id.Button10);
        Button10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("A");
            }
        });
        Button11 = (Button) findViewById(R.id.Button11);
        Button11.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("B");
            }
        });
        Button12 = (Button) findViewById(R.id.Button12);
        Button12.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("C");
            }
        });
        Button13 = (Button) findViewById(R.id.Button13);
        Button13.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("D");
            }
        });
        Button14 = (Button) findViewById(R.id.Button14);
        Button14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("E");
            }
        });
        Button15 = (Button) findViewById(R.id.Button15);
        Button15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("F");
            }
        });
        
       */ 

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);
        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    public void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        message=message + '\n';//add a newline to the message before we convert it to ascii
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);
       
            
            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }

    
    

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    mTitle.setText(R.string.title_connected_to);
                    mTitle.append(mConnectedDeviceName);
                    mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    mTitle.setText(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            case UPDATE_COLORS:
            	getcolors();
            	BluetoothChat.this.sendMessage("J "+color1+" "+color2+" "+color3+" "+color4+" "+color5+" "+color6+" "+color7+" "+color8);
            	break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.scan:
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;

    }
		return false;
    
    

}
}

 