package com.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import com.app.data.Player;
import com.app.helper.Utils;

public class GameHelper {

	private Map<Character, Integer> characterPoints;
	private List<Character> characters;
	private Player currentPlayer;

	private final Scanner scan = new Scanner(System.in);

	public Player[] createGame() {
		loadCharacters();
		loadCharacterPoints();
		return createPlayers();
	}

	private Player[] createPlayers() {
		System.out.print("Enter name of Player 1 : ");
		final Player p1 = new Player(scan.next());
		System.out.print("Enter name of Player 2 : ");
		final Player p2 = new Player(scan.next());
		return new Player[] { p1, p2 };
	}

	private void finishGame(final Player[] players) {
		System.out.println();
		System.out.println("Game over, result is,");
		final Player p1 = players[0];
		final Player p2 = players[1];
		if (p1.getPoints() > p2.getPoints()) {
			System.out.println(p1.getName() + " is winner with " + p1.getPoints() + " points");
		} else if (p2.getPoints() > p1.getPoints()) {
			System.out.println(p2.getName() + " is winner with " + p2.getPoints() + " points");
		} else {
			System.out.println("Its a draw");
		}
		System.out.println();
	}

	private boolean isValidAnswer(final String ans, final char[] chars) {
		final char[] ansChars = ans.toLowerCase().toCharArray();
		final List<Character> charList = new LinkedList<>();
		for (final char c : chars) {
			charList.add(Character.toLowerCase(c));
		}
		for (final char c : ansChars) {
			if (!listContains(charList, c)) {
				return false;
			}
		}
		return true;
	}

	private boolean listContains(final List<Character> chars, final char c) {
		if (chars.contains(c)) {
			chars.remove(Character.valueOf(c));
			return true;
		} else if (chars.contains('_')) {
			chars.remove(Character.valueOf('_'));
			return true;
		} else {
			return false;
		}
	}

	private void loadCharacterPoints() {
		this.characterPoints = new HashMap<>();
		final Properties characterPointsProp = Utils.loadPropertiesOfFile("character_points.properties");
		for (final Entry<Object, Object> entry : characterPointsProp.entrySet()) {
			this.characterPoints.put(((String) entry.getKey()).charAt(0), Utils.toInt((String) entry.getValue()));
		}
	}

	private void loadCharacters() {
		this.characters = new ArrayList<>(98);
		final Properties characterCount = Utils.loadPropertiesOfFile("character_count.properties");
		for (final Entry<Object, Object> entry : characterCount.entrySet()) {
			final int count = Utils.toInt((String) entry.getValue());
			for (int i = 0; i < count; i++) {
				this.characters.add(((String) entry.getKey()).toUpperCase().charAt(0));
			}
		}
	}

	private int printOptions() {
		System.out.println();
		System.out.println("\tPress 1 for answer ");
		System.out.println("\tPress 2 to pass ");
		if (!currentPlayer.isUsedBlankTile()) {
			System.out.println("\tPress 3 to use blank tile ");
		}
		try {
			final String o = scan.next();
			return Integer.parseInt(o);
		} catch (final Exception e) {
			System.out.println("Invalid option : ");
			return printOptions();
		}
	}

	private char[] printRandomCharacters() {
		System.out.println();
		System.out.print(currentPlayer.getName() + ", try making word from characters : ");
		final char[] chars = new char[7];
		for (int i = 0; i < 7; i++) {
			final int randomNo = Utils.getRandom(characters.size());
			chars[i] = characters.get(randomNo);
			System.out.print(characters.get(randomNo) + " ");
			characters.remove(randomNo);
		}
		return chars;
	}

	private void processAnswer(final String ans, final char[] chars, final boolean blankTileUsed) {
		if (isValidAnswer(ans.toLowerCase(), chars)) {
			int points = 0;
			final char[] c = ans.toLowerCase().toCharArray();
			for (final char cha : c) {
				points = points + characterPoints.getOrDefault(cha, 0);
			}
			if (blankTileUsed) {
				points = points / 2;
			}
			currentPlayer.addPoints(points);
		} else {
			System.out.println();
			System.out.println("Invalid Answer : ");
			processOption(printOptions(), chars);
		}
	}

	private void processOption(final int i, final char[] chars) {
		if (i == 1) {
			System.out.print("Enter your answer : ");
			final String ans = scan.next();
			processAnswer(ans, chars, false);
		} else if (i == 2) {
			System.out.println("Passing...");
		} else if (i == 3) {
			if (currentPlayer.isUsedBlankTile()) {
				processOption(printOptions(), chars);
			} else {
				showBlankTile(chars);
			}
		} else {
			System.out.println("Incorrect Option : " + i);
			processOption(printOptions(), chars);
		}
	}

	private void showBlankTile(final char[] chars) {
		System.out.println("Enter your answer with any one additional character : ");
		currentPlayer.setUsedBlankTile(true);
		final char[] newchars = new char[8];
		int i = 0;
		for (final char c : chars) {
			newchars[i] = c;
			i++;
		}
		newchars[7] = '_';
		processAnswer(scan.next(), newchars, true);
	}

	public void startGame(final Player[] players) {
		currentPlayer = players[0];
		while (!characters.isEmpty()) {
			final char[] chars = printRandomCharacters();
			final int i = printOptions();
			processOption(i, chars);
			togglePlayer(players);
		}
		finishGame(players);
	}

	private void togglePlayer(final Player[] players) {
		if (players[0].equals(currentPlayer)) {
			currentPlayer = players[1];
		} else {
			currentPlayer = players[0];
		}
	}

}
