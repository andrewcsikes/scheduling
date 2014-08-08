package com.vasa.scheduling.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnumFactory {

	public static boolean isEnum(Class clazz) {
		return CodeAndDisplayNameEnum.class.isAssignableFrom(clazz);
	}

	public static <T extends Enum> T toEnum(Class<T> clazz, String value) {
		assert value != null && !value.trim().isEmpty();
		try {
			Enum e = Enum.valueOf(clazz, value);
			return (T) e;
		} catch (IllegalArgumentException iae) {
			return null;
		}
	}

	public static <T extends CodeAndDisplayNameEnum<String>> T toEnumFromCode(Class<T> clazz, String code) {
		assert code != null;
		for (CodeAndDisplayNameEnum e : (CodeAndDisplayNameEnum[])clazz.getEnumConstants()) {
			if (code.equals(e.getCode().toString())) {
				return (T) e;
			}
		}
		return null;
	}

	public static <T extends CodeAndDisplayNameEnum<Integer>> T toEnumFromCode(Class<T> clazz, Integer code) {
		assert code != null;
		for (CodeAndDisplayNameEnum e : (CodeAndDisplayNameEnum[])clazz.getEnumConstants()) {
			if (code.equals(e.getCode())) {
				return (T) e;
			}
		}
		return null;
	}

	public static <T extends CodeAndDisplayNameEnum> T toEnumFromDisplayName(Class<T> clazz, String displayName) {
		assert displayName != null;
		for (CodeAndDisplayNameEnum e : (CodeAndDisplayNameEnum[])clazz.getEnumConstants()) {
			if (displayName.equals(e.getDisplayName())) {
				return (T) e;
			}
		}
		return null;
	}

	public static Map<?, String> getEnumDisplayNames(Class<? extends CodeAndDisplayNameEnum> clazz) {
		Map map = new LinkedHashMap();
		for (CodeAndDisplayNameEnum e : (CodeAndDisplayNameEnum[])clazz.getEnumConstants()) {
			map.put(e.getCode(), e.getDisplayName());
		}
		return map;
	}

	public static Map<String, String> getNameDisplayNameMap(Class<? extends CodeAndDisplayNameEnum> clazz) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (CodeAndDisplayNameEnum e : (CodeAndDisplayNameEnum[])clazz.getEnumConstants()) {
			map.put(((Enum)e).name(), e.getDisplayName());
		}
		return map;
	}
}
