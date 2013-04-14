package dinah.core;

import java.io.*;
import java.util.*;
import dinah.util.*;
import dinah.sql.*;
import dinah.sql.results.*;

public class Dinah {
	private Shell shell;
	private String dataDirectory;
	private ArrayList<Database> databaseList;
	//private ArrayList<User> userList;

	public Dinah(String dataDirectory, String database) throws Exception {

		System.out.println("Data Directory: "+dataDirectory);
		System.out.println("Database: "+database);

		this.setDataDirectory(dataDirectory);
		this.shell = new Shell(this, database);
		this.databaseList = new ArrayList<Database>();
		//this.userList = new ArrayList<User>();
	}

	private void setDataDirectory(String dataDirectory) throws Exception {
		this.dataDirectory = dataDirectory;
		File  directory = new File(this.dataDirectory);
		directory.mkdir();
	}

	public String getDataDirectory() {
		return this.dataDirectory;
	}

	public ArrayList<Database> getDatabaseList() {
		return this.databaseList;
	}

	public void start() throws Exception {
		this.shell.prompt(System.in, System.out);
	}

	public void status() {

	}

	public void shutdown() {

	}

	public void restart() {

	}

	public static void main(String args[]) throws Exception {
		if (args.length != 2) {
			System.out.println("usage: java dinah.core.Dinah dataDirectory databaseName");
			System.exit(0);
		}

		Dinah dinah = new Dinah(args[0], args[1]);
		dinah.start();
	}
}
