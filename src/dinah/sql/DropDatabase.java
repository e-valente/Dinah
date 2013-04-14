package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class DropDatabase extends SQLCommand {

	public DropDatabase(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher dropDatabase = Pattern.compile("[dD][rR][oO][pP][\\s\\t]+[dD][aA][tT][aA][bB][aA][sS][eE][\\s\\t]+(\\w+);").matcher(command);

		if (dropDatabase.find()) {
		    System.out.println(dropDatabase.group(1));

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
