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




import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
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
    }
   
	//get the selected dropdown list value
	
	
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
    			
    			
    			 public void onStopTrackingTouch(SeekBar arg0) {
    		        }
    		 
    		   public void onStartTrackingTouch(SeekBar arg0) {
    		        }
    		  
    		   public void onProgressChanged(SeekBar arg0,
    		            int progress, boolean arg2) {
    			   sendMessage("B "+progress);
    		    }
    		 });
        
        Button00 = (Button) findViewById(R.id.Button00);
        Button00.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
               sendMessage("A");
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
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }
    
    

}