package proyecto2ed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Stack;

public class Main extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stack<Producto> pilaProductos = new Stack<>();
    private JTextArea areaTexto;

    public Main() {
        // Configurar la ventana principal
        setTitle("Gestor de Productos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes de la GUI
        JButton botonAgregar = new JButton("Agregar Producto");
        JButton botonVerLista = new JButton("Ver Lista de Productos");
        JButton botonEliminarVencidos = new JButton("Eliminar Productos Vencidos");
        areaTexto = new JTextArea();

        // Configurar el diseño
        setLayout(new GridLayout(4, 1));
        add(botonAgregar);
        add(botonVerLista);
        add(botonEliminarVencidos);
        add(new JScrollPane(areaTexto));

        // Configurar acciones de los botones
        botonAgregar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        botonVerLista.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                verListaProductos();
            }
        });

        botonEliminarVencidos.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                eliminarProductosVencidos();
            }
        });
    }

    private void agregarProducto() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del producto:");
        String fechaCaducidadStr = JOptionPane.showInputDialog("Ingrese la fecha de caducidad (YYYY-MM-DD):");

        try {
            LocalDate fechaCaducidad = LocalDate.parse(fechaCaducidadStr);
            Producto producto = new Producto(nombre, fechaCaducidad);
            pilaProductos.push(producto);
            JOptionPane.showMessageDialog(this, "Producto agregado con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar el producto. Verifique la fecha en el formato correcto.");
        }
    }

    private void verListaProductos() {
        areaTexto.setText("");
        for (Producto producto : pilaProductos) {
            areaTexto.append(producto.getNombre() + " - Caduca el " + producto.getFechaCaducidad() + "\n");
        }
    }

    private void eliminarProductosVencidos() {
        Stack<Producto> nuevaPila = new Stack<>();
        LocalDate fechaActual = LocalDate.now();

        while (!pilaProductos.isEmpty()) {
            Producto producto = pilaProductos.pop();
            if (producto.getFechaCaducidad().isAfter(fechaActual)) {
                nuevaPila.push(producto);
            }
        }

        pilaProductos = nuevaPila;
        JOptionPane.showMessageDialog(this, "Productos vencidos eliminados.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}

class Producto {
    private String nombre;
    private LocalDate fechaCaducidad;

    public Producto(String nombre, LocalDate fechaCaducidad) {
        this.nombre = nombre;
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }
}