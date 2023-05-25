import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class MiVentana extends JFrame {
        private JTextArea textArea1;
        private JTextArea textArea2;
        private JTextArea textArea3;
        private JButton button;
        private AnalizadorLéxico analizador;
    
        public MiVentana() {
            // Configurar la ventana
            setTitle("Mi Ventana");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
    
            // Crear JTextAreas
            textArea1 = new JTextArea();
            JScrollPane scrollPane1 = new JScrollPane(textArea1);
            scrollPane1.setBounds(20, 20, 150, 100);
            add(scrollPane1);
    
            textArea2 = new JTextArea();
            JScrollPane scrollPane2 = new JScrollPane(textArea2);
            scrollPane2.setBounds(180, 20, 150, 100);
            add(scrollPane2);
    
            textArea3 = new JTextArea();
            JScrollPane scrollPane3 = new JScrollPane(textArea3);
            scrollPane3.setBounds(20, 140, 310, 100);
            add(scrollPane3);
    
            // Crear JButton
            button = new JButton("Aceptar");
            button.setBounds(140, 250, 120, 30);
            add(button);
    
            // Mostrar la ventana
            setVisible(true);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    (analizador = new AnalizadorLéxico(textArea3.getText())).analizar();
                    String errores = "";
                    for (String err :analizador.errores){
                        errores += err+"\n";
                    }
                    textArea1.setText(errores);
                    String símbolos = analizador.tabla.getDataVector().toString();
                    textArea2.setText(símbolos.replace("], [", "]\n["));
                }
            });
        }
    }