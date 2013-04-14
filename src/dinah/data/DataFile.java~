package dinah.data;

import dinah.sql.*;
import dinah.sql.results.*;
import dinah.core.*;

import java.io.*;
import java.nio.*;
import java.util.*;
import java.nio.channels.*;
import java.util.concurrent.atomic.*;

public class DataFile extends DataRelation {
	private Table table;

	private final int ENDOFFILE = 0;
	private final int FALSECLAUSES = 1;
	private final int TRUECLAUSES = 2;

	public DataFile(Table table) {
		this.table = table;
	}

	public byte[] defineSize(byte _value[], int size) {
		byte value[] = new byte[size];

		for (int i = 0; i < _value.length && i < size; i++) {
			value[i] = _value[i];
		}

		return value;
	}

	public void append(ArrayList<String> data) 
			throws Exception {

		String dataFileName = this.table.getFilename();
            	FileOutputStream fos = 
			new FileOutputStream(dataFileName, true);
            	FileChannel fileChannel = fos.getChannel();

            	ByteBuffer byteBuffer = 
			ByteBuffer.allocate(this.table.getSize());

		// TODO montar buffer aqui no meio
		for (int i = 0; i < this.table.getFields().size(); i++) {
			Field field = this.table.getFields().get(i);

			if (field.getType().equals("int")) {
				//System.out.println("int");
				byteBuffer = 
				  byteBuffer.putInt(Integer.parseInt(data.get(i)));
			} else if (field.getType().equals("double")) {
				//System.out.println("double");
				byteBuffer = 
				  byteBuffer.putDouble(Double.parseDouble(data.get(i)));
			} else if (field.getType().equals("char")) {
				//System.out.println("char");
				byteBuffer = 
				  byteBuffer.put(
					defineSize(
						data.get(i).getBytes(), field.getSize()
						)
					);
			}
		}

		byteBuffer.rewind();
		//System.out.println(byteBuffer.toString());
		//System.out.println("Offset: "+fileChannel.position());
		int bytes = fileChannel.write(byteBuffer);
		//System.out.println("Gravamos "+bytes+" bytes");

            	fos.close();
	}

	public ByteBuffer read(long recordOffset, AtomicReference<Integer> bytesRead) 
			throws Exception {
	
		String dataFileName = this.table.getFilename();
            	FileInputStream fis = 
			new FileInputStream(dataFileName);
            	FileChannel fileChannel = fis.getChannel().position(recordOffset);

            	ByteBuffer byteBuffer = 
			ByteBuffer.allocate(this.table.getSize());
		bytesRead.set(fileChannel.read(byteBuffer));
		byteBuffer.rewind();

            	fis.close();
	
		return byteBuffer;
	}


	/**
	 * return ENDOFFILE=0 means end of file
	 * return FALSECLAUSES=1 means clauses are false
	 * return TRUECLAUSES=2 means clauses are true
	 */
	private int testClauses(ByteBuffer record, 
			AtomicReference<Integer> bytesRead,
			ArrayList<SQLClause> clauses) {

		if (bytesRead.get() == -1) {
			return 0;
		}

		// para cada clausula (considerando AND somente)
		boolean validClause = true;

		for (int j = 0; j < clauses.size(); j++) {
		      SQLClause clause = clauses.get(j);

		      int bufferFieldOffset = 0;
		      for (int i = 0; i < this.table.getFields().size(); i++) {
		      	Field field = this.table.getFields().get(i);

			/*
		      	System.out.println("=====================");
		      	System.out.println(field.getName());
		      	System.out.println(clause.getFieldName());
		      	System.out.println("=====================");
			*/

		      	if (field.getName().equals(clause.getFieldName())) {

		      		//System.out.println("Iguais");

		      		if (field.getType().equals("int")) {
		      			int data = record.getInt();

					/*
		      			System.out.println(Integer.parseInt(
		      			  clause.getFieldValue()));
		      			System.out.println(data);
					*/

		      			if (Integer.parseInt(
		      			  clause.getFieldValue()) != data) {
		      				validClause = false;
		      			}
		      		} else if (field.getType().equals("double")) {
		      			double data = record.getDouble();
		      			if (Double.parseDouble(
		      			  clause.getFieldValue()) != data) {
		      				validClause = false;
		      			}
		      		} else if (field.getType().equals("char")) {
		      			byte data[] = new byte[field.getSize()];
		      			record.get(data, 0, field.getSize());

		      			if (!clause.getFieldValue().equals(
		      			  new String(data))) {
		      				validClause = false;
		      			}
		      		}
		      	} else {
		      		// just to make sure we skip the necessary
		      		// 	amount of bytes in the buffer
		      		// 	to read the correct fieldvalue
		      		// 	later
		      		byte tmp[] = new byte[field.getSize()];
		      		record.get(tmp, 0, field.getSize());
		      	}

		      	bufferFieldOffset += field.getSize();
		      }
		}

		if (validClause) {
			return 2;
		} else {
			return 1;
		}
	}

	private SQLQuery createSQLQuery(ByteBuffer record, long cursor,	
			ArrayList<SQLClause> clauses) {

		SQLQuery sqlQuery = new SQLQuery("QUERY");
		sqlQuery.setClauses(clauses);
		sqlQuery.setCursor(cursor);

		// para cada registro
		for (int i = 0; i < this.table.getFields().size(); i++) {
			Field field = this.table.getFields().get(i);

			if (field.getType().equals("int")) {
				sqlQuery.getFieldValues().add(
				  (new Integer(record.getInt())).toString());
			} else if (field.getType().equals("double")) {
				sqlQuery.getFieldValues().add(
				  (new Double(record.getDouble())).toString());
			} else if (field.getType().equals("char")) {
				byte buffer[] = new byte[field.getSize()];
				record.get(buffer, 0, field.getSize());
				sqlQuery.getFieldValues().add(new String(buffer));
			}
		}

		sqlQuery.setCursor(sqlQuery.getCursor() + this.table.getSize());

		return sqlQuery;
	}

	// Busca Sequencial
	public SQLQuery search(ArrayList<SQLClause> clauses) 
				throws Exception {

		ByteBuffer record = null;
		long recordOffset = 0;
		AtomicReference<Integer> bytesRead = new AtomicReference<Integer>(-1);
		SQLQuery sqlQuery = new SQLQuery("QUERY");
		int result = -1;

		sqlQuery.setClauses(clauses);

		do {
			record = read(recordOffset, bytesRead);
			result = this.testClauses(record, bytesRead, clauses);
			recordOffset += this.table.getSize();
			sqlQuery.setCursor(recordOffset);
		} while (result == FALSECLAUSES);

		if (result == ENDOFFILE) {
			sqlQuery.setCursor(-1);
			return sqlQuery;
		}

		// getting back at the beginning of this buffer
		if (result == TRUECLAUSES) {
			record.rewind();
			sqlQuery = this.createSQLQuery(record, sqlQuery.getCursor(),
					clauses);
		}

		return sqlQuery;
	}

	// Busca Sequencial
	public SQLQuery search(SQLQuery sqlQuery) 
			throws Exception {
		ByteBuffer record = null;
		long recordOffset = sqlQuery.getCursor();
		AtomicReference<Integer> bytesRead = new AtomicReference<Integer>(-1);
		int result = -1;

		if (!sqlQuery.hasNext()) {
			sqlQuery.setCursor(-1);
			return sqlQuery;
		}

		do {
			//System.out.println("DataFile: recordOffset: "+recordOffset);
			record = read(recordOffset, bytesRead);
			//System.out.println("DataFile: bytesRead: "+bytesRead.get());
			result = this.testClauses(record, bytesRead, sqlQuery.getClauses());
			recordOffset += this.table.getSize();
		} while (result == FALSECLAUSES);

		sqlQuery.setCursor(recordOffset);

		if (result == ENDOFFILE) {
			sqlQuery.setCursor(-1);
			return sqlQuery;
		}

		// getting back at the beginning of this buffer
		if (result == TRUECLAUSES) {
			record.rewind();
			sqlQuery = this.createSQLQuery(record, sqlQuery.getCursor(),
					sqlQuery.getClauses());
		}

		return sqlQuery;
	}

	/**
	 * Este método deve marcar um registro no arquivo de dados como removido.
	 * Lembre-se de marcá-lo como removido também nos arquivos de índice.
	 */
	public SQLResult remove(ArrayList<SQLClause> whereClauses) {
		return null;
	}

	/**
	 * Este método deve produzir um novo arquivo de dados sem aqueles registros
	 * marcados como removidos. Além disso, deve-se regerar os arquivos de índice.
	 */
	public SQLResult defrag() {
		return null;
	}

	public static void main(String args[]) throws Exception {
		Database database = new Database("/tmp", "myDB");
		Table t = new Table(database, "file");

		t.getFields().add(new Field("code", "int", 4, false, false, false));
		t.getFields().add(new Field("name", "char", 80, false, false, false));
		t.getFields().add(new Field("address", "char", 80, false, false, false));

		DataFile df = new DataFile(t);

		ArrayList<String> data = new ArrayList<String>();
		data.add("0");
		data.add("1");
		data.add("Joao da Silva");
		data.add("Av Sao Carlos, 123");
		df.append(data);

		ArrayList<SQLClause> clauses = new ArrayList<SQLClause>();
		clauses.add(new SQLClause("code", "=", "1"));

		SQLQuery sqlQuery = df.search(clauses);
		while (sqlQuery.hasNext()) {
			System.out.println(sqlQuery);
			sqlQuery = df.search(sqlQuery);
		}

		/*
		AtomicReference<Integer> bytesRead = new AtomicReference<Integer>(-1);
		ByteBuffer record = df.read(0, bytesRead);

		byte _name[] = new byte[80];
		byte _address[] = new byte[80];

		int deleted = record.getInt();
		int code = record.getInt();
		record.get(_name, 0, 80);
		record.get(_address, 0, 80);

		System.out.println("\n\nBytes Lidos: "+bytesRead.get());
		System.out.println("Registro foi removido? "+deleted);
		System.out.println("Codigo: "+code);
		System.out.println("Nome: "+(new String(_name)));
		System.out.println("Endereco: "+(new String(_address)));*/
	}
}
