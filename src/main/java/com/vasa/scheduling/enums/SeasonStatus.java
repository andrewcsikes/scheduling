package com.vasa.scheduling.enums;

import java.util.Map;

public enum SeasonStatus implements CodeAndDisplayNameEnum<Integer>{

	ACTIVE(1,"Active"),
	INACTIVE(2,"Inactive");

	private final Integer code;
	private final String displayName;

	private SeasonStatus(Integer code, String displayName) {
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
		return EnumFactory.getEnumDisplayNames(SeasonStatus.class);
	}

	public static Map<String, String> getDisplayNamesWithValue() {
		return EnumFactory.getNameDisplayNameMap(SeasonStatus.class);
	}

	public static SeasonStatus toEnumFromCode(Integer code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode( SeasonStatus.class, code);
	}

	public static SeasonStatus toEnumFromDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()){
			return null;
		}
		return EnumFactory.toEnumFromDisplayName(SeasonStatus.class, displayName);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }
  
}
