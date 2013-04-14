package dinah.core;

import java.io.*;
import java.util.*;

public class Database {
	private String dataDirectory;
	private String name;
	private ArrayList<Relation> relationList;

	public Database(String dataDirectory, String name) 
			throws Exception {
		this.relationList = new ArrayList<Relation>();
		this.setDataDirectory(dataDirectory);
		this.setName(name);
	}

	public void setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
	}

	public String getDataDirectory() {
		return this.dataDirectory;
	}

	public void setName(String name) throws Exception {
		this.name = name;

		System.out.println(this.dataDirectory+"/"+this.name);

		File databaseDirectory = new File(this.dataDirectory+"/"+
				this.name);
		databaseDirectory.mkdir();
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Relation> getRelationList() {
		return this.relationList;
	}
}
