package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class Update extends SQLCommand {

	public Update(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher update = Pattern.compile("[uU][pP][dD][aA][tT][eE][\\s\\t]+([^\\s]+)[\\s\\t]+[sS][eE][tT][\\s\\t]+(.*)[\\s\\t]+[wW][hH][eE][rR][eE][\\s\\t]+(.*)[\\s\\t]*;").matcher(command);

		if (update.find()) {
		    
		    // TABLENAME
		    System.out.println("\tTablename: "+update.group(1));
		    System.out.println("\tFields: "+update.group(2));
		    System.out.println("\tWhere: "+update.group(3));

		    // FIELDS
		    Matcher fields = Pattern.compile("(\\w+)[\\s\\t]*=[\\s\\t]*(['\"]{0,1}\\w+['\"]{0,1}),{0,1}").matcher(update.group(2));
		    
		    while (fields.find()) {
			System.out.println("\t"+fields.group(1));
			System.out.println("\t"+fields.group(2));
		    }
		    
		    Matcher whereClause = Pattern.compile("(\\w+)[\\s\\t]*([^\\s\\t'\"])[\\s\\t]*(['\"]{0,1}\\w+['\"]{0,1})[\\s\\t]*([aA][nN][dD]){0,1}\\.*").matcher(update.group(3));
		    
		    // WHERE CLAUSES
		    while (whereClause.find()) {
			System.out.println("\t"+whereClause.group(1)); // COLUMN
			System.out.println("\t"+whereClause.group(2)); // OPERATION
			System.out.println("\t"+whereClause.group(3)); // VALUE
		    }

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
