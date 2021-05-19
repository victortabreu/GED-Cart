/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package despesas;

import atos.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import principal.Conectar;
import principal.GerarNumero;

/**
 *
 * @author Victor
 */
public class DespesasSql {

    static Conectar cc = new Conectar();
    static Connection cn = cc.conexao();
    static PreparedStatement ps;
     
    

    public static void listarDepesas(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) vendas.FrmVendas.tabela1.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Despesas.LISTAR_DES;
        } else {
            sql = "SELECT * FROM despesas WHERE (num_des like'" + busca + "') "
                    + " order by data_des";

        }
        String dados[] = new String[4];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("data_des");
                dados[1] = rs.getString("num_des");                
                dados[2] = rs.getString("historico_des");
                dados[3] = rs.getString("valor_des");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Atos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void listarDataDes(String busca1, String busca2) {
        DefaultTableModel modelo = (DefaultTableModel) vendas.FrmVendas.tabela1.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca1.equals("")) {
            sql = Despesas.LISTAR_DES;
        } else {
            sql = "SELECT * FROM despesas WHERE (data_des BETWEEN '" + busca1 + "' AND '"+busca2+"') "
                    + " order by data_des";

        }
        String dados[] = new String[4];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("data_des");
                dados[1] = rs.getString("num_des");                
                dados[2] = rs.getString("historico_des");
                dados[3] = rs.getString("valor_des");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Atos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void listarCat(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) atos.FrmListaAtos.tabela.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Atos.LISTAR_ATO;
        } else {
            sql = "SELECT * FROM atos WHERE (codigo_ato like'" + busca + "%' or nome_ato like'%" + busca + "%') "
                    + " order by codigo_ato";

        }
        String dados[] = new String[7];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("codigo_ato");
                dados[1] = rs.getString("nome_ato");
                dados[2] = rs.getString("emol_bruto");
                dados[3] = rs.getString("recompe_mg");
                dados[4] = rs.getString("emol_Liquido");
                dados[5] = rs.getString("taxa_fiscal");
                dados[6] = rs.getString("valor_final");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Atos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int registrarDespesa(Despesas uc) {
        int rsu = 0;
        String sql = Despesas.REGISTRAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, uc.getPrimaryKey());
            ps.setString(2, uc.getData_des());
            ps.setString(3, uc.getHistorico_des());
            ps.setString(4, uc.getValor_des());

            //ps.setBytes(4, uc.getScan());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static boolean isNumber(String n) {
        try {
            return Integer.parseInt(n) > 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
     public static void numeros() {
        int j;
        int cont = 1;
        String num = "";
        String c = "";
        String SQL = "SELECT MAX(num_des) FROM despesas";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                c = rs.getString(1);
            }

            if (c == null) {
                FrmDespesas.codigo.setText("00000001");
            } else {
                j = Integer.parseInt(c);
                GerarNumero gen = new GerarNumero();
                gen.gerar(j);
                FrmDespesas.codigo.setText(gen.serie());

            }

        } catch (SQLException ex) {
            Logger.getLogger(DespesasSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static void gerarId() {
//        int j;
//        int cont = 1;
//        String num = "";
//        String c = "";
//        String SQL = "SELECT MAX(codigo_doc) FROM Atos";
//
//        try {
//            Statement st = cn.createStatement();
//            ResultSet rs = st.executeQuery(SQL);
//            while (rs.next()) {
//                c = rs.getString(1);
//            }
//
//            if (c == null) {
//                Atos.FrmListaAto.codigo.setText("DOC0001");
//            } else {
//                char r1 = c.charAt(3);
//                char r2 = c.charAt(4);
//                char r3 = c.charAt(5);
//                char r4 = c.charAt(6);
//                String r = "";
//                r = "" + r1 + r2 + r3 + r4;
//                j = Integer.parseInt(r);
//                GerarCodigos gen = new GerarCodigos();
//                gen.gerar(j);
//                Atos.FrmAtos.codigo.setText("DOC" + gen.serie());
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(AtosSql.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
    public static int atualizarDes(Despesas uc) {
        int rsu = 0;
        String sql = Despesas.ATUALIZAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, uc.getData_des());
            ps.setString(2, uc.getHistorico_des());
            ps.setString(3, uc.getValor_des());
            ps.setString(4, uc.getPrimaryKey());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            print(ex);
        }
        System.out.println(sql);
        return rsu;
    }
//
    public static int eliminarDes(String id) {
        int rsu = 0;
        String sql = Despesas.ELIMINAR;

        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }
//
//    public static int eliminaTodos() {
//        int rsu = 0;
//        String sql = Atos.ELIMINAR_TUDO;
//        try {
//            ps = cn.prepareStatement(sql);
//            rsu = ps.executeUpdate();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        System.out.println(sql);
//        return rsu;
//    }
    private static void print(SQLException ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
