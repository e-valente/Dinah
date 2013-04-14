package dinah.core;

public class Relation {
	protected Database database;

	public Relation(Database database) {
		this.setDatabase(database);
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Database getDatabase() {
		return this.database;
	}
}
