package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class ShowAll extends SQLCommand {

	public ShowAll(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher showAll = Pattern.compile("[sS][hH][oO][wW][\\s\\t]+[aA][lL][lL][\\s\\t]*;").matcher(command);

		if (showAll.find()) {
			return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
