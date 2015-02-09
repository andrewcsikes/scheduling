package com.vasa.scheduling.enums;

import java.util.Map;

public enum Status implements CodeAndDisplayNameEnum<Integer>{

	ACTIVE(1,"Active"),
	INACTIVE(2,"Inactive"),
	REQUEST(3,"Request");

	private final Integer code;
	private final String displayName;

	private Status(Integer code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	public Integer getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static Map<?, String> getDisplayNames() {
		return EnumFactory.getEnumDisplayNames(Status.class);
	}

	public static Map<String, String> getDisplayNamesWithValue() {
		return EnumFactory.getNameDisplayNameMap(Status.class);
	}

	public static Status toEnumFromCode(Integer code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode( Status.class, code);
	}

	public static Status toEnumFromDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()){
			return null;
		}
		return EnumFactory.toEnumFromDisplayName(Status.class, displayName);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }
  
}
