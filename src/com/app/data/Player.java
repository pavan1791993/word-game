package com.app.data;

public class Player {

	private String name;
	private int points;
	private boolean usedBlankTile;

	public Player(final String name) {
		super();
		this.name = name;
	}

	public void addPoints(final int points) {
		this.points = this.points + points;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public boolean isUsedBlankTile() {
		return usedBlankTile;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPoints(final int points) {
		this.points = points;
	}

	public void setUsedBlankTile(final boolean usedBlankTile) {
		this.usedBlankTile = usedBlankTile;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", points=" + points + "]";
	}

}
