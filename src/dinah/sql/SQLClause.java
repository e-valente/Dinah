package dinah.sql;

public class SQLClause {
	private String fieldName;
	private String operation;
	private String fieldValue;

	public SQLClause(String fieldName, String operation, String fieldValue) {
		this.setFieldName(fieldName);
		this.setOperation(operation);
		this.setFieldValue(fieldValue);
	}

	public void setFieldName(String fieldName) { this.fieldName = fieldName; }
	public void setOperation(String operation) { this.operation = operation; }
	public void setFieldValue(String fieldValue) { this.fieldValue = fieldValue; }

	public String getFieldName() { return this.fieldName; }
	public String getOperation() { return this.operation; }
	public String getFieldValue() { return this.fieldValue; }
}
