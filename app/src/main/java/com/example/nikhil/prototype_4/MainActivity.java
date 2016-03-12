package com.example.nikhil.prototype_4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MainActivity extends AppCompatActivity {

    TextView textView = (TextView)findViewById((R.id.textView));
    EditText entermessage = (EditText)findViewById(R.id.entermessage);
    Button sendbutton = (Button)findViewById(R.id.sendButton);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Server data", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void butClick(View arg0) {
        Thread t = new Thread(){

            @Override
            public void run() {
                try {
                    Socket s = new Socket("http://localhost/page1.php", 8080);
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    dos.writeUTF(entermessage.getText().toString());

                    //read input stream
                    DataInputStream dis2 = new DataInputStream(s.getInputStream());
                    InputStreamReader disR2 = new InputStreamReader(dis2);
                    BufferedReader br = new BufferedReader(disR2);//create a BufferReader object for input

                    //print the input to the application screen
                    final TextView receivedMsg = (TextView) findViewById(R.id.textView);
                    receivedMsg.setText(br.toString());

                    dis2.close();
                    s.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        Toast.makeText(this, "The message has been sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
