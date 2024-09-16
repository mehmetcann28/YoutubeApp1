package com.mehmetc.utility;

import java.sql.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class SQLQueryBuilder {
	
	// Insert sorgusu oluşturma
	public static String generateInsert(Object entity, String tableName) {
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		
		boolean firstField = true;
		for (Field field : fields) {
			field.setAccessible(true);
			if (!field.getName().equalsIgnoreCase("id") && !field.getName().endsWith("_id")) {  // ID alanını atlıyoruz
				if (!firstField) {
					columns.append(", ");
					values.append(", ");
				} else {
					firstField = false;
				}
				columns.append(field.getName());
				try {
					Object value = field.get(entity);
					if (value == null) {
						values.append("NULL");
					} else if (value instanceof String) {
						// Tırnak işaretlerini kaçış karakteri ile değiştiriyoruz
						String safeValue = ((String) value).replace("'", "''");
						values.append("'").append(safeValue).append("'");
					} else if (value instanceof LocalDate || value instanceof LocalDateTime || value instanceof Timestamp) {
						values.append("'").append(value).append("'");
					} else if (value instanceof UUID) {
						values.append("'").append(value.toString()).append("'");
					} else if (value instanceof BigDecimal) {
						values.append(((BigDecimal) value).toPlainString());
					} else if (value instanceof Enum) { // Enum türleri için
						values.append("'").append(value.toString()).append("'");
					} else {
						values.append(value);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")";
	}
	
	// Update sorgusu oluşturma
	public static String generateUpdate(Object entity, String tableName) {
		Class<?> clazz = entity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder setClause = new StringBuilder();
		Object idValue = null;
		
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getName().equalsIgnoreCase("id") || field.getName().endsWith("_id")) {
				try {
					idValue = field.get(entity);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				try {
					Object value = field.get(entity);
					if (setClause.length() > 0) setClause.append(", ");
					setClause.append(field.getName()).append("=");
					if (value == null) {
						setClause.append("NULL");
					} else if (value instanceof String) {
						// Tırnak işaretlerini kaçış karakteri ile değiştiriyoruz
						String safeValue = ((String) value).replace("'", "''");
						setClause.append("'").append(safeValue).append("'");
					} else if (value instanceof LocalDate || value instanceof LocalDateTime || value instanceof Timestamp) {
						setClause.append("'").append(value).append("'");
					} else if (value instanceof UUID) {
						setClause.append("'").append(value.toString()).append("'");
					} else if (value instanceof BigDecimal) {
						setClause.append(((BigDecimal) value).toPlainString());
					} else if (value instanceof Enum) { // Enum türleri için
						setClause.append("'").append(value.toString()).append("'");
					} else {
						setClause.append(value);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (idValue == null) {
			throw new IllegalArgumentException("ID değeri eksik.");
		}
		
		return "UPDATE " + tableName + " SET " + setClause + " WHERE " + idField(clazz) + "=" + idValue;
	}
	
	// Delete sorgusu oluşturma
	public static String generateDelete(Class<?> entityClass, String tableName, Object id) {
		String idColumnName = idField(entityClass);
		return "DELETE FROM " + tableName + " WHERE " + idColumnName + " = '" + id + "'";
	}
	
	// Veritabanından listeleme işlemi
	public static <T> List<T> generateList(Class<T> entityClass, String tableName, ResultSet resultSet) {
		List<T> resultList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				T entity = entityClass.getDeclaredConstructor().newInstance();
				populateEntityFromResultSet(entity, entityClass, resultSet);
				resultList.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	// ID'ye göre veri bulma işlemi
	public static <T> Optional<T> findById(Class<T> entityClass, String tableName, Object id, ResultSet resultSet) {
		try {
			if (resultSet.next()) {
				T entity = entityClass.getDeclaredConstructor().newInstance();
				populateEntityFromResultSet(entity, entityClass, resultSet);
				return Optional.of(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
	// ResultSet'ten entity'yi doldurma
	private static <T> void populateEntityFromResultSet(T entity, Class<?> entityClass, ResultSet resultSet)
			throws SQLException, IllegalAccessException {
		Class<?> currentClass = entityClass;
		while (currentClass != null) {
			for (Field field : currentClass.getDeclaredFields()) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object value = resultSet.getObject(fieldName);
				
				if (value != null) {
					try {
						// Long ve Integer tür dönüşümü
						if (field.getType().equals(Long.class) && value instanceof Integer) {
							field.set(entity, ((Integer) value).longValue());
						}
						// Array -> String[]
						else if (field.getType().equals(String[].class) && value instanceof java.sql.Array) {
							java.sql.Array array = (java.sql.Array) value;
							field.set(entity, (String[]) array.getArray());
						}
						// LocalDate -> Date
						else if (field.getType().equals(LocalDate.class) && value instanceof java.sql.Date) {
							field.set(entity, ((java.sql.Date) value).toLocalDate());
						}
						// LocalDateTime -> Timestamp
						else if (field.getType().equals(LocalDateTime.class) && value instanceof Timestamp) {
							field.set(entity, ((Timestamp) value).toLocalDateTime());
						}
						// LocalTime -> Time
						else if (field.getType().equals(LocalTime.class) && value instanceof java.sql.Time) {
							field.set(entity, ((java.sql.Time) value).toLocalTime());
						}
						// BigDecimal -> Number
						else if (field.getType().equals(BigDecimal.class) && value instanceof Number) {
							field.set(entity, BigDecimal.valueOf(((Number) value).doubleValue()));
						}
						// UUID
						else if (field.getType().equals(UUID.class) && value instanceof UUID) {
							field.set(entity, value);
						}
						// Boolean
						else if (field.getType().equals(Boolean.class) && value instanceof Boolean) {
							field.set(entity, value);
						}
						// Enum türleri için
						else if (field.getType().isEnum()) {
							field.set(entity, Enum.valueOf((Class<Enum>) field.getType(), value.toString()));
						}
						// Genel set işlemi
						else {
							field.set(entity, value);
						}
					} catch (SQLException | IllegalArgumentException e) {
						System.out.println("Error setting field: " + fieldName + " - " + e.getMessage());
					}
				}
			}
			currentClass = currentClass.getSuperclass();
		}
	}
	
	
	
	// Entity sınıfından ID alanını belirleme
	private static String idField(Class<?> entityClass) {
		for (Field field : entityClass.getDeclaredFields()) {
			if (field.getName().equalsIgnoreCase("id") || field.getName().endsWith("_id")) {
				return field.getName();
			}
		}
		throw new IllegalArgumentException("ID alanı bulunamadı.");
	}
}