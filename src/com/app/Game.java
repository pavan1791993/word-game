package com.app;

import com.app.data.Player;

public class Game {

	public static void main(final String[] args) {
		final GameHelper helper = new GameHelper();
		final Player[] players = helper.createGame();
		helper.startGame(players);
	}

}
