package com.vasa.scheduling.enums;

import java.util.Map;

public enum DayOfWeek implements CodeAndDisplayNameEnum<Integer>{

	SUNDAY(1,"Sunday"),
	MONDAY(2,"Monday"),
	TUESDAY(3,"Tuesday"),
	WEDNESDAY(4,"Wednesday"),
	THURSDAY(5,"Thursday"),
	FRIDAY(6,"Friday"),
	SATURDAY(7,"Saturday");

	private final Integer code;
	private final String displayName;

	private DayOfWeek(Integer code, String displayName) {
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
		return EnumFactory.getEnumDisplayNames(DayOfWeek.class);
	}

	public static Map<String, String> getDisplayNamesWithValue() {
		return EnumFactory.getNameDisplayNameMap(DayOfWeek.class);
	}

	public static DayOfWeek toEnumFromCode(Integer code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode( DayOfWeek.class, code);
	}

	public static DayOfWeek toEnumFromDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()){
			return null;
		}
		return EnumFactory.toEnumFromDisplayName(DayOfWeek.class, displayName);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }
  
}
