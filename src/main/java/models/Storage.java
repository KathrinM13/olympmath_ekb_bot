package main.java.models;

import java.util.ArrayList;

public interface Storage<DataType> {
	DataType getById(int id);
	ArrayList<DataType> getAll();
	int getSize();
	
}
