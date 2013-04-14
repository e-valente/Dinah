package dinah.util;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import dinah.core.*;
import dinah.sql.*;
import dinah.sql.results.*;

public class Shell {
	private Dinah dinah;
	private Database database;
	private PrintWriter printWriter;
	private StringBuffer commandBuffer;

	public Shell(Dinah dinah, String databaseName) 
			throws Exception {
		this.printWriter = null;
		this.dinah = dinah;
		
		this.database = new Database(
			this.dinah.getDataDirectory(), 
			databaseName);
		this.commandBuffer = new StringBuffer();
	}

	/**
	 * A quebra individual de cada comando e invocando 
	 * o processamento desse comando.
	 */
	public void prompt(InputStream is, OutputStream os) 
			throws Exception {
		this.printWriter = new PrintWriter(os, true);
		Scanner keyboard = new Scanner(is);

		while (!(processCommand(os, keyboard.next()) instanceof SQLExit)) {}
	}

	/**
	 * Quebra cada comando individual e manda executar.
	 */
	private SQLResult processCommand(OutputStream os, String cmd) 
			throws Exception {
		SQLResult sqlResult = null;
		int position = cmd.indexOf(';');

		if (position == -1) {
			this.commandBuffer.append(" "+cmd);
			return new SQLOk("OK");
		} else {
			this.commandBuffer.append(" "+cmd.substring(0, position+1));

			String cmdToExecute = this.commandBuffer.toString().trim();
			cmdToExecute = cmdToExecute.replace('\r', ' ');
			cmdToExecute = cmdToExecute.replace('\n', ' ');

			// processando o comando
			SQLCommand sqlCommand = 
				SQLCommandFactory.build( this.dinah,
					this.database, cmdToExecute);
			sqlResult = sqlCommand.process();
			
			this.printWriter.println(sqlResult);

			if (cmd.length() > position+2) {
				this.commandBuffer = 
					new StringBuffer(cmd.substring(position+2));
			} else {
				this.commandBuffer = new StringBuffer();
			}
		}

		return sqlResult;
	}
}
