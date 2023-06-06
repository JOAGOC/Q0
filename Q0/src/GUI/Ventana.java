package GUI;

import Analizadores.AnalizadorLéxico;
import Componentes.NumeroLinea;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import Analizadores.AnalizadorSintáctico;

public class Ventana extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jDialog1 = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        txtTiempoAG = new javax.swing.JTextField();
        javax.swing.JSplitPane jSplitPane2 = new javax.swing.JSplitPane();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        btnAnalizar = new javax.swing.JButton();
        javax.swing.JSplitPane jSplitPane1 = new javax.swing.JSplitPane();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane4 = new javax.swing.JScrollPane();
        txtTokens = new javax.swing.JTextArea();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        txtSalida = new javax.swing.JTextArea();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        CheckAutoguardado = new javax.swing.JCheckBoxMenuItem();
        btnAbrir = new javax.swing.JMenuItem();
        btnGuardar = new javax.swing.JMenuItem();
        btnGuardarComo = new javax.swing.JMenuItem();
        btnConfiguración = new javax.swing.JMenuItem();

        jDialog1.setLocation(getLocation());
        jDialog1.setMinimumSize(new java.awt.Dimension(335, 124));
        jDialog1.setModal(true);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel3.setText("Configuración");

        jLabel4.setText("<html><center>Tiempo de<br>Autoguardado (s)");

        jSlider1.setMaximum(60);
        jSlider1.setMinimum(1);

        txtTiempoAG.setColumns(4);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTiempoAG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtTiempoAG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.7);
        jSplitPane2.setMinimumSize(new java.awt.Dimension(0, 0));
        jSplitPane2.setPreferredSize(new java.awt.Dimension(0, 0));

        jPanel3.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 99;
        gridBagConstraints.ipady = 287;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        btnAnalizar.setText("Analizar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnAnalizar, gridBagConstraints);

        jSplitPane2.setLeftComponent(jPanel3);

        jSplitPane1.setBorder(null);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(0, 0));
        jSplitPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        jPanel2.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane4.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(0, 0));

        txtTokens.setColumns(20);
        txtTokens.setRows(5);
        jScrollPane4.setViewportView(txtTokens);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane4, gridBagConstraints);

        jLabel2.setText("Tokens");
        jPanel2.add(jLabel2, new java.awt.GridBagConstraints());

        jSplitPane1.setTopComponent(jPanel2);

        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(0, 0));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(0, 0));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(0, 0));

        txtSalida.setColumns(20);
        txtSalida.setRows(5);
        jScrollPane2.setViewportView(txtSalida);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane2, gridBagConstraints);

        jLabel1.setText("Salida");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jSplitPane1.setRightComponent(jPanel1);

        jSplitPane2.setRightComponent(jSplitPane1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jSplitPane2, gridBagConstraints);

        jMenu1.setText("File");

        CheckAutoguardado.setSelected(true);
        CheckAutoguardado.setText("Autoguardado");
        jMenu1.add(CheckAutoguardado);

        btnAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        btnAbrir.setText("Abrir");
        jMenu1.add(btnAbrir);

        btnGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        btnGuardar.setText("Guardar");
        jMenu1.add(btnGuardar);

        btnGuardarComo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        btnGuardarComo.setText("Guardar Como...");
        jMenu1.add(btnGuardarComo);

        btnConfiguración.setText("Configuración");
        jMenu1.add(btnConfiguración);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(431, 368));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    AnalizadorLéxico analizadorL;
    AnalizadorSintáctico analizadorS;
    NumeroLinea nm;
    String rutaDocumento = System.getProperty("user.dir") + "\\" + "Archivo.cato";
    private int AUTO_SAVE_DELAY = 1500;
    private DocumentListener autosaveListener;
    private static Timer autoSaveTimer = new Timer();

    public Ventana() {
        initComponents();

        File f;
        if ((f = new File(rutaDocumento)).exists())
            cargarDocumento(f);

        // Enumerador de línea
        nm = new NumeroLinea(textArea);
        jScrollPane1.setRowHeaderView(nm);

        // Evento de analizar
        btnAnalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analizar(e);
            }
        });

        // Evento de autoguardado
        CheckAutoguardado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (CheckAutoguardado.isSelected()) {
                    escucharAutoguardado();
                    guardar();
                } else
                    textArea.getDocument().removeDocumentListener(autosaveListener);

            }
        });
        CheckAutoguardado.getItemListeners()[0].itemStateChanged(null);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        btnGuardarComo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarComo();
            }
        });

        btnAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrir();
            }
        });

        jSlider1.addChangeListener(new ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                txtTiempoAG.setText((jSlider1.getValue())+"");
                AUTO_SAVE_DELAY = jSlider1.getValue()*1000;
            };
        });
        jSlider1.setValue(AUTO_SAVE_DELAY/1000);
        
        txtTiempoAG.addKeyListener(new KeyListenerP() {
            @Override
            public void keyReleased(KeyEvent e) {
                sincronizarSlider();
            }

            private void sincronizarSlider() {
                try {
                    if (txtTiempoAG.getText().equals(""))
                        return;
                    jSlider1.setValue(Integer.parseInt(txtTiempoAG.getText()));
                    AUTO_SAVE_DELAY = jSlider1.getValue()*1000;
                } catch (Exception e) {
                    jSlider1.setValue(30);
                }
            }
        });
        
        btnConfiguración.addActionListener(e -> {
            jDialog1.setLocationRelativeTo(null);
            jDialog1.setVisible(true);
            //jDialog1.setLocation(10,10);
        });
    }

    interface KeyListenerP extends KeyListener{
        default void keyReleased(KeyEvent e) {}
        default void keyTyped(KeyEvent e) {}
        default void keyPressed(KeyEvent e) {}
    }

    private void abrir() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Cato (*.cato)", "cato");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(rutaDocumento));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            cargarDocumento(file);
        }
    }

    private void cargarDocumento(File file) {
        rutaDocumento = file.getAbsolutePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            // Cargar el contenido en el JTextArea
            textArea.setText(content.toString());
            System.out.println("Archivo cargado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e.getMessage());
        }
    }

    private void guardarComo() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Cato (*.cato)", "cato");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(rutaDocumento));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getAbsolutePath().endsWith(".cato")) {
                file = new File(file.getAbsolutePath() + ".cato");
            }
            rutaDocumento = file.getAbsolutePath();
            guardar();
        }
    }

    private void escucharAutoguardado() {
        textArea.getDocument().addDocumentListener(autosaveListener = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                resetearAutosave();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                resetearAutosave();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                resetearAutosave();
            }
        });
    }

    private void resetearAutosave() {
        new Runnable() {
            public void run() {
                detenerAutosave();
                iniciarAutosave();
            }
        }.run();
    }

    private void iniciarAutosave() {
        autoSaveTimer = new Timer();
        Ventana v = this;
        TimerTask autoSaveTask = new TimerTask() {
            @Override
            public void run() {
                v.guardar();
                cancel();
            }
        };

        autoSaveTimer.schedule(autoSaveTask, AUTO_SAVE_DELAY);
    }

    private static void detenerAutosave() {
        autoSaveTimer.cancel();
        autoSaveTimer.purge();
    }

    private void guardar() {
        try (PrintWriter escritor = new PrintWriter(rutaDocumento)) {
            escritor.println(textArea.getText());
            // Puedes escribir más contenido si lo deseas
            escritor.flush(); // Asegúrate de guardar los cambios
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
        }
        System.out.println("Guardado");
    }

    private void analizar(ActionEvent e) {
        (analizadorL = new AnalizadorLéxico(textArea.getText())).analizar();
        txtSalida.setText(analizadorL.errores.isEmpty() ? " /\\_/\\\n( o.o )\n> ^ <\nFelicidades!. Ha compilado exitosamente!":analizadorL.getErrores());
        txtTokens.setText(analizadorL.tokens.toString());
        (analizadorS = new AnalizadorSintáctico(analizadorL)).parse();
        txtSalida.setText(txtSalida.getText() + "\n\n" + analizadorS.getErrores());
    }

    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatIntelliJLaf());
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem CheckAutoguardado;
    private javax.swing.JMenuItem btnAbrir;
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JMenuItem btnConfiguración;
    private javax.swing.JMenuItem btnGuardar;
    private javax.swing.JMenuItem btnGuardarComo;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextArea txtSalida;
    private javax.swing.JTextField txtTiempoAG;
    private javax.swing.JTextArea txtTokens;
    // End of variables declaration//GEN-END:variables
}