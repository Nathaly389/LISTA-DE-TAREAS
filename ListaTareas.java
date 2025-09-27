import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ListaTareas extends JFrame {
    private DefaultListModel<String> modeloTareas;
    private JList<String> listaTareas;
    private JTextField campoTarea;
    private JButton btnAñadir, btnCompletar, btnEliminar;

    public ListaTareas() {
        setTitle("Lista de Tareas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloTareas = new DefaultListModel<>();
        listaTareas = new JList<>(modeloTareas);
        listaTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTareas.setCellRenderer(new TareaRenderer());

        campoTarea = new JTextField(20);

        btnAñadir = new JButton("Añadir Tarea");
        btnCompletar = new JButton("Marcar como Completada");
        btnEliminar = new JButton("Eliminar Tarea");

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(campoTarea);
        panelSuperior.add(btnAñadir);

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnCompletar);
        panelInferior.add(btnEliminar);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(listaTareas), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        btnAñadir.addActionListener(e -> añadirTarea());
        btnCompletar.addActionListener(e -> completarTarea());
        btnEliminar.addActionListener(e -> eliminarTarea());
        campoTarea.addActionListener(e -> añadirTarea());

        listaTareas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    completarTarea();
                }
            }
        });
    }

    private void añadirTarea() {
        String tarea = campoTarea.getText().trim();
        if (!tarea.isEmpty()) {
            modeloTareas.addElement(tarea);
            campoTarea.setText("");
        }
    }

    private void completarTarea() {
        int index = listaTareas.getSelectedIndex();
        if (index != -1) {
            String tarea = modeloTareas.getElementAt(index);
            if (!tarea.startsWith("✓ ")) {
                modeloTareas.set(index, "✓ " + tarea);
            }
        }
    }

    private void eliminarTarea() {
        int index = listaTareas.getSelectedIndex();
        if (index != -1) {
            modeloTareas.remove(index);
        }
    }

    private class TareaRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String tarea = value.toString();
            if (tarea.startsWith("✓ ")) {
                label.setForeground(Color.GRAY);
                label.setText("<html><strike>" + tarea.substring(2) + "</strike></html>");
            } else {
                label.setForeground(Color.BLACK);
            }
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListaTareas().setVisible(true));
    }
}
