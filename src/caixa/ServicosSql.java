/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caixa;

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
 * @author hugov
 */
public class ServicosSql {
    static Conectar cc = new Conectar();
    static Connection cn = cc.conexao();
    static PreparedStatement ps;
    
    
    public static int registrar(Servicos uc) {
        int rsu = 0;
        String sql = Servicos.REGISTRAR;
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, uc.getPrimaryKey());
            ps.setString(2, uc.getNumDoc_ser());
            ps.setString(3, uc.getNumAto_ser());
            ps.setString(4, uc.getQuant_ser());
            ps.setString(5, uc.getTotalAto_ser());
            ps.setString(6, uc.getData_ser());
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static int eliminar(String id, String ato) {
        int rsu = 0;
        String sql = Servicos.ELIMINAR;

        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, ato);
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static int eliminaTodos() {
        int rsu = 0;
        String sql = Servicos.ELIMINAR_TUDO;
        try {
            ps = cn.prepareStatement(sql);
            rsu = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(sql);
        return rsu;
    }

    public static void listar(String busca) {
        DefaultTableModel modelo = (DefaultTableModel) vendas.FrmVendas.tabela.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (busca.equals("")) {
            sql = Servicos.LISTAR_ESPECIAL;
        } else {
            sql = "SELECT data_ser, numDoc_ser, numAto_ser, quant_ser, emol_bruto, recompe_mg, emol_Liquido, taxa_fiscal,num_ser FROM `servicos` INNER JOIN atos ON servicos.numAto_ser = atos.codigo_ato WHERE num_ser like '%"+busca+"%';";
        }
        String dados[] = new String[9];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("data_ser");
                dados[1] = rs.getString("num_ser");
                dados[2] = rs.getString("numDoc_ser");
                dados[3] = rs.getString("numAto_ser");
                dados[4] = rs.getString("quant_ser");
                dados[5] = rs.getString("emol_bruto");
                dados[6] = rs.getString("recompe_mg");
                dados[7] = rs.getString("emol_Liquido");
                dados[8] = rs.getString("taxa_fiscal");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicosSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void listarData(String data1, String data2) {
        DefaultTableModel modelo = (DefaultTableModel) vendas.FrmVendas.tabela.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (data1.equals("")) {
            sql = Servicos.LISTAR_ESPECIAL;
        } else {
            sql = "SELECT data_ser, numDoc_ser, numAto_ser, quant_ser, emol_bruto, recompe_mg, emol_Liquido, taxa_fiscal,num_ser FROM `servicos` INNER JOIN atos ON servicos.numAto_ser = atos.codigo_ato WHERE data_ser BETWEEN '" + data1 + "' AND '" + data2 + "';";
            System.out.println(data1);
            System.out.println(data2);
            //SELECT numAto_ser, quant_ser, emol_bruto, recompe_mg, emol_Liquido, taxa_fiscal, valor_final, totalAto_ser FROM `servicos` INNER JOIN atos ON servicos.numAto_ser = atos.codigo_ato WHERE totalAto_ser <> "----" GROUP BY numAto_ser;
        }
        String dados[] = new String[9];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("data_ser");
                dados[1] = rs.getString("num_ser");
                dados[2] = rs.getString("numDoc_ser");
                dados[3] = rs.getString("numAto_ser");
                dados[4] = rs.getString("quant_ser");
                dados[5] = rs.getString("emol_bruto");
                dados[6] = rs.getString("recompe_mg");
                dados[7] = rs.getString("emol_Liquido");
                dados[8] = rs.getString("taxa_fiscal");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicosSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void listarDataH(String dataH) {
        DefaultTableModel modelo = (DefaultTableModel) vendas.FrmVendas.tabela.getModel();

        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        String sql = "";
        if (dataH.equals("")) {
            sql = Servicos.LISTAR_ESPECIAL;
        } else {
            sql = "SELECT data_ser, numDoc_ser, numAto_ser, quant_ser, emol_bruto, recompe_mg, emol_Liquido, taxa_fiscal,num_ser FROM `servicos` INNER JOIN atos ON servicos.numAto_ser = atos.codigo_ato WHERE data_ser LIKE '" + dataH + "';";
            System.out.println(dataH);
            //SELECT numAto_ser, quant_ser, emol_bruto, recompe_mg, emol_Liquido, taxa_fiscal, valor_final, totalAto_ser FROM `servicos` INNER JOIN atos ON servicos.numAto_ser = atos.codigo_ato WHERE totalAto_ser <> "----" GROUP BY numAto_ser;
        }
        String dados[] = new String[9];
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dados[0] = rs.getString("data_ser");
                dados[1] = rs.getString("num_ser");
                dados[2] = rs.getString("numDoc_ser");
                dados[3] = rs.getString("numAto_ser");
                dados[4] = rs.getString("quant_ser");
                dados[5] = rs.getString("emol_bruto");
                dados[6] = rs.getString("recompe_mg");
                dados[7] = rs.getString("emol_Liquido");
                dados[8] = rs.getString("taxa_fiscal");
                modelo.addRow(dados);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicosSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void numeros() {
        int j;
        int cont = 1;
        String num = "";
        String c = "";
        String SQL = "SELECT MAX(num_ser) FROM servicos";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                c = rs.getString(1);
            }

            if (c == null) {
                vendas.FrmCaixa.numFac.setText("00000001");
            } else {
                j = Integer.parseInt(c);
                GerarNumero gen = new GerarNumero();
                gen.gerar(j);
                vendas.FrmCaixa.numFac.setText(gen.serie());

            }

        } catch (SQLException ex) {
            Logger.getLogger(ServicosSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void numeros1() {
        int j;
        int cont = 1;
        String num = "";
        String c = "";
        String SQL = "SELECT MAX(num_ser) FROM vendas";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            if (rs.next()) {
                c = rs.getString(1);
            }

            if (c == null) {
                vendas.FrmCaixa.numFac.setText("00000001");
            } else {
                j = Integer.parseInt(c);
                GerarNumero gen = new GerarNumero();
                gen.gerar(j);
                vendas.FrmCaixa.numFac.setText(gen.serie());

            }

        } catch (SQLException ex) {
            Logger.getLogger(ServicosSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
