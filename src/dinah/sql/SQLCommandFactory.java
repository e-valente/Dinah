package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.*;
import dinah.sql.results.*;

public class SQLCommandFactory {

	/**
	 * Identificar o comando que o usuario deseja executar.
	 * Em seguida, faça verificação do comando.
	 */
	public static SQLCommand build(Dinah dinah, Database database, String cmd) {
		cmd = cmd.trim();
		Matcher exit = Pattern.compile("[eE][xX][iI][tT][\\s\\t]*;").matcher(cmd);
		Matcher quit = Pattern.compile("[qQ][uU][iI][tT][\\s\\t]*;").matcher(cmd);
		Matcher select = Pattern.compile("[sS][eE][lL][eE][cC][tT].*").matcher(cmd);
		Matcher insert = Pattern.compile("[iI][nN][sS][eE][rR][tT].*").matcher(cmd);
		Matcher delete = Pattern.compile("[dD][eE][lL][eE][tT][eE][\\s\\t]+[fF][rR][oO][mM].*").matcher(cmd);
		Matcher update = Pattern.compile("[uU][pP][dD][aA][tT][eE].*").matcher(cmd);
		Matcher createDatabase = Pattern.compile("[cC][rR][eE][aA][tT][eE][\\s\\t]+[dD][aA][tT][aA][bB][aA][sS][eE].*").matcher(cmd);
		Matcher connectTo = Pattern.compile("[cC][oO][nN][nN][eE][cC][tT][\\s\\t]+[tT][oO].*").matcher(cmd);
		Matcher createIndex = Pattern.compile("[cC][rR][eE][aA][tT][eE][\\s\\t]+[iI][nN][dD][eE][xX].*").matcher(cmd);
		Matcher createTable = Pattern.compile("[cC][rR][eE][aA][tT][eE][\\s\\t]+[tT][aA][bB][lL][eE].*").matcher(cmd);
		Matcher createUser = Pattern.compile("[cC][rR][eE][aA][tT][eE][\\s\\t]+[uU][sS][eE][rR].*").matcher(cmd);
		Matcher dropDatabase = Pattern.compile("[dD][rR][oO][pP][\\s\\t]+[dD][aA][tT][aA][bB][aA][sS][eE].*").matcher(cmd);
		Matcher dropIndex = Pattern.compile("[dD][rR][oO][pP][\\s\\t]+[iI][nN][dD][eE][xX].*").matcher(cmd);
		Matcher dropTable = Pattern.compile("[dD][rR][oO][pP][\\s\\t]+[tT][aA][bB][lL][eE].*").matcher(cmd);
		Matcher dropUser = Pattern.compile("[dD][rR][oO][pP][\\s\\t]+[uU][sS][eE][rR].*").matcher(cmd);
		Matcher grant = Pattern.compile("[gG][rR][aA][nN][tT].*").matcher(cmd);
		Matcher showAll = Pattern.compile("[sS][hH][oO][wW][\\s\\t]+[aA][lL][lL].*").matcher(cmd);

		if (select.find()) {
			return new Select(dinah, database, cmd);
		} else if (insert.find()) {
			return new Insert(dinah, database, cmd);
		} else if (delete.find()) {
			return new Delete(dinah, database, cmd);
		} else if (update.find()) {
			return new Update(dinah, database, cmd);
		} else if (createDatabase.find()) {
			return new CreateDatabase(dinah, database, cmd);
		} else if (connectTo.find()) {
			return new ConnectTo(dinah, database, cmd);
		} else if (createIndex.find()) {
			return new CreateIndex(dinah, database, cmd);
		} else if (createTable.find()) {
			return new CreateTable(dinah, database, cmd);
		} else if (createUser.find()) {
			return new CreateUser(dinah, database, cmd);
		} else if (dropDatabase.find()) {
			return new DropDatabase(dinah, database, cmd);
		} else if (dropIndex.find()) {
			return new DropIndex(dinah, database, cmd);
		} else if (dropTable.find()) {
			return new DropTable(dinah, database, cmd);
		} else if (dropUser.find()) {
			return new DropUser(dinah, database, cmd);
		} else if (grant.find()) {
			return new Grant(dinah, database, cmd);
		} else if (showAll.find()) {
			return new ShowAll(dinah, database, cmd);
		} else if (quit.find()) {
			return new Exit(dinah, database, cmd);
		} else if (exit.find()) {
			return new Exit(dinah, database, cmd);
		}

		return null;
	}
}
