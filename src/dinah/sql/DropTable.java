package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class DropTable extends SQLCommand {

	public DropTable(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher dropTable = Pattern.compile("[dD][rR][oO][pP][\\s\\t]+[tT][aA][bB][lL][eE][\\s\\t]+(\\w+);").matcher(command);

		if (dropTable.find()) {
		    System.out.println(dropTable.group(1));

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
