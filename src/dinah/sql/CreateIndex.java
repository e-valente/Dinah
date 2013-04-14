package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class CreateIndex extends SQLCommand {

	public CreateIndex(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher createIndex = Pattern.compile("[cC][rR][eE][aA][tT][eE][\\s\\t]+[iI][nN][dD][eE][xX][\\s\\t]+(\\w+)[\\s\\t]+[oO][nN][\\s\\t]+(\\w+)[\\s\\t]*(.*);").matcher(command);

		if (createIndex.find()) {
		    System.out.println(createIndex.group(1));
		    System.out.println(createIndex.group(2));

		    Matcher fields = Pattern.compile("(\\w+)").matcher(createIndex.group(3));
		    while (fields.find()) {
			System.out.println("\t"+fields.group(1));
		    }

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
