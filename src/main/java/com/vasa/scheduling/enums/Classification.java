package com.vasa.scheduling.enums;

import java.util.Map;

public enum Classification implements CodeAndDisplayNameEnum<Integer>{

	VASA(1,"VASA"),
	NON_VASA(2,"NON-VASA");

	private final Integer code;
	private final String displayName;

	private Classification(Integer code, String displayName) {
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
		return EnumFactory.getEnumDisplayNames(Classification.class);
	}

	public static Map<String, String> getDisplayNamesWithValue() {
		return EnumFactory.getNameDisplayNameMap(Classification.class);
	}

	public static Classification toEnumFromCode(Integer code) {
		if (code == null)
		{
			return null;
		}
		return EnumFactory.toEnumFromCode(Classification.class, code);
	}

	public static Classification toEnumFromDisplayName(String displayName) {
		if (displayName == null || displayName.isEmpty()){
			return null;
		}
		return EnumFactory.toEnumFromDisplayName(Classification.class, displayName);
	}

  /**
   * Convert enum value to a string
   */
  public String toString() 
  {
    return code.toString();
  }
}
