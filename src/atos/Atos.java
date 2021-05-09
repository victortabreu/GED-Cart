/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos;

/**
 *
 * @author Victor
 */
public class Atos {

    public static String LISTAR_ATO = "SELECT * FROM atos ORDER BY codigo_ato";

    public static String REGISTRAR = "INSERT INTO atos(codigo_ato, nome_ato, emol_bruto, recompe_mg, emol_Liquido, taxa_fiscal, valor_final)"
            + "VALUES(?,?,?,?,?,?,?)";

    public static String ATUALIZAR = "UPDATE atos SET "
            + "nome_ato=?, "
            + "emol_bruto=?, "
            + "recompe_mg=?, "
            + "emol_Liquido=?, "
            + "taxa_fiscal=?, "
            + "valor_final=? WHERE codigo_ato=?";
    //"
    //+ "scan=? 

    public static String ELIMINAR = "DELETE FROM atos WHERE codigo_ato = ?";

    public static String ELIMINAR_TUDO = "DELETE FROM atos";

    private String primaryKey;
    private String nome;
    private String emol_bruto;
    private String recompe_mg;
    private String emol_liquido;
    private String taxa_fiscal;
    private String valor_final;

    public Atos() {

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

    public String getEmol_bruto() {
        return emol_bruto;
    }

    public void setEmol_bruto(String emol_bruto) {
        this.emol_bruto = emol_bruto;
    }

    public String getRecompe_mg() {
        return recompe_mg;
    }

    public void setRecompe_mg(String recompe_mg) {
        this.recompe_mg = recompe_mg;
    }

    public String getEmol_liquido() {
        return emol_liquido;
    }

    public void setEmol_liquido(String emol_liquido) {
        this.emol_liquido = emol_liquido;
    }

    public String getTaxa_fiscal() {
        return taxa_fiscal;
    }

    public void setTaxa_fiscal(String taxa_fiscal) {
        this.taxa_fiscal = taxa_fiscal;
    }

    public String getValor_final() {
        return valor_final;
    }

    public void setValor_final(String valor_final) {
        this.valor_final = valor_final;
    }
}
