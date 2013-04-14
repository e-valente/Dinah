package dinah.sql;

import dinah.core.*;
import dinah.sql.results.*;

public abstract class SQLCommand {
	protected Database database;
	protected String command;
	protected Dinah dinah;

	public SQLCommand(Dinah dinah, Database database, String command) {
		this.dinah = dinah;
		this.database = database;
		this.command = command;
	}

	public abstract SQLResult process() throws Exception;
}
