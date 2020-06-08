package model.dao;

import db.DB;
import model.dao.impl.DocumentoDaoJDBC;
import model.dao.impl.FuncionarioDaoJDBC;
import model.dao.impl.PessoaDaoJDBC;
import model.dao.impl.TipoDaoJDBC;

public class DaoFactory {

	public static FuncionarioDao createSellerDao() {
		return new FuncionarioDaoJDBC(DB.getConnection());
	}
	
	public static DocumentoDao createDocumentoDao() {
		return new DocumentoDaoJDBC(DB.getConnection());
	}
	public static TipoDao createTipoDao() {
		return new TipoDaoJDBC(DB.getConnection());
	}
	public static PessoaDao createPessoaDao() {
		return new PessoaDaoJDBC(DB.getConnection());
	}
}
