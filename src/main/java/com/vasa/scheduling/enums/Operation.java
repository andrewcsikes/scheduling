package com.vasa.scheduling.enums;

import java.util.Map;

public enum Operation implements CodeAndDisplayNameEnum<Integer>{

	BEFORE(1,"Before"),
	AFTER(2,"After");

	private final Integer code;
	private final String displayName;

	private Operation(Integer code, String displayName) {
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
		return EnumFactory.getEnumDisplayNames(Operation.class);
	}

	public static Map<String, String> getDisplayNamesWithValue() {
		return EnumFactory.getNameDisplayNameMap(Operation.class);
	}

	public static Operation toEnumFromCode(Integer code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode( Operation.class, code);
	}

	public static Operation toEnumFromDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()){
			return null;
		}
		return EnumFactory.toEnumFromDisplayName(Operation.class, displayName);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }
  
}
