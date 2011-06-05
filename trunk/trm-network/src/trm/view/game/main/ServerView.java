/*
 * ServerView.java
 *
 * Created on 03/06/2011, 15:47:46
 */
package trm.view.game.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import trm.net.server.Server;
import trm.net.server.game.GameManager;
import trm.net.server.game.PlayerServer;
import trm.net.server.game.RoomInf;
import trm.net.server.game.StatePlayer;

/**
 *
 * @author Marcos Paulo
 */
public class ServerView extends javax.swing.JFrame implements ActionListener {
    
    private static final String EDIT_LABEL = "editar";
    private static final String REMOVE_LABEL = "remover";
    
    private GameManager gameManager = GameManager.getPlayerManager();

    /** Creates new form ServerView */
    public ServerView() {
        initComponents();
        
        
        final JPopupMenu popMenu = new JPopupMenu("T");
//        popMenu.addPopupMenuListener(new PopupMenuListener() {
//
//            @Override
//            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
////                System.out.println(popMenu.getLocation() + ">>");
////                System.out.println(roomsTable.rowAtPoint(popMenu.getLocation()));
//            }
//
//            @Override
//            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
//                   System.out.println(popMenu.getLocation() + ">>");
//                System.out.println(roomsTable.rowAtPoint(popMenu.getLocation()));
//            }
//
//            @Override
//            public void popupMenuCanceled(PopupMenuEvent e) {
//            }
//        });
        JMenuItem editItem = new JMenuItem(EDIT_LABEL);
        JMenuItem removeItem = new JMenuItem(REMOVE_LABEL);
        
        editItem.addActionListener(this);
        removeItem.addActionListener(this);
        
        roomsTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
//                  System.out.println(e.getPoint() + ">>");
//                System.out.println(roomsTable.rowAtPoint(e.getPoint()));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getLocationOnScreen() + ">>");
                System.out.println(roomsTable.rowAtPoint(e.getPoint()));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                System.out.println(e.getPoint() + ">>");
//                System.out.println(roomsTable.rowAtPoint(e.getPoint()));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println(e.getPoint()+ ">>");
//                System.out.println(roomsTable.rowAtPoint(e.getPoint()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                System.out.println(e.getPoint()+ ">>");
//                System.out.println(roomsTable.rowAtPoint(e.getPoint()));
            }
        });
        popMenu.add(editItem);
        popMenu.add(removeItem);
        
        roomsTable.setComponentPopupMenu(popMenu);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        roomsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        playersTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        roomsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome", "Quantidade jogadores", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(roomsTable);

        jLabel1.setText("Salas de jogo:");

        playersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome ", "Sala", "Estado "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(playersTable);

        jLabel2.setText("Jogadores:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Informações do servidor");

        updateButton.setText("Atualiza");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(394, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(319, 319, 319))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(321, Short.MAX_VALUE)
                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(265, 265, 265))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(updateButton)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        
        Set<RoomInf> roomsInfs = gameManager.findAllRooms();
        
        updateRoomsTable(roomsInfs);
        updatePlayersInfs(gameManager.findAllPlayers());
    }//GEN-LAST:event_updateButtonActionPerformed
    
    private void updateRoomsTable(Set<RoomInf> roomsInfs) {
        clearTable(roomsTable);
        
        DefaultTableModel temp = (DefaultTableModel)roomsTable.getModel();
        
        for (RoomInf roomInf : roomsInfs) {
            String id = roomInf.id.toString();
            String roomName = roomInf.roomName;
            String players = roomInf.players.toString();
            String stateRoom = "iniciada";

            if (!roomInf.started) {
                stateRoom = "não iniciada";
            }

            temp.addRow(new String[]{id, roomName, players, stateRoom});
        }
    }
    
    private void updatePlayersInfs(Set<PlayerServer> players) {
        clearTable(playersTable);
        
        DefaultTableModel temp = (DefaultTableModel)playersTable.getModel();
        System.out.println(">>>>");
        for (PlayerServer player : players) {
            String id = player.getInf().getId().toString();
            String nickNameName = player.getInf().getNickName();
            String room = "---";
            String statePlayer = "jogando";
            
            if (player.getState().equals(StatePlayer.NO_PLAYING)) {
                statePlayer = "sem jogar";
                
            }


            temp.addRow(new String[]{id, nickNameName, room, statePlayer});
        }
    }

    private void clearTable(JTable table) {
        DefaultTableModel temp = (DefaultTableModel) table.getModel();

        while (table.getRowCount() > 0) {
            temp.removeRow(table.getRowCount() - 1);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new Thread(new Server(8080)).start();
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ServerView view = new ServerView();
                view.setVisible(true);
//                view.
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable playersTable;
    private javax.swing.JTable roomsTable;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        
        System.out.println(e.getActionCommand() + ">>");
        int temp = roomsTable.getSelectedColumnCount();
//        int i =
//        System.out.println(temp + "<<<");
    }
}
