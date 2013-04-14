package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class CreateUser extends SQLCommand {

	public CreateUser(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher createUser = Pattern.compile("[cC][rR][eE][aA][tT][eE][\\s\\t]+[uU][sS][eE][rR][\\s\\t]+(\\w+)[\\s\\t]+[pP][aA][sS][sS][wW][oO][rR][dD][\\s\\t]+[\\'\"](.+)[\\'\"];").matcher(command);

		if (createUser.find()) {
		    System.out.println(createUser.group(1));
		    System.out.println(createUser.group(2));

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
