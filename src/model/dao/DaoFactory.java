package model.dao;

import db.DB;
import model.dao.impl.DocumentoDaoJDBC;

public class DaoFactory {
	
	public static DocumentoDao createDocumentoDao() {
		return new DocumentoDaoJDBC(DB.getConnection());
	}
}
