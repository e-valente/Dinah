package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class DropIndex extends SQLCommand {

	public DropIndex(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher dropIndex = Pattern.compile("[dD][rR][oO][pP][\\s\\t]+[iI][nN][dD][eE][xX][\\s\\t]+(\\w+);").matcher(command);

		if (dropIndex.find()) {
		    System.out.println(dropIndex.group(1));

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
