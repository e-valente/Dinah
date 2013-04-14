package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class CreateDatabase extends SQLCommand {

	public CreateDatabase(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {

		Matcher createDatabase = Pattern.compile("[cC][rR][eE][aA][tT][eE][\\s\\t]+[dD][aA][tT][aA][bB][aA][sS][eE][\\s\\t]+(\\w+);").matcher(command);

		if (createDatabase.find()) {
		    String dbName = createDatabase.group(1);

		    // Adicionando Database na lista da Dinah
		    this.dinah.getDatabaseList().add(
			new Database(this.dinah.getDataDirectory(), 
				dbName));

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
