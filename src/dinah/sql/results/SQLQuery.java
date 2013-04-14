package dinah.sql.results;

import dinah.sql.*;
import java.util.*;

public class SQLQuery extends SQLResult {

	private ArrayList<String> fieldValues;
	private ArrayList<SQLClause> clauses;
	private long cursor;
	
	public SQLQuery(String message) {
		super(message);
		this.fieldValues = new ArrayList<String>();
		long cursor = -1;
	}

	public ArrayList<String> getFieldValues() {
		return this.fieldValues;
	}

	public void setCursor(long cursor) {
		this.cursor = cursor;
	}

	public long getCursor() {
		return this.cursor;
	}

	public boolean hasNext() {
		if (this.cursor != -1) 
			return true;
		return false;
	}

	public void setClauses(ArrayList<SQLClause> clauses) {
		this.clauses = clauses;
	}

	public ArrayList<SQLClause> getClauses() {
		return this.clauses;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Number of fieldValues: "+this.fieldValues+"\n");
		sb.append("Cursor is at position: "+this.cursor+"\n");

		for (int i = 0; i < this.fieldValues.size(); i++) {
			sb.append("\t"+this.fieldValues.get(i)+"\n");
		}

		return sb.toString();
	}
}
