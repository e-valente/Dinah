package dinah.sql.results;

public class SQLResult {
	private String message;

	public SQLResult(String message) {
		this.message = message;
	}

	public String toString() {
		return this.message;
	}
}
