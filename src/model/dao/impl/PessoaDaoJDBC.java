package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.PessoaDao;
import model.entities.Pessoa;

public class PessoaDaoJDBC implements PessoaDao {

	private Connection conn;
	
	public PessoaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Pessoa findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM pessoas WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Pessoa obj = new Pessoa();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("name"));
				obj.setCpf(rs.getString("cpf"));
				obj.setRg(rs.getString("rg"));
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Pessoa> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM pessoas ORDER BY name");
			rs = st.executeQuery();

			List<Pessoa> list = new ArrayList<>();

			while (rs.next()) {
				Pessoa obj = new Pessoa();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("name"));
				obj.setCpf(rs.getString("cpf"));
				obj.setRg(rs.getString("rg"));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Pessoa obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO pessoas " +
				"(name) " +
				"(cpf) " +
				"(rg) " +
				"VALUES " +
				"(?)", 
				Statement.RETURN_GENERATED_KEYS);

			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Pessoa obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE pessoas " +
				"SET name = ? " +
				"SET cpf = ? " +
				"SET rg = ? " +
				"WHERE Id = ?");

			st.setInt(2, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM pessoas WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
		
}
