package com.example.nathan.app2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class MyActivity extends Activity {

    private Socket socket;
    private final static int SERVER_PORT = 5000;
    private static final String SERVER_IP = "10.0.2.2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        new Thread(new ClientThread()).start();

        final TextView status = (TextView) findViewById(R.id.status);
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int var = status.getCurrentTextColor();
                if (var != getResources().getColor(R.color.color1)) {
                    status.setTextColor(getResources().getColor(R.color.color1));
                } else {
                    status.setTextColor(getResources().getColor(R.color.color2));
                }
            }
        });

        //for locking the car
        //unlock should look the same
        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                    int var = button3.getCurrentTextColor();
                    if (var != getResources().getColor(R.color.color1)) {
                        button3.setTextColor(getResources().getColor(R.color.color1));
                        out.print(1);
                    } else {
                        button3.setTextColor(getResources().getColor(R.color.color2));
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    //Handles the click of button2
    //If connected and button2 is pressed, it will print to Server output
    public void onClick(View view) {
        try {
           // EditText et = (EditText) findViewById(R.id.EditText01);
            String str = "yo";
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println(str);

            //echo data back to the server if data is received
            while(true)
            {
                String input = in.toString();
                out.println(input);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Client class
    class ClientThread implements Runnable{
        @Override
        public void run(){
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVER_PORT);

            }catch (UnknownHostException e1){
                e1.printStackTrace();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }

    }

}