package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class Insert extends SQLCommand {

	public Insert(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher insert = Pattern.compile("[iI][nN][sS][eE][rR][tT][\\s\\t]+[iI][nN][tT][oO][\\s\\t]+(\\w+)[\\s\\t]+\\(([^\\)]*)\\)[\\s\\t]+[vV][aA][lL][uU][eE][sS][\\s\\t]+\\(([^\\)]*)\\)[\\s\\t]*;").matcher(command);

		if (insert.find()) {
		    // TABLENAME
		    System.out.println("Tablename: "+insert.group(1));

		    // IDENTIFYING INDIVIDUAL COLUMNS
		    Matcher columns = Pattern.compile("(\\w+),{0,1}").matcher(insert.group(2));

		    if (columns.find()) {
			columns.reset();
			// COLUMN NAMES
			while (columns.find()) {
				System.out.println("\t"+columns.group(1));
			}
		    }

		    // TODO: PAREI AQUI (usar somente se values.group(1).length() > 0
		    Matcher values = Pattern.compile("([^,\\']+)[\\s\\t]*[,]{0,1}[\\s\\t]*").matcher(insert.group(3));
		    
		    // VALUES CLAUSES
		    while (values.find()) {
			System.out.println("\t'"+values.group(1)+"'"); // COLUMN
		    }

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
