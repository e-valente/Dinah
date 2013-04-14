package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class ConnectTo extends SQLCommand {

	public ConnectTo(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher connectTo = Pattern.compile("[cC][oO][nN][nN][eE][cC][tT][\\s\\t]+[tT][oO][\\s\\t]+(\\w+);").matcher(command);

		if (connectTo.find()) {
		    System.out.println(connectTo.group(1));
		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
