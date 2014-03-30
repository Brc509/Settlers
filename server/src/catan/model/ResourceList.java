package catan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResourceList {

	private int brick = 0;
	private int ore = 0;
	private int sheep = 0;
	private int wheat = 0;
	private int wood = 0;
	
	/**
	 * Initialize all resource values to 0
	 * Used for initializing players
	 */
	public ResourceList() {}

	public String toString() {
		
		String returnString = "{\n";
		returnString += "\tbrick: " + brick + "\n";
		returnString += "\tore: " + ore + "\n";
		returnString += "\tsheep: " + sheep + "\n";
		returnString += "\twheat: " + wheat + "\n";
		returnString += "\twood: " + wood + "\n";
		returnString += "}";
		return returnString;
	}
	
	/**
	 * Initialize the resource list with pre-defined resource values
	 * Useful for creating the ClientModel's bank
	 * @param brick
	 * @param ore
	 * @param sheep
	 * @param wheat
	 * @param wood
	 */
	public ResourceList(int brick, int ore, int sheep, int wheat, int wood) {
		
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
	}
	
	public void testString () {
		System.out.println( brick + " " + ore + " " + sheep + " " + wheat + " " + wood + " ");
	}

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}
	
	public String getRandomResource() {
		
		List<String> resources = new ArrayList<>();
		for (int i = 0; i < brick; i++)
			resources.add("brick");
		for (int i = 0; i < ore; i++)
			resources.add("ore");
		for (int i = 0; i < sheep; i++)
			resources.add("sheep");
		for (int i = 0; i < wheat; i++)
			resources.add("wheat");
		for (int i = 0; i < wood; i++)
			resources.add("wood");
		
		return resources.get(new Random().nextInt(resources.size()));
	}
	
	public boolean decrementResource(String resourceName) {
		
		if (resourceName.equals("brick") && brick > 0)
			brick--;
		else if (resourceName.equals("ore") && ore > 0)
			ore--;
		else if (resourceName.equals("sheep") && sheep > 0)
			sheep--;
		else if (resourceName.equals("wheat") && wheat > 0)
			wheat--;
		else if (resourceName.equals("wood") && wood > 0)
			wood--;
		else
			return false;
		return true;
	}
	
	public boolean incrementResource(String resourceName) {
		
		if (resourceName.equals("brick"))
			brick++;
		else if (resourceName.equals("ore"))
			ore++;
		else if (resourceName.equals("sheep"))
			sheep++;
		else if (resourceName.equals("wheat"))
			wheat++;
		else if (resourceName.equals("wood"))
			wood++;
		else
			return false;
		return true;
	}

	public boolean incrementResource(String resourceName, int amount) {
		if (resourceName.equals("brick"))
			brick += amount;
		else if (resourceName.equals("ore"))
			ore += amount;
		else if (resourceName.equals("sheep"))
			sheep += amount;
		else if (resourceName.equals("wheat"))
			wheat += amount;
		else if (resourceName.equals("wood"))
			wood += amount;
		else
			return false;
		return true;
	}
}