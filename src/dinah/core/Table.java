package dinah.core;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import dinah.data.*;
import dinah.sql.*;
import dinah.sql.results.*;

public class Table extends Relation {
	private String name;
	private String filename;
	private ArrayList<Field> fields;
	private DataFile datafile;

	/*
	public Table() {
		this.name = null;
		this.filename = null;
		this.datafile = new DataFile(this);
		this.fields = new ArrayList<Field>();
		this.fields.add(new Field("deleted", "int", 4, false, false, false));
	}*/

	public Table(Database database, String name) 
			throws Exception {
		super(database);

		this.datafile = new DataFile(this);
		this.fields = new ArrayList<Field>();

		// adicionando um campo para definir se registro foi ou nao
		// 	removido do arquivo de dados
		this.fields.add(new Field("deleted", "int", 4, false, false, false));

		this.setName(name);
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public void setName(String name) throws Exception {
		Matcher validName = Pattern.compile("[\\s\\t]*(\\w+)[\\s\\t]*").matcher(name);
		if (validName.find()) {
			String dataDirectory = this.getDatabase().getDataDirectory();
			String databaseName = this.getDatabase().getName();
			this.name = name;

			// Criando arquivo de dados para a tabela
			this.filename = dataDirectory+"/"+databaseName+"/"+this.name+".dat";
			File dataFilename = new File(this.filename);
			dataFilename.createNewFile();
		}
	}

	public String getName() {
		return this.name;
	}

	public String getFilename() {
		return this.filename;
	}

	public ArrayList<Field> getFields() {
		return this.fields;
	}

	public int getSize() {
		int size = 0;

		for (int i = 0; i < this.fields.size(); i++) {
			size += this.fields.get(i).getSize();
		}

		return size;
	}

	public int insert(ArrayList<String> fieldValues) 
			throws Exception {

		// verificar se o numero de fields eh igual
		// 	ao numero de valores que vieram para mim
		if (this.fields.size() != fieldValues.size())
			return -1;

		for (int i = 0; i < this.fields.size(); i++) {
			// recuperando o campo que deve ter um valor
			// 	definido pelo usuario de nosso BD
			Field field = this.fields.get(i);
			if (!field.validate(fieldValues.get(i))) {
				return -1;
			}
		}

		this.datafile.append(fieldValues);

		return 0;
	}

	public void remove() {

	}

	public void update() {

	}

	public SQLQuery search(ArrayList<SQLClause> clauses) 
				throws Exception {
		return this.datafile.search(clauses);
	}

	public SQLQuery search(SQLQuery sqlQuery) 
			throws Exception {
		return this.datafile.search(sqlQuery);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("Data filename: "+this.filename+"\n");
		sb.append("Table name: "+this.name+"\n");
		sb.append("Fields:\n");

		for (int i = 0; i < this.fields.size(); i++) {
			Field field = this.fields.get(i);
			sb.append("\tField name: "+field.getName()+"\n");
			sb.append("\tField type: "+field.getType()+"\n");
			sb.append("\tField size: "+field.getSize()+"\n\n");
		}
		
		return sb.toString();
	}
}
