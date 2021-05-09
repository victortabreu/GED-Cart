/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package selos;

/**
 *
 * @author Victor
 */
public class Selos {
    public static String LISTAR_SELO = "SELECT * FROM selos ORDER BY codigo_selo";
    
    public static String REGISTRAR = "INSERT INTO selos(codigo_selo, tipo_selo) "
            + "VALUES(?,?)";
    
    public static String ATUALIZAR = "UPDATE selos SET "
                + "tipo_doc=? WHERE codigo_doc=?";
                //"
                //+ "scan=? 
    
    public static String ELIMINAR = "DELETE FROM selos WHERE codigo_doc = ?";
    
    public static String ELIMINAR_TUDO = "DELETE FROM selos";
    
    private String primaryKey;
    private String tipoSelo;

    public Selos(){
        
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getTipoSelo() {
        return tipoSelo;
    }

    public void setTipoSelo(String tipoSelo) {
        this.tipoSelo = tipoSelo;
    }
    
}
