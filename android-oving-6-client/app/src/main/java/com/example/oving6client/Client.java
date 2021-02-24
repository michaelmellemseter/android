package com.example.oving6client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {
	private final static String TAG = "Client";
	private final static String IP = "10.0.2.2";
	private final static int PORT = 12345;
	private String res;

    private int[] array;

    public Client(int[] array)
    {
        this.array=array;
    }
    
	public void run() {
    	Socket s 			= null;
    	ObjectOutputStream out		= null;
    	BufferedReader in 	= null;

        try {
            s = new Socket(IP, PORT);
            Log.v(TAG, "C: Connected to server" + s.toString());
            out = new ObjectOutputStream(s.getOutputStream());
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.writeObject(array);
            
            res = in.readLine();
            Log.i(TAG,res);

            //out.println("PING to server from client");

        } catch (IOException e) {
            e.printStackTrace();
        }finally{//close socket!!
        	try{
        	    if(out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
                if(s != null) {
                    s.close();
                }
        	}catch(IOException e){}
        }
    }

    public String getAnswer(){
        return res;
    }
}