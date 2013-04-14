package dinah.core;

public class Field {
	private String name;
	private String type;
	private int size;
	private boolean notnull;
	private boolean unique;
	private boolean autoincrement;

	public Field() {
		this.name = null;
		this.type = null;
		this.size = 0;
		this.notnull = false;
		this.unique = false;
		this.autoincrement = false;
	}

	public Field(String name, String type, int size,
			boolean notnull,
			boolean unique,
			boolean autoincrement) {
		this.setName(name);
		this.setType(type);
		this.setSize(size);
		this.setNotNull(notnull);
		this.setUnique(unique);
		this.setAutoIncrement(autoincrement);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setNotNull(boolean notnull) {
		this.notnull = notnull;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public void setAutoIncrement(boolean autoincrement) {
		this.autoincrement = autoincrement;
	}

	public String getName() { return this.name; }
	public String getType() { return this.type; }
	public int getSize() { return this.size; }
	public boolean getNotNull() { return this.notnull; }
	public boolean getUnique() { return this.unique; }
	public boolean getAutoIncrement() { return this.autoincrement; }

	public boolean validate(String fieldValue) {

		if (this.type.equals("int")) {
			Integer.parseInt(fieldValue);
		} else if (this.type.equals("double")) {
			Double.parseDouble(fieldValue);
		}
		
		return true;
	}

}
