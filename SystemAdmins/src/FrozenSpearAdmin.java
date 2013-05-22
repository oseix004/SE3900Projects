    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Formatter;
import java.util.Scanner;
import javax.swing.JOptionPane;
/*import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;*/
/**
 *
 * @author Isaac Osei
 */
public class FrozenSpearAdmin extends javax.swing.JFrame {

   private Socket wib;
   private Formatter serverWriter;
   private Scanner serverReader;
   private boolean connected;
   private int playerRatio;
   private int developerRatio;
   private int administratorRatio;
   private Object[][] playerProfileInformation;
   private Object[][] hotlistInformation;
   private Object[][] administratorProfileInformation;
   private Object[][] developerProfileInformation;
   private Object[][] databaseHistoryInformation;
   private Object[][]  loginHistoryInformation;
   private Object[][] gameInformation;
   
   
   String hotListQuery = "";
   int selectedGameID = 0;
   int gameTableSelectedRow = 0;
    /**
     * Creates new form FrozenSpearAdmin
     */
    public FrozenSpearAdmin() {
       // wib = new WebsiteInformationBuilder();
        establishConnection();
        initVariables();
        initComponents();
            
    }
private void establishConnection(){
//    String serverComputer = "146.57.164.38";
    String serverComputer = "UMC-8470-1158";
    try {
        wib = new Socket(serverComputer, 3309);
        serverWriter = new Formatter(wib.getOutputStream());
        serverReader = new Scanner(wib.getInputStream());
        connected = true;
    } catch (UnknownHostException ex){
        System.err.println("Don't know about host: " + serverComputer);
        System.exit(1);
    } catch (IOException ex){
        System.err.println("Couldn't get I/O for the connection");
        System.exit(1);
    }
}
    private void initVariables() {
        serverWriter.format("%s\n", "initialize");
        serverWriter.flush();
       while(connected){
           if(serverReader.hasNextLine()){
               String[] variables = serverReader.nextLine().split(" ");
               playerRatio = Integer.parseInt(variables[0]);
               developerRatio = Integer.parseInt(variables[1]);
               administratorRatio =Integer.parseInt(variables[2]);
          
           String[] ppiRows = variables[3].split(";");
          playerProfileInformation = new String[ppiRows.length][ppiRows[0].split(":").length];
         for( int x = 0;x<ppiRows.length; x++){
             System.out.println(ppiRows[x]);
         }
            for(int x= 0; x < ppiRows.length; x++) {
                String[] ppiColumns = ppiRows[x].split(":");
              for(int y = 0; y < ppiColumns.length; y++){
                  System.out.println(ppiColumns[y]);
              }
              for (int y = 0; y < ppiColumns.length; y++){
                  System.out.println(ppiColumns[y]);
                 playerProfileInformation[x][y] = ppiColumns[y];
              }
            }
            
         
         String[] hliRows = variables[4].split(";");
         hotlistInformation = new String[hliRows.length][hliRows[0].split(":").length];
         for( int x = 0;x<hliRows.length; x++){
             System.out.println(hliRows[x]);
         }
            for(int x= 0; x < hliRows.length; x++) {
                String[] hliColumns = hliRows[x].split(":");
              for(int y = 0; y < hliColumns.length; y++){
                  System.out.println(hliColumns[y]);
              }
              for (int y = 0; y < hliColumns.length; y++){
                  System.out.println(hliColumns[y]);
                 hotlistInformation[x][y] = hliColumns[y];
              }
            }
            
            
         String[] apiRows = variables[5].split(";");
         administratorProfileInformation = new String[apiRows.length][apiRows[0].split(":").length];
         for( int x = 0;x<apiRows.length; x++){
             System.out.println(apiRows[x]);
         }
            for(int x= 0; x < apiRows.length; x++) {
                String[] apiColumns = apiRows[x].split(":");
              for(int y = 0; y < apiColumns.length; y++){
                  System.out.println(apiColumns[y]);
              }
              for (int y = 0; y < apiColumns.length; y++){
                  System.out.println(apiColumns[y]);
                 administratorProfileInformation[x][y] = apiColumns[y];
              }
            }

            
          String[] dpiRows = variables[6].split(";");
         developerProfileInformation = new String[dpiRows.length][dpiRows[0].split(":").length];
         for( int x = 0;x<dpiRows.length; x++){
             System.out.println(dpiRows[x]);
         }
            for(int x= 0; x < dpiRows.length; x++) {
                String[] dpiColumns = dpiRows[x].split(":");
              for(int y = 0; y < dpiColumns.length; y++){
                  System.out.println(dpiColumns[y]);
              }
              for (int y = 0; y < dpiColumns.length; y++){
                  System.out.println(dpiColumns[y]);
                 developerProfileInformation[x][y] = dpiColumns[y];
              }
            }
            
            
            
         String[] dhiRows = variables[7].split(";");
         databaseHistoryInformation = new String[dhiRows.length][dhiRows[0].split(":").length];
         for( int x = 0;x<dhiRows.length; x++){
             System.out.println(dhiRows[x]);
         }
            for(int x= 0; x < dhiRows.length; x++) {
                String[] dhiColumns = dhiRows[x].split(":");
              for(int y = 0; y < dhiColumns.length; y++){
                  System.out.println(dhiColumns[y]);
              }
              for (int y = 0; y < dhiColumns.length; y++){
                  System.out.println(dhiColumns[y]);
                 databaseHistoryInformation[x][y] = dhiColumns[y];
              }
            }
            
         String[] lhiRows = variables[8].split(";");
         loginHistoryInformation = new String[lhiRows.length][lhiRows[0].split(":").length];
         for( int x = 0;x<lhiRows.length; x++){
             System.out.println(lhiRows[x]);
         }
            for(int x= 0; x < lhiRows.length; x++) {
                String[] lhiColumns = lhiRows[x].split(":");
              for(int y = 0; y < lhiColumns.length; y++){
                  System.out.println(lhiColumns[y]);
              }
              for (int y = 0; y < lhiColumns.length; y++){
                  System.out.println(lhiColumns[y]);
                 loginHistoryInformation[x][y] = lhiColumns[y];
              }
            }
            
            
         String[] giRows = variables[11].split(";");
         gameInformation = new String[giRows.length][giRows[0].split(":").length];
         for( int x = 0;x<giRows.length; x++){
             System.out.println(giRows[x]);
         }
            for(int x= 0; x < giRows.length; x++) {
                String[] giColumns = giRows[x].split(":");
              for(int y = 0; y < giColumns.length; y++){
                  System.out.println(giColumns[y]);
              }
              for (int y = 0; y < giColumns.length; y++){
                  System.out.println(giColumns[y]);
                 gameInformation[x][y] = giColumns[y];
              }
            }
            
            break;
           }
       }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Administrator = new javax.swing.JFrame();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        Admin = new javax.swing.JInternalFrame();
        jScrollPane5 = new javax.swing.JScrollPane();
        admin = new javax.swing.JTable();
        Player = new javax.swing.JFrame();
        jPanel7 = new javax.swing.JPanel();
        Play = new javax.swing.JInternalFrame();
        jScrollPane9 = new javax.swing.JScrollPane();
        play = new javax.swing.JTable();
        Developer = new javax.swing.JFrame();
        jPanel6 = new javax.swing.JPanel();
        Develop = new javax.swing.JInternalFrame();
        jScrollPane7 = new javax.swing.JScrollPane();
        developer = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        Overallstatistics1 = new javax.swing.JInternalFrame();
        playerratio = new javax.swing.JProgressBar();
        DeveloperRatio = new javax.swing.JProgressBar();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jInternalFrame6 = new javax.swing.JInternalFrame();
        jScrollPane6 = new javax.swing.JScrollPane();
        loginHistroy = new javax.swing.JTable();
        Login = new javax.swing.JButton();
        Logout = new javax.swing.JButton();
        jInternalFrame7 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        databasehistory = new javax.swing.JTable();
        jInternalFrame8 = new javax.swing.JInternalFrame();
        Java = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        Information = new javax.swing.JInternalFrame();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        games = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Games = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        Int_GameID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Date_DateAdded = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Int_PlayCount = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        PutonHotlist = new javax.swing.JRadioButton();
        TakeofHotlist = new javax.swing.JRadioButton();
        Cmd_Save = new javax.swing.JButton();

        Administrator.setAlwaysOnTop(true);
        Administrator.setName("Administrator"); // NOI18N
        Administrator.setType(java.awt.Window.Type.POPUP);

        Admin.setTitle("HISTROY");
        Admin.setMaximumSize(new java.awt.Dimension(21474, 21474));
        Admin.setVisible(true);

        admin.setAutoCreateRowSorter(true);
        admin.setModel(new javax.swing.table.DefaultTableModel(
            administratorProfileInformation,
            new String [] {
                "UserId", "FirstName", "LastName"
            }
        ));
        jScrollPane5.setViewportView(admin);

        javax.swing.GroupLayout AdminLayout = new javax.swing.GroupLayout(Admin.getContentPane());
        Admin.getContentPane().setLayout(AdminLayout);
        AdminLayout.setHorizontalGroup(
            AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1122, Short.MAX_VALUE)
        );
        AdminLayout.setVerticalGroup(
            AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Admin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Admin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout AdministratorLayout = new javax.swing.GroupLayout(Administrator.getContentPane());
        Administrator.getContentPane().setLayout(AdministratorLayout);
        AdministratorLayout.setHorizontalGroup(
            AdministratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AdministratorLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        AdministratorLayout.setVerticalGroup(
            AdministratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdministratorLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Play.setVisible(true);

        play.setModel(new javax.swing.table.DefaultTableModel(
            playerProfileInformation,
            new String [] {
                "UserId", "FirstName", "LastName"
            }
        ));
        play.setMinimumSize(new java.awt.Dimension(367, 450));
        jScrollPane9.setViewportView(play);

        javax.swing.GroupLayout PlayLayout = new javax.swing.GroupLayout(Play.getContentPane());
        Play.getContentPane().setLayout(PlayLayout);
        PlayLayout.setHorizontalGroup(
            PlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );
        PlayLayout.setVerticalGroup(
            PlayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PlayerLayout = new javax.swing.GroupLayout(Player.getContentPane());
        Player.getContentPane().setLayout(PlayerLayout);
        PlayerLayout.setHorizontalGroup(
            PlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Play)
        );
        PlayerLayout.setVerticalGroup(
            PlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PlayerLayout.createSequentialGroup()
                .addComponent(Play, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Develop.setVisible(true);

        developer.setModel(new javax.swing.table.DefaultTableModel(
            developerProfileInformation,
            new String [] {
                "UserId", "FirstName", "LastName"
            }
        ));
        jScrollPane7.setViewportView(developer);

        javax.swing.GroupLayout DevelopLayout = new javax.swing.GroupLayout(Develop.getContentPane());
        Develop.getContentPane().setLayout(DevelopLayout);
        DevelopLayout.setHorizontalGroup(
            DevelopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );
        DevelopLayout.setVerticalGroup(
            DevelopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Develop, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Develop, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout DeveloperLayout = new javax.swing.GroupLayout(Developer.getContentPane());
        Developer.getContentPane().setLayout(DeveloperLayout);
        DeveloperLayout.setHorizontalGroup(
            DeveloperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DeveloperLayout.setVerticalGroup(
            DeveloperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        getContentPane().add(jLabel1, java.awt.BorderLayout.CENTER);

        Overallstatistics1.setTitle("Overall statistic");
        Overallstatistics1.setName("Overall statistics"); // NOI18N
        Overallstatistics1.setVisible(true);

        playerratio.setValue(administratorRatio);

        DeveloperRatio.setValue(playerRatio);

        jProgressBar1.setValue(developerRatio);

        jLabel6.setText("ACCOUNT TYPE");

        jLabel7.setText("Administrator");

        jLabel8.setText("Player");

        jLabel9.setText("Developer");

        javax.swing.GroupLayout Overallstatistics1Layout = new javax.swing.GroupLayout(Overallstatistics1.getContentPane());
        Overallstatistics1.getContentPane().setLayout(Overallstatistics1Layout);
        Overallstatistics1Layout.setHorizontalGroup(
            Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Overallstatistics1Layout.createSequentialGroup()
                .addGroup(Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Overallstatistics1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel6))
                    .addGroup(Overallstatistics1Layout.createSequentialGroup()
                        .addGroup(Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Overallstatistics1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(jLabel7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Overallstatistics1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(48, 48, 48)
                        .addGroup(Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(playerratio, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(DeveloperRatio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 207, Short.MAX_VALUE))
        );
        Overallstatistics1Layout.setVerticalGroup(
            Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Overallstatistics1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(playerratio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeveloperRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Overallstatistics1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jInternalFrame6.setTitle("Stats on Usage");
        jInternalFrame6.setVisible(true);

        loginHistroy.setModel(new javax.swing.table.DefaultTableModel(
            loginHistoryInformation,
            new String [] {
                "Username", "LoggedIn", "LoggedOut", "LoggedInID"
            }
        ));
        jScrollPane6.setViewportView(loginHistroy);

        Login.setText("Login");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame6Layout = new javax.swing.GroupLayout(jInternalFrame6.getContentPane());
        jInternalFrame6.getContentPane().setLayout(jInternalFrame6Layout);
        jInternalFrame6Layout.setHorizontalGroup(
            jInternalFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1127, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame6Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(Logout)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Login)
                .addGap(101, 101, 101))
        );
        jInternalFrame6Layout.setVerticalGroup(
            jInternalFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame6Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jInternalFrame6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jInternalFrame7.setTitle("Database History");
        jInternalFrame7.setVisible(true);

        databasehistory.setModel(new javax.swing.table.DefaultTableModel(
            databaseHistoryInformation,
            new String [] {
                "Username", "LoggedIn", "LoggedOut", "FowlConduct"
            }
        ));
        databasehistory.setName(""); // NOI18N
        jScrollPane1.setViewportView(databasehistory);

        javax.swing.GroupLayout jInternalFrame7Layout = new javax.swing.GroupLayout(jInternalFrame7.getContentPane());
        jInternalFrame7.getContentPane().setLayout(jInternalFrame7Layout);
        jInternalFrame7Layout.setHorizontalGroup(
            jInternalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jInternalFrame7Layout.setVerticalGroup(
            jInternalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jInternalFrame8.setTitle("System Requirements");
        jInternalFrame8.setVisible(true);
        jInternalFrame8.getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        Java.setText("Java");
        Java.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JavaActionPerformed(evt);
            }
        });
        jInternalFrame8.getContentPane().add(Java);

        jButton5.setText("Browser");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jInternalFrame8.getContentPane().add(jButton5);

        jButton6.setText("Security");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jInternalFrame8.getContentPane().add(jButton6);

        Information.setTitle("Role");
        Information.setVisible(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Admin1.jpg"))); // NOI18N
        jButton1.setText("Adminstrator");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Information.getContentPane().add(jButton1, java.awt.BorderLayout.CENTER);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Players.png"))); // NOI18N
        jButton2.setText("Players");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        Information.getContentPane().add(jButton2, java.awt.BorderLayout.PAGE_START);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/developer.png"))); // NOI18N
        jButton3.setText("Developer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        Information.getContentPane().add(jButton3, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Overallstatistics1)
                            .addComponent(Information, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jInternalFrame7)
                            .addComponent(jInternalFrame8)))
                    .addComponent(jInternalFrame6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Overallstatistics1)
                    .addComponent(jInternalFrame7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Information)
                    .addComponent(jInternalFrame8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jInternalFrame6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Overiew", null, jPanel4, "");

        games.setAutoCreateRowSorter(true);
        games.setModel(new javax.swing.table.DefaultTableModel(
            loginHistoryInformation,
            new String [] {
                "GameID", "GameName", "Genre", "Developer", "ShortDescription", "LongDescription", "DateAdded", "Rating", "TotalPlays"
            }
        ));
        games.setUpdateSelectionOnSort(false);
        games.setVerifyInputWhenFocusTarget(false);
        games.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                gamesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(games);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1133, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 57, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pending Games", jPanel2);

        Games.setModel(new javax.swing.table.DefaultTableModel(
            hotlistInformation,
            new String [] {
                "GameId", "DateAdded", "PlayCount", "Hotlist"
            }
        ));
        Games.setToolTipText("");
        Games.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GamesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Games);

        jLabel2.setText("GameId");

        Int_GameID.setText("gameid");

        jLabel3.setText("DateAdded");

        Date_DateAdded.setText("jTextField2");

        jLabel4.setText("PlayCount");

        Int_PlayCount.setText("jTextField3");

        jLabel5.setText("Hotlist");

        buttonGroup1.add(PutonHotlist);
        PutonHotlist.setText("Put on Hotlist");
        PutonHotlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PutonHotlistActionPerformed(evt);
            }
        });

        buttonGroup1.add(TakeofHotlist);
        TakeofHotlist.setText("Take of Hotlist");
        TakeofHotlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TakeofHotlistActionPerformed(evt);
            }
        });

        Cmd_Save.setText("Update");
        Cmd_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cmd_SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1153, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(Int_PlayCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(PutonHotlist)
                        .addGap(18, 18, 18)
                        .addComponent(TakeofHotlist))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(Int_GameID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(Date_DateAdded, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(266, 266, 266)
                        .addComponent(Cmd_Save)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Int_GameID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Date_DateAdded, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cmd_Save))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Int_PlayCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(PutonHotlist)
                    .addComponent(TakeofHotlist))
                .addGap(0, 250, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hotlist", jPanel1);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Developer.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Player.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Administrator.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try{
            String URL ="http://news.cnet.com/security/";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try{
            String URL ="http://www.favbrowser.com/";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void JavaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JavaActionPerformed
        try{
            String URL ="http://java.dzone.com/";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_JavaActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        /*try{
            String query = "select date,logout from";
            JDBCCategoryDataset dataset = new JDBCCategoryDataset(WebsiteInformationBuilder.mySQLConnection(), query);
            JFreeChart chart = ChartFactory.createLineChart("Logout Stats", "LogOut", "Date", dataset, PlotOrientation.VERTICAL, false, true, false);
            BarRenderer renderer = null;
            CategoryPlot plot = null;
            renderer = new BarRenderer();
            ChartFrame frame = new ChartFrame("Login Stats", chart);
            frame.setVisible(true);
            frame.setSize(400,650);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }*/
    }//GEN-LAST:event_LogoutActionPerformed

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
       /* try{
            String query = "select date,login from";
            JDBCCategoryDataset dataset = new JDBCCategoryDataset(WebsiteInformationBuilder.mySQLConnection(), query);
            JFreeChart chart = ChartFactory.createLineChart("Login Stats", "Login", "Date", dataset, PlotOrientation.VERTICAL, false, true, false);
            BarRenderer renderer = null;
            CategoryPlot plot = null;
            renderer = new BarRenderer();
            ChartFrame frame = new ChartFrame("Login Stats", chart);
            frame.setVisible(true);
            frame.setSize(400,650);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }*/

        // TODO add your handling code here:
    }//GEN-LAST:event_LoginActionPerformed

    private void Cmd_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cmd_SaveActionPerformed
        // TODO add your handling code here:
        System.out.println("The Update button was clicked");
        try{
            System.out.println("Prepareing message " + " hotlist " + hotListQuery + " " + selectedGameID);
            serverWriter.format("%s %s %s", "hotlist", hotListQuery, selectedGameID);
            serverWriter.flush();
            
            
//            if(connected) {
//            System.out.println("Connected to client. Listening for input");
//            if(serverReader.hasNextLine()) {
//                Games.getModel().setValueAt(serverReader.nextLine(), gameTableSelectedRow, 3);
//            }
//                int x = 0;
//                x++;
//                if(x > 10) {
 //    Make a loop that will increment x.......
//                    break;
//                }
//            }
//            serverWriter.format("%s\n", "initialize");
//            serverWriter.flush();
            // have to make sure the update button is outside of the loop..... Try using an if statement to replace the while loop.
            
           System.out.println("Message Sent");
        }
        catch(Exception e){

            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_Cmd_SaveActionPerformed

    private void TakeofHotlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TakeofHotlistActionPerformed
        // TODO add your handling code here:
        try{
           hotListQuery = "0 " + selectedGameID;
        }
        catch(Exception e){

            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_TakeofHotlistActionPerformed

    private void PutonHotlistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PutonHotlistActionPerformed
        // TODO add your handling code here:
      try{
           hotListQuery = "1 " + selectedGameID;
        }
        catch(Exception e){

            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_PutonHotlistActionPerformed

    private void GamesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GamesMouseClicked
       try{
            gameTableSelectedRow = Games.getSelectedRow();
            String Table_click = (Games.getModel().getValueAt(gameTableSelectedRow, 0).toString());
                Int_GameID.setText(Games.getModel().getValueAt(gameTableSelectedRow, 0).toString());
                selectedGameID = Integer.parseInt(Int_GameID.getText());
                Date_DateAdded.setText(Games.getModel().getValueAt(gameTableSelectedRow, 1).toString());
                Int_PlayCount.setText(Games.getModel().getValueAt(gameTableSelectedRow, 2).toString());
                if(Games.getModel().getValueAt(gameTableSelectedRow, 3).toString().equalsIgnoreCase("true")) {
                    PutonHotlist.setSelected(true);
                    TakeofHotlist.setSelected(false);
                } else {
                    PutonHotlist.setSelected(false);
                    TakeofHotlist.setSelected(true);
                }


        }// Taking zero because i want consider other fields
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_GamesMouseClicked

    private void gamesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gamesMouseClicked
        try{
                        String URL ="http://146.57.164.38/NewGameCheckout.php?game=3";
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
        }        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_gamesMouseClicked
//This is to add uopdated information in to the database like my radio button
    // this is to show information from the table into my text field to know which one you are changing.
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrozenSpearAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrozenSpearAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrozenSpearAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrozenSpearAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrozenSpearAdmin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame Admin;
    private javax.swing.JFrame Administrator;
    private javax.swing.JButton Cmd_Save;
    private javax.swing.JTextField Date_DateAdded;
    private javax.swing.JInternalFrame Develop;
    private javax.swing.JFrame Developer;
    private javax.swing.JProgressBar DeveloperRatio;
    private javax.swing.JTable Games;
    private javax.swing.JInternalFrame Information;
    private javax.swing.JTextField Int_GameID;
    private javax.swing.JTextField Int_PlayCount;
    private javax.swing.JButton Java;
    private javax.swing.JButton Login;
    private javax.swing.JButton Logout;
    private javax.swing.JInternalFrame Overallstatistics1;
    private javax.swing.JInternalFrame Play;
    private javax.swing.JFrame Player;
    private javax.swing.JRadioButton PutonHotlist;
    private javax.swing.JRadioButton TakeofHotlist;
    private javax.swing.JTable admin;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTable databasehistory;
    private javax.swing.JTable developer;
    private javax.swing.JTable games;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JInternalFrame jInternalFrame6;
    private javax.swing.JInternalFrame jInternalFrame7;
    private javax.swing.JInternalFrame jInternalFrame8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable loginHistroy;
    private javax.swing.JTable play;
    private javax.swing.JProgressBar playerratio;
    // End of variables declaration//GEN-END:variables

    private String hotlist;
}
