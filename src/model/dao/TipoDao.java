package model.dao;

import java.util.List;

import model.entities.Tipo;

public interface TipoDao {

	void insert(Tipo obj);
	void update(Tipo obj);
	void deleteById(Integer id);
	Tipo findById(Integer id);
	List<Tipo> findAll();
}
