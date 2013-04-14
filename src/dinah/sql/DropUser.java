package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class DropUser extends SQLCommand {

	public DropUser(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher dropUser = Pattern.compile("[dD][rR][oO][pP][\\s\\t]+[uU][sS][eE][rR][\\s\\t]+(\\w+);").matcher(command);

		if (dropUser.find()) {
		    System.out.println(dropUser.group(1));

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
