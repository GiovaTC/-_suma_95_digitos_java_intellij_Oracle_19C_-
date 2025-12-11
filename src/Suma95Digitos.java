/*
Suma95Digitos.java
Aplicación Java Swing completa para IntelliJ.
Registra 95 dígitos, calcula sumas cada 8 números (+5),
muestra todo el procedimiento y registra en Oracle 19c usando un Stored Procedure.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Suma95Digitos extends JFrame {

    private JTextArea txtAreaProceso;
    private JButton btnCalcular;
    private JButton btnGuardar;
    private JTextField txtNumeros[];
    private int resultadoFinal = 0;
    private String procedimientoTexto = "";

    public Suma95Digitos(){
        setTitle("Suma de 95 digitos con procedimiento");
        setSize(900,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(10,10));

        txtNumeros = new JTextField[95];
        for(int i = 0; i < 95; i++){
            txtNumeros[i] = new JTextField();
            txtNumeros[i].setBorder(BorderFactory.createTitledBorder("N" + (i + 1)));
            panelInput.add(txtNumeros[i]);
        }

        txtAreaProceso = new JTextArea();
        txtAreaProceso.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtAreaProceso);

        JPanel panelBotones = new JPanel();
        btnCalcular = new JButton("Calcular Procedimiento");
        btnGuardar = new JButton("Guardar en Oracle (SP)");
        btnGuardar.setEnabled(false);

        panelBotones.add(btnCalcular);
        panelBotones.add(btnGuardar);

        add(panelInput, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnCalcular.addActionListener(this::calcularOperacion);
        btnGuardar.addActionListener(this::guardarOracle);
    }

    private void calcularOperacion(ActionEvent e) {

        List<Integer> lista = new ArrayList<>();
        procedimientoTexto = "";
        resultadoFinal = 0;

        try {
            // leer los 95 números
            for(int i = 0; i < 95; i++){
                int val = Integer.parseInt(txtNumeros[i].getText().trim());
                lista.add(val);
            }

            procedimientoTexto += "=== PROCESO DE SUMA ===\n";
            int parcial = 0;
            int contador = 0;

            for (int i = 0; i < lista.size(); i++) {
                parcial += lista.get(i);
                contador++;

                procedimientoTexto += "N" + (i + 1) + ": " + lista.get(i) + " -> Parcial: " + parcial + "\n";

                if (contador == 8) {
                    procedimientoTexto += "Se completo un bloque de 8 numeros. Suma parcial: " + parcial + "\n";
                    procedimientoTexto += "Se suma +5 adicional -> " + (parcial + 5) + "\n";
                    resultadoFinal += parcial + 5;
                    procedimientoTexto += "Acumulado total: " + resultadoFinal + "\n\n";

                    parcial = 0;
                    contador = 0;
                }
            }
            // si sobran numeros despues de grupos de 8
                if (contador == 0) {
                    procedimientoTexto += "Bloque final incompleto. Suma: " + parcial + "\n";
                    resultadoFinal += parcial;
                }

                procedimientoTexto += "\nRESULTADO FINAL: " + resultadoFinal;

                txtAreaProceso.setText(procedimientoTexto);
                btnGuardar.setEnabled(true);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingrese valores numéricos válidos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }   

    private void guardarOracle(ActionEvent e) {

    }


    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}