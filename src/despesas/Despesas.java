/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package despesas;

/**
 *
 * @author Victor
 */
public class Despesas {

    public static String LISTAR_DES = "SELECT * FROM despesas ORDER BY data_des";
    
    public static String LISTAR_DESEespec = "SELECT data_des, historico_des, valor_des from despesas order by data_des";
    
    public static String REGISTRAR = "INSERT INTO despesas(num_des, data_des, historico_des, valor_des)"
            + "VALUES(?,?,?,?)";

    public static String ATUALIZAR = "UPDATE despesas SET "
            + "data_des=?, "
            + "historico_des=?, "
            + "valor_des=? WHERE num_des=?";
    //"
    //+ "scan=? 

    public static String ELIMINAR = "DELETE FROM despesas WHERE num_des = ?";

    public static String ELIMINAR_TUDO = "DELETE FROM despesas";

    private String primaryKey;
    private String data_des;
    private String historico_des;
    private String valor_des;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getData_des() {
        return data_des;
    }

    public void setData_des(String data_des) {
        this.data_des = data_des;
    }

    public String getHistorico_des() {
        return historico_des;
    }

    public void setHistorico_des(String historico_des) {
        this.historico_des = historico_des;
    }

    public String getValor_des() {
        return valor_des;
    }

    public void setValor_des(String valor_des) {
        this.valor_des = valor_des;
    }

}
