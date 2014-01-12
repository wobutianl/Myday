package com.cyan.myday.sql;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class ObjectMap<K, V> extends HashMap {
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Set<Entry<String, Object>> set = this.entrySet();
		sb.append("[");
		for (Entry<String, Object> s : set) {
			sb.append("{").append(s.getKey()).append(":'")
					.append(s.getValue().toString()).append("'},");
		}
		sb.setLength(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 返回一个任意类型的对象
	 * 
	 * @param key
	 * @return
	 */
	public <T> T getAnyTypeVale(String key) {
		return (T) get(key);
	}

	public String getStringValue(String key) {
		return (String) get(key);
	}

	public int getIntegerValue(String key) {
		return (Integer) get(key);
	}

	public Long getLongValue(String key) {
		return (Long) get(key);
	}

	public boolean getBooleanValue(String key) {
		return (Boolean) get(key);
	}

	public Date getDateValue(String key) {
		return (Date) get(key);
	}
}
