/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixa;

/**
 *
 * @author hugov
 */
public class Servicos {

    public static String LISTAR = "SELECT * FROM servicos WHERE totalAto_ser <> \"----\" ORDER BY data_ser";
    
    public static String LISTAR_ESPECIAL = "SELECT data_ser, numDoc_ser, numAto_ser, quant_ser, emol_bruto, recompe_mg, emol_Liquido, taxa_fiscal,num_ser FROM `servicos` INNER JOIN atos ON servicos.numAto_ser = atos.codigo_ato WHERE totalAto_ser <> \"----\";";

    public static String REGISTRAR = "INSERT INTO servicos(num_ser, numDoc_ser, numAto_ser, quant_ser, totalAto_ser, data_ser) "
            + "VALUES(?,?,?,?,?,?)";

    public static String ELIMINAR = "DELETE FROM servicos WHERE num_ser = ? and numAto_ser = ?";

    public static String ELIMINAR_TUDO = "DELETE FROM servicos";

    private String primaryKey;
    private String numDoc_ser;
    private String numAto_ser;
    private String quant_ser;
    private String totalAto_ser;
    private String data_ser;

    public Servicos() {

    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getNumDoc_ser() {
        return numDoc_ser;
    }

    public void setNumDoc_ser(String numDoc_ser) {
        this.numDoc_ser = numDoc_ser;
    }

    public String getNumAto_ser() {
        return numAto_ser;
    }

    public void setNumAto_ser(String numAto_ser) {
        this.numAto_ser = numAto_ser;
    }
    
    public String getQuant_ser() {
        return quant_ser;
    }

    public void setQuant_ser(String quant_ser) {
        this.quant_ser = quant_ser;
    }

    public String getTotalAto_ser() {
        return totalAto_ser;
    }

    public void setTotalAto_ser(String totalAto_ser) {
        this.totalAto_ser = totalAto_ser;
    }

    public String getData_ser() {
        return data_ser;
    }

    public void setData_ser(String data_ser) {
        this.data_ser = data_ser;
    }

}
