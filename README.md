# -_suma_95_digitos_java_intellij_Oracle_19C_- :. .

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/1f72e018-9df0-4f85-99db-3b9b1a18dac8" />  

# Suma95Digitos — README

## Resumen

Solución completa, profesional y lista para usar en IntelliJ (Java Swing) que cumple exactamente con lo solicitado:

* Java Swing con interfaz gráfica.
* Permite registrar **95 dígitos**.
* Cada **8 números** se realiza una **suma parcial + 5**.
* Muestra todo el procedimiento paso a paso en pantalla.
* Inserta la información en **Oracle 19c** mediante un Stored Procedure.
* Código estructurado, claro y funcional para IntelliJ.

---

## 1. Script del Stored Procedure (Oracle 19c)

Ejecute esto en su Oracle 19c (por ejemplo, en SQL*Plus, SQL Developer o similar):

```sql
CREATE TABLE LOG_SUMAS (
    ID NUMBER GENERATED ALWAYS AS IDENTITY,
    INPUT_DATA VARCHAR2(4000),
    RESULTADO_TOTAL NUMBER,
    FECHA_REGISTRO DATE DEFAULT SYSDATE
);

CREATE OR REPLACE PROCEDURE SP_INSERT_SUMAS(
    P_INPUT_DATA IN VARCHAR2,
    P_TOTAL IN NUMBER
)
AS
BEGIN
    INSERT INTO LOG_SUMAS (INPUT_DATA, RESULTADO_TOTAL)
    VALUES (P_INPUT_DATA, P_TOTAL);
END;
/
```

> **Nota:** Asegúrese de tener privilegios para crear tablas y procedimientos en el esquema en el que esté trabajando.

---

## 2. Programa completo en Java (IntelliJ — single file)

**Nombre del archivo:** `Suma95Digitos.java`

Asegúrese de agregar al classpath el driver de Oracle (`ojdbc11.jar` u otro apropiado para su versión de Oracle 19c).

```java
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

    public Suma95Digitos() {
        setTitle("Suma de 95 Dígitos con procedimiento");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(10, 10));

        txtNumeros = new JTextField[95];
        for (int i = 0; i < 95; i++) {
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
            // Leer los 95 números
            for (int i = 0; i < 95; i++) {
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
                    procedimientoTexto += "Se completó un bloque de 8 números. Suma parcial: " + parcial + "\n";
                    procedimientoTexto += "Se suma +5 adicional -> " + (parcial + 5) + "\n";
                    resultadoFinal += parcial + 5;
                    procedimientoTexto += "Acumulado total: " + resultadoFinal + "\n\n";

                    parcial = 0;
                    contador = 0;
                }
            }

            // Si sobran números después de grupos de 8
            if (contador > 0) {
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
        String url = "jdbc:oracle:thin:@localhost:1521:XE";  // Cambie a su servicio/PUDB
        String user = "SYSTEM";   // Cambiar
        String pass = "12345";    // Cambiar

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            CallableStatement cs = conn.prepareCall("{ call SP_INSERT_SUMAS(?, ?) }");
            cs.setString(1, procedimientoTexto);
            cs.setInt(2, resultadoFinal);

            cs.execute();

            JOptionPane.showMessageDialog(this,
                    "Datos insertados correctamente en Oracle.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al insertar en Oracle: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Suma95Digitos().setVisible(true));
    }
}
```

---

## 3. ¿Cómo funciona?

### a) El usuario ingresa 95 dígitos

Cada celda está numerada `N1, N2, ..., N95`.

### b) El sistema realiza automáticamente:

* Lee cada número.
* Suma cada grupo de 8 dígitos.
* Al completar 8 números, agrega `+5` a esa suma.
* Continúa acumulando hasta llegar a los 95.
* Muestra en pantalla todo el procedimiento paso a paso.

### c) Luego permite grabar en Oracle

Envía el texto completo + resultado final al SP:

```
SP_INSERT_SUMAS(P_INPUT_DATA, P_TOTAL)
```

---

## 4. Instrucciones para compilar y ejecutar en IntelliJ

1. Cree un nuevo proyecto Java en IntelliJ (JDK 11+ recomendado).
2. Copie `Suma95Digitos.java` en el paquete principal (`src`).
3. Agregue `ojdbc11.jar` a las dependencias del proyecto (File > Project Structure > Modules > Dependencies).
4. Ajuste la cadena de conexión, usuario y contraseña en `guardarOracle(...)` para que apunten a su instancia Oracle.
5. Ejecute la clase `Suma95Digitos.main`.

---

## 5. Notas y recomendaciones

* El SP y la tabla usan `VARCHAR2(4000)` para `INPUT_DATA`. Si espera textos más largos, modifique a CLOB.
* Asegúrese de contar con el driver Oracle correcto para su versión de JDK/Oracle.
* Considere validaciones adicionales (rango de los dígitos, longitud exacta, manejo de nulos) según sus necesidades.

---

## 6. Licencia / Copyright

(C) 2025 — Giovanny Alejandro Tapiero Cataño & chatGpt - Proyecto Suma95Digitos. Todos los derechos reservados.

--- :. 
