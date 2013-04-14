package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class Grant extends SQLCommand {

	public Grant(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher grant = Pattern.compile("[gG][rR][aA][nN][tT][\\s\\t]+(\\w+)[oO][nN][\\s\\t]+(\\w+)[\\s\\t]+(\\w+)[\\s\\t]+;").matcher(command);

		if (grant.find()) {
		    System.out.println(grant.group(1));
		    System.out.println(grant.group(2));
		    System.out.println(grant.group(3));

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
