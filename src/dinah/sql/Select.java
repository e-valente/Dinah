package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class Select extends SQLCommand {

	public Select(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher select = Pattern.compile("([sS][eE][lL][eE][cC][tT])(.*)([fF][rR][oO][mM])(.*);").matcher(command);

		if (select.find()) {

		    // IDENTIFYING INDIVIDUAL COLUMNS
		    Matcher columns = Pattern.compile("(\\w+),{0,1}").matcher(select.group(2));
		    Matcher all = Pattern.compile("^[\\s\\t]*(\\*)[\\s\\t]*$").matcher(select.group(2));

		    if (columns.find()) {
			columns.reset();
			// COLUMN NAMES
			while (columns.find()) {
				System.out.println("\t"+columns.group(1));
			}
		    } else if (all.find()) {
			// ALL COLUMNS
			System.out.println("\t'"+all.group(1)+"'");
		    }

		    Matcher where = Pattern.compile("(.*)[wW][hH][eE][rR][eE](.*)").matcher(select.group(4));
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
			System.out.println("\tTablename: "+select.group(4));
		    }

		    return new SQLQuery("QUERY");
		}

		return new SQLFailed("FAILED");
	}
}
