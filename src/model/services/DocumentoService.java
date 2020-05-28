package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Documento;

public class DocumentoService {
	
	public List<Documento> findAll(){
		List<Documento> list = new ArrayList<>();
		list.add(new Documento(1,"Certidão de Nascimento"));
		list.add(new Documento(2,"Certidão de Óbito"));
		list.add(new Documento(3,"Certidão de Casamento"));
		return list;
	}

}
