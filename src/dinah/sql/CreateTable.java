package dinah.sql;

import java.util.regex.*;
import dinah.core.*;
import dinah.sql.results.*;

public class CreateTable extends SQLCommand {

	public CreateTable(Dinah dinah, Database database, String command) {
		super(dinah, database, command);
	}

	public SQLResult process() throws Exception {
		
		Matcher createTable = Pattern.compile("[cC][rR][eE][aA][tT][eE][\\s\\t]+[tT][aA][bB][lL][eE][\\s\\t]+(\\w+)[\\s\\t]+\\([\\s\\t]*(.*)[\\s\\t]*;").matcher(command);

		if (createTable.find()) {

		    // TABLENAME
		    String tableName = createTable.group(1);
		    Table table = new Table(this.database, tableName);
		    
		    // IDENTIFYING INDIVIDUAL COLUMNS
		    Matcher columns = Pattern.compile("([^,\\)]+)").matcher(createTable.group(2));

		    if (columns.find()) {
			columns.reset();
			// FOR EVERY COLUMN
			while (columns.find()) {
				System.out.println("\t"+columns.group(1));
				
				Matcher fieldInfo = Pattern.compile("[\\s\\t]*(\\w+)[\\s\\t]+([^\\s]+)[\\s\\t]*(.*)").matcher(columns.group(1));

				System.out.println("\tCreateTable.java: Fields");

				while (fieldInfo.find()) {
					System.out.println("\t\t"+fieldInfo.group(1));
					System.out.println("\t\t"+fieldInfo.group(2));
					System.out.println("\t\t"+fieldInfo.group(3));
				

					String fieldname = fieldInfo.group(1);
					String fieldtype = null;
					int fieldsize = 0;

					if (fieldInfo.group(2).equals("int")) {
						fieldtype = fieldInfo.group(2);
						fieldsize = 4;
					} else if (fieldInfo.group(2).equals("double")) {
						fieldtype = fieldInfo.group(2);
						fieldsize = 8; // FIXME: Certo?
					} else {
						Matcher charInfo = Pattern.compile(
							"[\\s\\t]*(\\w+)\\[{0,1}([0-9]*)\\]{0,1}.*").matcher(
							fieldInfo.group(2));

						if (charInfo.find()) {

							System.out.println("CharInfo: "+
									charInfo.group(1)+
									" "+charInfo.group(2));

							fieldtype = charInfo.group(1);
							if (charInfo.group(2).trim().length()
									> 0) {
								fieldsize = Integer.parseInt(
									charInfo.group(2));
							} else {
								fieldsize = 1;
							}
						}
					}

					table.getFields().add(new Field(fieldname, 
							fieldtype, fieldsize,
							false, false, false));
				}
			}
		    }

		    System.out.println(table);

		    this.database.getRelationList().add(table);

		    return new SQLOk("OK");
		}

		return new SQLFailed("FAILED");
	}
}
