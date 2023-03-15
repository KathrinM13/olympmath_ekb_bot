package main.java.models;

import java.util.ArrayList;

public class ThemesStorage implements Storage<Theme> {
	
	private ArrayList<Theme> themes;
	
	public ThemesStorage() {
		themes = new ArrayList<Theme>();
	}
	
	public ThemesStorage(ArrayList<Theme> themes) {
		this.themes = themes;
	}

	@Override
	public Theme getById(int id) {
		try {
			return themes.get(id);
		}
		catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public int getSize() {
		return themes.size();
	}

	@Override
	public ArrayList<Theme> getAll() {
		return themes;
	}

}
