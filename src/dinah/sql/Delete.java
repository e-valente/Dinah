package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class Delete extends SQLCommand {

	public Delete(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher delete = Pattern.compile("[dD][eE][lL][eE][tT][eE][\\s\\t]+[fF][rR][oO][mM](.*);").matcher(command);

		if (delete.find()) {

		    Matcher where = Pattern.compile("[\\s\\t]+(\\w+)[\\s\\t]+[wW][hH][eE][rR][eE][\\s\\t]+(.*)").matcher(delete.group(1));

		    if (where.find()) {
			// TABLENAME
			System.out.println("\tTablename: "+where.group(1));
			System.out.println("\tWhere: "+where.group(2));
			Matcher whereClause = Pattern.compile("(\\w+)[\\s\\t]*([^\\s\\t'\"])[\\s\\t]*(['\"]{0,1}\\w+['\"]{0,1})[\\s\\t]*([aA][nN][dD]){0,1}\\.*").matcher(where.group(2));
			
			// WHERE CLAUSES
			while (whereClause.find()) {
				System.out.println("\t"+whereClause.group(1)); // COLUMN
				System.out.println("\t"+whereClause.group(2)); // OPERATION
				System.out.println("\t"+whereClause.group(3)); // VALUE
			}

		    } else {
			// TABLENAME
			System.out.println("\tTablename: "+delete.group(4));
		    }

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
