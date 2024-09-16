package com.mehmetc.utility;

import java.util.List;
import java.util.Optional;

public interface ICRUDService<T> {
	Optional<T> save(T t);
	boolean delete(Long id);
	Optional<T> update(T t);
	List<T> findAll();
	Optional<T> findById(Long id);
}