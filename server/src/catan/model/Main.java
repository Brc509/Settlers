package catan.model;

public class Main {

	public static void main(String[] args) {
		
		ClientModel clientModel = new ClientModel();
		clientModel.initializeMap(false, false, false);
		System.out.println(clientModel.getModel());
	}
}
