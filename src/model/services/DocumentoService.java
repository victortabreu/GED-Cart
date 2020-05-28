package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Documento;

public class DocumentoService {
	
	public List<Documento> findAll(){
		List<Documento> list = new ArrayList<>();
		list.add(new Documento(1,"Certid�o de Nascimento"));
		list.add(new Documento(2,"Certid�o de �bito"));
		list.add(new Documento(3,"Certid�o de Casamento"));
		return list;
	}

}
