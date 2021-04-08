/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package documentos;

/**
 *
 * @author Victor
 */
public class Documentos {
    public static String LISTAR_DOC = "SELECT * FROM documentos ORDER BY codigo_doc";
    
    public static String REGISTRAR = "INSERT INTO documentos(codigo_doc, nome_doc, tipo_doc, scan, textoOCR ) "
            + "VALUES(?,?,?,?,?)";
    
    public static String ATUALIZAR = "UPDATE documentos SET "
                + "nome_doc=?, "
                + "tipo_doc=? WHERE codigo_doc=?";
                //"
                //+ "scan=? 
    
    public static String ELIMINAR = "DELETE FROM documentos WHERE codigo_doc = ?";
    
    public static String ELIMINAR_TUDO = "DELETE FROM documentos";
    
    private String primaryKey;
    private String nome;
    private String tipodoc;
    private String scan;
    private String textoOCR;

    public Documentos(){
        
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(String tipouser) {
        this.tipodoc = tipouser;
    }
    
    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }
    
     public String getTextoOCR() {
        return textoOCR;
    }

    public void setTextoOCR(String textoOCR) {
        this.textoOCR = textoOCR;
    }
    
}
