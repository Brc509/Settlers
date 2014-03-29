package catan.server.command.moves;

import java.io.IOException;
import java.util.Map;

import catan.model.ClientModel;
import catan.model.Model;
import catan.server.Cookie;
import catan.server.Games;
import catan.server.command.Command;
import catan.server.handler.HandlerUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

public class BuildSettlementCommand implements Command{
	
	private String type;
	private int playerIndex;
	public VertexLocation vertexLocation;
	private boolean free;

	public BuildSettlementCommand(){
	}
	
	class VertexLocation
	{
		int x;
		int y;
		String direction;
	}

	@Override
	public Object execute(Object obj){
		Model model = Games.get().getGames().get((Integer)obj);
		Player currPlayer = model.getPlayerByIndex(playerIndex);
		
		if(!free)
		{
			ResourceList playerList = currPlayer.getResources();
			
			playerList.setBrick(playerList.getBrick()-1);
			playerList.setWood(playerList.getWood()-1);
			playerList.setSheep(playerList.getSheep()-1);
			playerList.setWheat(playerList.getWheat()-1);
			
			int brick = model.getModel().get("bank").getAsJsonObject().get("brick").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("brick", brick+1);
			int wood = model.getModel().get("bank").getAsJsonObject().get("wood").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("wood", wood+1);
			int sheep = model.getModel().get("bank").getAsJsonObject().get("sheep").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("sheep", sheep+1);
			int wheat = model.getModel().get("bank").getAsJsonObject().get("wheat").getAsInt();
			model.getModel().get("bank").getAsJsonObject().addProperty("wheat", wheat+1);
		}
		
		return null;
	}

}
