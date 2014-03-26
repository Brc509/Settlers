package catan.model;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		
		ClientModel clientModel = new ClientModel();
		try {
		
			clientModel.initializeDefaultMap();
			System.out.println(clientModel.getModel());
		} 
		catch (Exception e) {
			
			System.out.println("ERROR!");
			e.printStackTrace();
		}
		System.out.println("Done!");
	}
}
