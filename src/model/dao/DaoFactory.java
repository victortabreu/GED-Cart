package model.dao;

import db.DB;
import model.dao.impl.DocumentoDaoJDBC;
import model.dao.impl.FuncionarioDaoJDBC;

public class DaoFactory {

	public static FuncionarioDao createSellerDao() {
		return new FuncionarioDaoJDBC(DB.getConnection());
	}
	
	public static DocumentoDao createDocumentoDao() {
		return new DocumentoDaoJDBC(DB.getConnection());
	}
}
