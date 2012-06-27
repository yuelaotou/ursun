package cn.ursun.console.app.domain;

public enum Sex {
	UNKNOWN("", "U"), MALE("男", "M"), FEMALE("女", "F");
	private final String value;

	private final String label;

	private Sex(String label, String value) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static Sex parse(String s) {
		if (s == null)
			return UNKNOWN;
		if ("M".equals(s)) {
			return MALE;
		} else if ("F".equals(s)) {
			return FEMALE;
		} else {
			return UNKNOWN;
		}
	}
}
