package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.PessoaDao;
import model.entities.Pessoa;

public class PessoaDaoJDBC implements PessoaDao {

	private Connection conn;
	
	public PessoaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	//lista os ítens da tabela ordenados por Id 
	@Override
	public Pessoa findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM Pessoa WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Pessoa obj = new Pessoa();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setCpf(rs.getString("Cpf"));
				obj.setRg(rs.getString("Rg"));
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
	
	//lista os ítens da tabela ordenados por nome 
	@Override
	public List<Pessoa> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM pessoa ORDER BY Name");
			rs = st.executeQuery();

			List<Pessoa> list = new ArrayList<>();

			while (rs.next()) {
				Pessoa obj = new Pessoa();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				obj.setCpf(rs.getString("Cpf"));
				obj.setRg(rs.getString("Rg"));
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
	
	//Insere um ítem na tabela
	@Override
	public void insert(Pessoa obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO pessoa " +
				"(Name, Rg, Cpf) " +
				"VALUES " +
				"(?, ?, ?)", 
				Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getRg());

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

	//Edita o ítem da tabela
	@Override
	public void update(Pessoa obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE Pessoa " +
				"SET Name = ?, Cpf = ?, Rg = ? " +
				"WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getRg());
			st.setInt(4, obj.getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}
	
	// Exclui o ítem da tabela
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM pessoa WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
}