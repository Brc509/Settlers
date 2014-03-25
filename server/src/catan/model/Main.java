package catan.model;

public class Main {

	public static void main(String[] args) {
		
		ClientModel clientModel = new ClientModel();
		try {
		
			clientModel.initializeDefaultMap();
		} 
		catch (Exception e) {
			
			System.out.println("ERROR!");
			e.printStackTrace();
		}
		System.out.println("Done!");
	}
}
