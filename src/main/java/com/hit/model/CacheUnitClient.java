package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class CacheUnitClient {

	private static final int PORT = 12345;
	private Socket mSocket;
	private ObjectInputStream mInputStream;
	private ObjectOutputStream mOutputStream;
	
	public CacheUnitClient() {
	}
	private boolean createSocket() {
		try {
			mSocket = new Socket("localhost",PORT);
			mInputStream = new ObjectInputStream(mSocket.getInputStream());//read server response
	 		mOutputStream = new ObjectOutputStream(mSocket.getOutputStream());//write to socket
	 		return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return false;
	}
	
	public String send(String request) {
		String str = null;
		try {
			if((mSocket == null ||!mSocket.isConnected()) && !createSocket()) {
				return "could not connect to server.\n";
			}
			mOutputStream.writeObject(request);
		    mOutputStream.flush();
			System.out.println("Waiting for response");
			str = (String) mInputStream.readObject();
			System.out.println("Recieved response: " + str);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
		}
		
		return str;
	}	

}