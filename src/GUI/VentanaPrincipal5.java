package GUI;

import Model.Buceador;
import Model.Inmersion;
import Model.Nivel;
import Model.Reserva;
import Services.SistemaReservas;
import Utils.FicheroUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;

public class VentanaPrincipal5 extends JFrame {

    // ── Paleta ───────────────────────────────────────────────────────
    private static final Color AZUL_PROFUNDO  = new Color(2,   27,  46);
    private static final Color AZUL_MEDIO     = new Color(6,   90, 130);
    private static final Color AZUL_CLARO     = new Color(28, 114, 147);
    private static final Color AZUL_SUAVE     = new Color(220, 240, 248);
    private static final Color NARANJA        = new Color(234, 124,  10);
    private static final Color NARANJA_HOVER  = new Color(255, 152,  30);
    private static final Color BLANCO         = new Color(255, 255, 255);
    private static final Color GRIS_FONDO     = new Color(245, 248, 252);
    private static final Color GRIS_TABLA     = new Color(230, 238, 245);
    private static final Color TEXTO_OSCURO   = new Color(15,  35,  55);
    private static final Color TEXTO_SUAVE    = new Color(90, 120, 145);
    private static final Color VERDE_EXITO    = new Color(39, 174,  96);
    private static final Color ROJO_ERROR     = new Color(192, 57,  43);

    private SistemaReservas sistema;
    private JPanel panelCentral;
    private CardLayout cardLayout;

    private DefaultTableModel modeloBuceadores;
    private DefaultTableModel modeloInmersiones;
    private DefaultTableModel modeloReservas;

    private JComboBox<String> comboBuceadoresReserva;
    private JComboBox<String> comboInmersionesReserva;

    private JLabel lblTotalBuceadores;
    private JLabel lblTotalInmersiones;
    private JLabel lblTotalReservas;

    public VentanaPrincipal5() {
        sistema = new SistemaReservas();
        cargarDatosEjemplo();

        setTitle("🤿  Sistema de Reservas de Buceo");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(GRIS_FONDO);

        add(crearCabecera(),    BorderLayout.NORTH);
        add(crearMenuLateral(), BorderLayout.WEST);

        cardLayout   = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(GRIS_FONDO);

        // ── modeloReservas se inicializa aquí antes de crear las pantallas ──
        modeloReservas = new DefaultTableModel(
            new String[]{"Buceador", "Inmersión reservada", "Fecha de reserva"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        panelCentral.add(crearPantallaBuceadores(), "BUCEADORES");
        panelCentral.add(crearPantallaInmersiones(), "INMERSIONES");
        panelCentral.add(crearPantallaReservas(),   "RESERVAS");
        panelCentral.add(crearPantallaDashboard(),  "DASHBOARD");

        add(panelCentral, BorderLayout.CENTER);
    }

    // ── Cabecera ─────────────────────────────────────────────────────
    private JPanel crearCabecera() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AZUL_PROFUNDO);
        panel.setPreferredSize(new Dimension(1100, 60));
        panel.setBorder(new EmptyBorder(0, 25, 0, 25));

        JLabel titulo = new JLabel("🤿  Sistema de Reservas de Buceo");
        titulo.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        titulo.setForeground(BLANCO);

        JLabel subtitulo = new JLabel("Panel de gestión");
        subtitulo.setFont(new Font("Calibri", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(158, 200, 218));
        subtitulo.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(titulo,    BorderLayout.WEST);
        panel.add(subtitulo, BorderLayout.EAST);
        return panel;
    }

    // ── Menú lateral ─────────────────────────────────────────────────
    private JPanel crearMenuLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AZUL_PROFUNDO);
        panel.setPreferredSize(new Dimension(210, 0));
        panel.setBorder(new EmptyBorder(25, 12, 25, 12));

        JLabel lblNav = new JLabel("NAVEGACIÓN");
        lblNav.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
        lblNav.setForeground(new Color(120, 160, 185));
        lblNav.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblNav.setBorder(new EmptyBorder(0, 5, 12, 0));

        JButton btnDashboard   = crearBotonMenu("  🏠  Inicio",        false);
        JButton btnBuceadores  = crearBotonMenu("  👤  Buceadores",     false);
        JButton btnInmersiones = crearBotonMenu("  🌊  Inmersiones",    false);
        JButton btnReservas    = crearBotonMenu("  📋  Reservas",       false);
        JButton btnGuardar     = crearBotonMenu("  💾  Guardar datos",  false);
        JButton btnSalir       = crearBotonMenu("  ✖   Salir",         true);

        btnDashboard.addActionListener(e -> {
            actualizarDashboard();
            cardLayout.show(panelCentral, "DASHBOARD");
        });
        btnBuceadores.addActionListener(e  -> cardLayout.show(panelCentral, "BUCEADORES"));
        btnInmersiones.addActionListener(e -> cardLayout.show(panelCentral, "INMERSIONES"));
        btnReservas.addActionListener(e    -> cardLayout.show(panelCentral, "RESERVAS"));
        btnGuardar.addActionListener(e -> {
            FicheroUtils.guardarReservas(sistema.getReservas());
            JOptionPane.showMessageDialog(this,
                "Datos guardados en reservas.txt", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        });
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(lblNav);
        panel.add(btnDashboard);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnBuceadores);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnInmersiones);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnReservas);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnGuardar);
        panel.add(Box.createVerticalGlue());
        panel.add(btnSalir);

        return panel;
    }

    // ── Dashboard ────────────────────────────────────────────────────
    private JPanel crearPantallaDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titulo = new JLabel("Panel de Control");
        titulo.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
        titulo.setForeground(TEXTO_OSCURO);
        titulo.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 20, 0));
        panelTarjetas.setBackground(GRIS_FONDO);
        panelTarjetas.setPreferredSize(new Dimension(0, 130));

        lblTotalBuceadores  = new JLabel("0", SwingConstants.CENTER);
        lblTotalInmersiones = new JLabel("0", SwingConstants.CENTER);
        lblTotalReservas    = new JLabel("0", SwingConstants.CENTER);

        panelTarjetas.add(crearTarjeta("👤  Buceadores",  lblTotalBuceadores,  AZUL_MEDIO));
        panelTarjetas.add(crearTarjeta("🌊  Inmersiones", lblTotalInmersiones, AZUL_CLARO));
        panelTarjetas.add(crearTarjeta("📋  Reservas",    lblTotalReservas,    NARANJA));

        JLabel lblRecientes = new JLabel("Reservas recientes");
        lblRecientes.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        lblRecientes.setForeground(TEXTO_OSCURO);
        lblRecientes.setBorder(new EmptyBorder(20, 0, 10, 0));

        // Usa directamente modeloReservas — ya está inicializado en el constructor
        JTable tablaRecientes = crearTabla(modeloReservas);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(GRIS_FONDO);
        centro.add(lblRecientes,                    BorderLayout.NORTH);
        centro.add(new JScrollPane(tablaRecientes), BorderLayout.CENTER);

        panel.add(titulo,        BorderLayout.NORTH);
        panel.add(panelTarjetas, BorderLayout.CENTER);
        panel.add(centro,        BorderLayout.SOUTH);

        return panel;
    }

    // ── Buceadores ───────────────────────────────────────────────────
    private JPanel crearPantallaBuceadores() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        panel.add(crearTituloPantalla("👤  Gestión de Buceadores",
            "Registra y consulta los buceadores del sistema"), BorderLayout.NORTH);

        JPanel tarjetaForm = crearTarjetaBlanca();
        tarjetaForm.setLayout(new BorderLayout());
        tarjetaForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblForm = new JLabel("Nuevo buceador");
        lblForm.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblForm.setForeground(AZUL_MEDIO);
        lblForm.setBorder(new EmptyBorder(0, 0, 12, 0));

        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        formulario.setBackground(BLANCO);

        JTextField txtNombre = crearCampoTexto("Nombre del buceador", 18);
        JComboBox<Nivel> comboNivel = new JComboBox<>(Nivel.values());
        estilizarCombo(comboNivel);
        JButton btnRegistrar = crearBotonAccion("+ Registrar", AZUL_MEDIO);
        JLabel lblMsg = new JLabel("");
        lblMsg.setFont(new Font("Calibri", Font.ITALIC, 12));

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Nivel:"));
        formulario.add(comboNivel);
        formulario.add(btnRegistrar);
        formulario.add(lblMsg);

        tarjetaForm.add(lblForm,    BorderLayout.NORTH);
        tarjetaForm.add(formulario, BorderLayout.CENTER);

        String[] cols = {"Nombre", "Nivel de certificación"};
        modeloBuceadores = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = crearTabla(modeloBuceadores);

        JPanel tarjetaTabla = crearTarjetaBlanca();
        tarjetaTabla.setLayout(new BorderLayout());
        tarjetaTabla.setBorder(new EmptyBorder(15, 15, 15, 15));
        JLabel lblTabla = new JLabel("Buceadores registrados");
        lblTabla.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        lblTabla.setForeground(TEXTO_SUAVE);
        lblTabla.setBorder(new EmptyBorder(0, 0, 10, 0));
        tarjetaTabla.add(lblTabla,              BorderLayout.NORTH);
        tarjetaTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            Nivel nivel   = (Nivel) comboNivel.getSelectedItem();
            try {
                sistema.registrarBuceador(new Buceador(nombre, nivel));
                txtNombre.setText("");
                lblMsg.setForeground(VERDE_EXITO);
                lblMsg.setText("✔ Registrado correctamente");
                actualizarTodo();
            } catch (IllegalArgumentException ex) {
                lblMsg.setForeground(ROJO_ERROR);
                lblMsg.setText("✘ " + ex.getMessage());
            }
        });

        JPanel centro = new JPanel(new BorderLayout(0, 15));
        centro.setBackground(GRIS_FONDO);
        centro.add(tarjetaForm,  BorderLayout.NORTH);
        centro.add(tarjetaTabla, BorderLayout.CENTER);

        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // ── Inmersiones ──────────────────────────────────────────────────
    private JPanel crearPantallaInmersiones() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        panel.add(crearTituloPantalla("🌊  Inmersiones disponibles",
            "Consulta el catálogo de inmersiones y sus plazas"), BorderLayout.NORTH);

        String[] cols = {"Punto de inmersión", "Fecha", "Profundidad", "Plazas máx.", "Disponibles", "Nivel requerido"};
        modeloInmersiones = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = crearTabla(modeloInmersiones);

        JPanel tarjeta = crearTarjetaBlanca();
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBorder(new EmptyBorder(15, 15, 15, 15));
        JLabel lbl = new JLabel("Catálogo de inmersiones");
        lbl.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        lbl.setForeground(TEXTO_SUAVE);
        lbl.setBorder(new EmptyBorder(0, 0, 10, 0));
        tarjeta.add(lbl,                    BorderLayout.NORTH);
        tarjeta.add(new JScrollPane(tabla), BorderLayout.CENTER);

        panel.add(tarjeta, BorderLayout.CENTER);
        return panel;
    }

    // ── Reservas ─────────────────────────────────────────────────────
    private JPanel crearPantallaReservas() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(GRIS_FONDO);
        panel.setBorder(new EmptyBorder(25, 25, 25, 25));

        panel.add(crearTituloPantalla("📋  Gestión de Reservas",
            "Realiza y consulta las reservas de inmersiones"), BorderLayout.NORTH);

        JPanel tarjetaForm = crearTarjetaBlanca();
        tarjetaForm.setLayout(new BorderLayout());
        tarjetaForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblForm = new JLabel("Nueva reserva");
        lblForm.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblForm.setForeground(NARANJA);
        lblForm.setBorder(new EmptyBorder(0, 0, 12, 0));

        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        formulario.setBackground(BLANCO);

        comboBuceadoresReserva  = new JComboBox<>();
        comboInmersionesReserva = new JComboBox<>();
        estilizarCombo(comboBuceadoresReserva);
        estilizarCombo(comboInmersionesReserva);

        JButton btnReservar = crearBotonAccion("✔ Reservar", NARANJA);
        JLabel lblMsg = new JLabel("");
        lblMsg.setFont(new Font("Calibri", Font.ITALIC, 12));

        formulario.add(new JLabel("Buceador:"));
        formulario.add(comboBuceadoresReserva);
        formulario.add(new JLabel("Inmersión:"));
        formulario.add(comboInmersionesReserva);
        formulario.add(btnReservar);
        formulario.add(lblMsg);

        tarjetaForm.add(lblForm,    BorderLayout.NORTH);
        tarjetaForm.add(formulario, BorderLayout.CENTER);

        // Usa directamente modeloReservas — ya inicializado en el constructor
        JTable tabla = crearTabla(modeloReservas);

        JPanel tarjetaTabla = crearTarjetaBlanca();
        tarjetaTabla.setLayout(new BorderLayout());
        tarjetaTabla.setBorder(new EmptyBorder(15, 15, 15, 15));
        JLabel lbl = new JLabel("Historial de reservas");
        lbl.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        lbl.setForeground(TEXTO_SUAVE);
        lbl.setBorder(new EmptyBorder(0, 0, 10, 0));
        tarjetaTabla.add(lbl,                    BorderLayout.NORTH);
        tarjetaTabla.add(new JScrollPane(tabla), BorderLayout.CENTER);

        btnReservar.addActionListener(e -> {
            int idxB = comboBuceadoresReserva.getSelectedIndex();
            int idxI = comboInmersionesReserva.getSelectedIndex();

            if (idxB < 0 || idxI < 0) {
                lblMsg.setForeground(ROJO_ERROR);
                lblMsg.setText("✘ Registra al menos un buceador primero.");
                return;
            }

            Buceador  b = sistema.getBuceadores().get(idxB);
            Inmersion i = sistema.getInmersiones().get(idxI);

            try {
                i.puedeReservar(b);
                sistema.realizarReserva(b, i);
                lblMsg.setForeground(VERDE_EXITO);
                lblMsg.setText("✔ Reserva realizada correctamente.");
                actualizarTodo();
            } catch (Exception ex) {
                lblMsg.setForeground(ROJO_ERROR);
                lblMsg.setText("✘ " + ex.getMessage());
            }
        });

        JPanel centro = new JPanel(new BorderLayout(0, 15));
        centro.setBackground(GRIS_FONDO);
        centro.add(tarjetaForm,  BorderLayout.NORTH);
        centro.add(tarjetaTabla, BorderLayout.CENTER);

        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // ── Sincronización ───────────────────────────────────────────────
    private void actualizarTodo() {
        if (modeloBuceadores  != null) modeloBuceadores.setRowCount(0);
        if (modeloInmersiones != null) modeloInmersiones.setRowCount(0);
        if (modeloReservas    != null) modeloReservas.setRowCount(0);
        if (comboBuceadoresReserva  != null) comboBuceadoresReserva.removeAllItems();
        if (comboInmersionesReserva != null) comboInmersionesReserva.removeAllItems();

        for (Inmersion inm : sistema.getInmersiones()) {
            if (modeloInmersiones != null)
                modeloInmersiones.addRow(new Object[]{
                    inm.getNombreInmersion(), inm.getFecha(),
                    inm.getProfundidad() + "m", inm.getPlazasMaximas(),
                    inm.getPlazasDisponibles(), inm.getNivelMinimo()
                });
            if (comboInmersionesReserva != null)
                comboInmersionesReserva.addItem(
                    inm.getNombreInmersion() + "  (" + inm.getNivelMinimo() + ")");
        }

        for (Buceador buc : sistema.getBuceadores()) {
            if (modeloBuceadores != null)
                modeloBuceadores.addRow(new Object[]{buc.getNombre(), buc.getNivel()});
            if (comboBuceadoresReserva != null)
                comboBuceadoresReserva.addItem(
                    buc.getNombre() + "  [" + buc.getNivel() + "]");
        }

        for (Reserva res : sistema.getReservas()) {
            if (modeloReservas != null)
                modeloReservas.addRow(new Object[]{
                    res.getBuceador().getNombre(),
                    res.getInmersion().getNombreInmersion(),
                    res.getFechaReserva()
                });
        }

        actualizarDashboard();
    }

    private void actualizarDashboard() {
        if (lblTotalBuceadores  != null) lblTotalBuceadores.setText(String.valueOf(sistema.getBuceadores().size()));
        if (lblTotalInmersiones != null) lblTotalInmersiones.setText(String.valueOf(sistema.getInmersiones().size()));
        if (lblTotalReservas    != null) lblTotalReservas.setText(String.valueOf(sistema.getReservas().size()));
    }

    // ── Helpers de UI ────────────────────────────────────────────────
    private JPanel crearTarjeta(String etiqueta, JLabel lblNumero, Color color) {
        JPanel t = new JPanel(new BorderLayout());
        t.setBackground(color);
        t.setBorder(new EmptyBorder(18, 22, 18, 22));

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        lbl.setForeground(new Color(220, 240, 255));

        lblNumero.setFont(new Font("Trebuchet MS", Font.BOLD, 36));
        lblNumero.setForeground(BLANCO);

        t.add(lbl,       BorderLayout.NORTH);
        t.add(lblNumero, BorderLayout.CENTER);
        return t;
    }

    private JPanel crearTarjetaBlanca() {
        JPanel t = new JPanel();
        t.setBackground(BLANCO);
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 225, 235), 1),
            new EmptyBorder(0, 0, 0, 0)
        ));
        return t;
    }

    private JPanel crearTituloPantalla(String titulo, String subtitulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(GRIS_FONDO);
        p.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel lTitulo = new JLabel(titulo);
        lTitulo.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        lTitulo.setForeground(TEXTO_OSCURO);

        JLabel lSub = new JLabel(subtitulo);
        lSub.setFont(new Font("Calibri", Font.PLAIN, 13));
        lSub.setForeground(TEXTO_SUAVE);

        p.add(lTitulo, BorderLayout.NORTH);
        p.add(lSub,    BorderLayout.SOUTH);
        return p;
    }

    private JTable crearTabla(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setBackground(BLANCO);
        tabla.setForeground(TEXTO_OSCURO);
        tabla.setFont(new Font("Calibri", Font.PLAIN, 13));
        tabla.setSelectionBackground(AZUL_SUAVE);
        tabla.setSelectionForeground(TEXTO_OSCURO);

        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (sel) {
                    setBackground(AZUL_SUAVE);
                } else {
                    setBackground(row % 2 == 0 ? BLANCO : GRIS_TABLA);
                }
                return this;
            }
        });

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(AZUL_PROFUNDO);
        header.setForeground(BLANCO);
        header.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(new MatteBorder(0, 0, 2, 0, NARANJA));

        return tabla;
    }

    private JButton crearBotonMenu(String texto, boolean esSalir) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setBackground(esSalir ? new Color(100, 20, 20) : AZUL_PROFUNDO);
        btn.setForeground(esSalir ? new Color(255, 120, 120) : new Color(190, 220, 235));
        btn.setFont(new Font("Calibri", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(8, 10, 8, 10));
        Color base = btn.getBackground();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(esSalir ? new Color(150, 30, 30) : AZUL_CLARO);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(base);
            }
        });
        return btn;
    }

    private JButton crearBotonAccion(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(BLANCO);
        btn.setFont(new Font("Calibri", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 32));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(NARANJA_HOVER); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(color); }
        });
        return btn;
    }

    private JTextField crearCampoTexto(String placeholder, int cols) {
        JTextField campo = new JTextField(cols);
        campo.setFont(new Font("Calibri", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 210, 230), 1),
            new EmptyBorder(4, 8, 4, 8)
        ));
        return campo;
    }

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(new Font("Calibri", Font.PLAIN, 13));
        combo.setBackground(BLANCO);
        combo.setPreferredSize(new Dimension(200, 30));
    }

    private void cargarDatosEjemplo() {
        sistema.agregarInmersion(new Inmersion("Cueva Azul",      LocalDate.of(2025, 7, 10), 25, 8,  Nivel.ADVANCED));
        sistema.agregarInmersion(new Inmersion("Arrecife del Sur", LocalDate.of(2025, 7, 15), 12, 10, Nivel.OPEN_WATER));
        sistema.agregarInmersion(new Inmersion("Pecio Hundido",    LocalDate.of(2025, 8,  1), 40, 6,  Nivel.DIVEMASTER));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal5 v = new VentanaPrincipal5();
            v.actualizarTodo();
            v.setVisible(true);
        });
    }
}
