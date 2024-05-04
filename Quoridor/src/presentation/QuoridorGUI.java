package src.presentation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

public class QuoridorGUI extends  JFrame{
    Dimension screenSize;
    private Dimension buttonSize = new Dimension(250,60);
    private final QuoridorGUI gui = this;
    // Menu
    private JMenuItem nuevo, abrir, guardar, cerrar;
    // Principal
    private JPanel mainPanel;
    private JButton newGameButton;
    private JButton exitGame;
    private JButton loadGame;

    // Juego
    private JPanel newGameOp;
    private JComboBox nPlayers, modes, difficulty;
    private JButton startGame, settings, customize;
    private JPanel infPlayer, startOp;
    private JTextField namePlayer;
    private JTextField namePlayer2;
    //Ajustes
    private JPanel settingsPanel;
    private JButton colorBoard, colorP1, colorP2, colorB1, colorB2, applySettings;
    // Customize window;
    private JPanel customPanel;
    private  JTextField boardSize;
    private ArrayList<JTextField> custumValuesBarriers;
    private ArrayList<JTextField> custumValuesSquares;
    private JTextField numberNormalB, numberTemporal, numberLarga, numberAliadas;
    private JButton applyCustoms;
    // Game window
    private JLabel turns;
    private JPanel gamePanel;
    private JPanel boardPanel;
    private JButton homeButton;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton changeSizeButton;
    private JButton reStartButton;
    private QuoridorGUI()  {
        super("Quoridor");
        custumValuesBarriers = new ArrayList<>();
        custumValuesSquares = new ArrayList<>();
        prepareElements();
        prepareActions();
    }
    private void prepareElements(){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        prepareMenuElements();
        prepareMainWindowElements();
    }
    private void prepareActions(){
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent event){
                int option = JOptionPane.showConfirmDialog(gui, "¿Estás seguro de que quieres cerrar?", "Cofirmar cierre", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    gui.setVisible(false);
                    System.exit(0);
                }
            }
        });
        prepareMenuActions();
        prepareMainWindowActions();
    }
    private void prepareMenuElements(){
        // Botones-Opciones Menu
        JMenuBar menuBar;
        JMenu opciones;
        menuBar = new JMenuBar();
        opciones = new JMenu("Archivo");
        setJMenuBar(menuBar);
        nuevo = new JMenuItem("Nuevo");
        opciones.add(nuevo);
        opciones.addSeparator();
        abrir = new JMenuItem("Abrir");
        opciones.add(abrir);
        opciones.addSeparator();
        guardar = new JMenuItem("Guardar");
        opciones.add(guardar);
        opciones.addSeparator();
        cerrar = new JMenuItem("Cerrar");
        opciones.add(cerrar);
        menuBar.add(opciones);
    }
    private void prepareMenuActions(){

        cerrar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ev){
                int option = JOptionPane.showConfirmDialog(gui, "¿Estás seguro de que quieres cerrar?", "Cofirmar cierre", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    gui.setVisible(false);
                    System.exit(0);
                }
            }
        });

        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser openFileChooser = new JFileChooser();
                int result = openFileChooser.showOpenDialog(gui);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = openFileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(gui, "Funcionalidad de abrir en construcción. Archivo seleccionado: " + selectedFile.getName(), "Abrir Juego", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showSaveDialog(gui);
                if(option == JFileChooser.APPROVE_OPTION){
                    java.io.File selectedFolder = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(gui, "Funcionalidad de guardar en construcción. Carpeta seleccionada: " + selectedFolder.getName(), "Guardar Juego", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void prepareMainWindowElements(){
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 0, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("QUORIDOR");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel);
        // new game button
        JPanel newGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newGamePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        newGameButton = createButton(" NEW GAME ", buttonSize);
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        exitGame = createButton("   EXIT   ", buttonSize);


        loadGame = createButton(" LOAD GAME", buttonSize);

        buttonPanel.add(loadGame);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exitGame);
        newGamePanel.add(buttonPanel);

        mainPanel.add(newGamePanel);

        add(mainPanel);
    }
    private void prepareMainWindowActions(){
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareNewGameWindowElements();
                prepareNewGameWindowActions();
                setContentPane(newGameOp);
                revalidate();
                repaint();
                nPlayers.setSelectedIndex(0);

            }
        });
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrar.doClick();
            }
        });
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar.doClick();
            }
        });
    }
    public void prepareNewGameWindowElements(){
        newGameOp = new JPanel();
        newGameOp.setLayout(new GridLayout(3, 1, 0, 10));
        newGameOp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("NEW GAME");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newGameOp.add(titleLabel);
        //Jugadores
        startOp = new JPanel();
        startOp.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 50));
        infPlayer = new JPanel();
        startOp.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        nPlayers = new JComboBox<String>();
        nPlayers.setPreferredSize(buttonSize);
        nPlayers.addItem("1 PLAYER");
        nPlayers.addItem("2 PLAYERS");
        nPlayers.setPreferredSize(buttonSize);
        nPlayers.setFont(new Font("Consolas", Font.BOLD, 30));
        nPlayers.setFont(new Font("Consolas", Font.BOLD, 30));
        nPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        infPlayer.add(nPlayers);
        newGameOp.add(infPlayer);
        //Inicio y ajustes
        startGame = createButton("START", buttonSize);
        settings = createButton("SETTINGS", buttonSize);
        customize = createButton("CUSTOMIZE", buttonSize);
        settings.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        customize.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficulty = new JComboBox<String>();
        difficulty.setPreferredSize(buttonSize);
        difficulty.setFont(new Font("Consolas", Font.BOLD, 30));
        difficulty.addItem("NORMAL");
        difficulty.addItem("TIME TRIAL");
        difficulty.addItem("TIMED");
        difficulty.setPreferredSize(buttonSize);


        startOp.add(startGame);
        startOp.add(new Label());
        startOp.add(settings);
        startOp.add(new Label());
        startOp.add(customize);
        newGameOp.add(startOp);
    }
    public void prepareNewGameWindowActions(){
        nPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) nPlayers.getSelectedItem();
                if (selectedOption.equals("1 PLAYER")) {
                    newGameOp.remove(startOp);
                    newGameOp.add(infoPlayer());
                    newGameOp.add(startOp);
                    newGameOp.revalidate();
                    newGameOp.repaint();
                } else if (selectedOption.equals("2 PLAYERS")) {
                    newGameOp.remove(startOp);
                    newGameOp.add(infoPlayer2());
                    newGameOp.add(startOp);
                    newGameOp.revalidate();
                    newGameOp.repaint();
                }
            }
        });
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareSettingsWindowElements();
                prepareSettingsWindowActions();
                setContentPane(settingsPanel);
                revalidate();
                repaint();
            }
        });
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareStartGameWindowElements();
                prepareStartGameWindowActions();
                setContentPane(gamePanel);
                revalidate();
                repaint();
            }
        });
        customize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareCustomizeWindowElements();
                prepareCustomizeWindowActions();
                setContentPane(customPanel);
                revalidate();
                repaint();
            }
        });


    }

    private JPanel infoPlayer(){
        infPlayer.removeAll();
        infPlayer.setLayout(new GridLayout(4, 1, 0, 10));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        nPlayers.setPreferredSize(buttonSize);
        panel.add(nPlayers);
        JPanel info = new JPanel();
        info.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel nameLabel = new JLabel("NAME PLAYER");
        nameLabel.setFont(new Font("Consolas", Font.BOLD, 30));
        namePlayer = new JTextField(20);
        namePlayer.setFont(new Font("Consolas", Font.BOLD, 30));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel modos = new JPanel();
        modos.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel modsmaq = new JLabel("MODES");
        modsmaq.setPreferredSize(buttonSize);
        modsmaq.setFont(new Font("Consolas", Font.BOLD, 30));
        modes = new JComboBox<String>();
        modes.setPreferredSize(buttonSize);
        modes.setFont(new Font("Consolas", Font.BOLD, 30));
        modes.addItem("AMATEUR");
        modes.addItem("MEDIAN");
        modes.addItem("ADVANCED");
        modes.setPreferredSize(buttonSize);
        modsmaq.setAlignmentX(Component.CENTER_ALIGNMENT);
        modes.setAlignmentX(Component.CENTER_ALIGNMENT);
        modos.add(modsmaq);
        modos.add(modes);
        JPanel dificultad = new JPanel();
        dificultad.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel dificult = new JLabel("DIFFICULTY");
        dificult.setFont(new Font("Consolas", Font.BOLD, 30));
        dificult.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
        dificultad.add(dificult);
        dificultad.add(difficulty);
        infPlayer.add(panel);
        infPlayer.add(modos);
        infPlayer.add(dificultad);
        info.add(nameLabel);
        info.add(namePlayer);
        infPlayer.add(info);
        return infPlayer;
    }
    private JPanel infoPlayer2(){
        infPlayer.removeAll();
        infPlayer.setLayout(new GridLayout(4, 1, 0, 10));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        nPlayers.setPreferredSize(buttonSize);
        panel.add(nPlayers);
        JPanel player1 = new JPanel();
        player1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel nameLabel = new JLabel("NAME PLAYER");
        nameLabel.setFont(new Font("Consolas", Font.BOLD, 30));
        namePlayer = new JTextField(20);
        namePlayer.setFont(new Font("Consolas", Font.BOLD, 30));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1.add(nameLabel);
        player1.add(namePlayer);
        JPanel player2 = new JPanel();
        player2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel nameLabel2 = new JLabel("NAME PLAYER");
        nameLabel2.setFont(new Font("Consolas", Font.BOLD, 30));
        namePlayer2 = new JTextField(20);
        namePlayer2.setFont(new Font("Consolas", Font.BOLD, 30));
        nameLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer2.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2.add(nameLabel2);
        player2.add(namePlayer2);

        JPanel dificultad = new JPanel();
        dificultad.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel dificult = new JLabel("DIFFICULTY");
        dificult.setFont(new Font("Consolas", Font.BOLD, 30));
        dificult.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
        dificultad.add(dificult);
        dificultad.add(difficulty);
        infPlayer.add(panel);
        infPlayer.add(dificultad);
        infPlayer.add(player1);
        infPlayer.add(player2);
        return infPlayer;
    }
    public void prepareSettingsWindowElements() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());

        // Restricciones para el título
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = GridBagConstraints.REMAINDER;
        titleConstraints.insets = new Insets(0, 0, 20, 0);
        JLabel titleLabel = new JLabel("SETTINGS");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        settingsPanel.add(titleLabel, titleConstraints);
        // Restricciones para los botones
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 1;
        buttonConstraints.anchor = GridBagConstraints.CENTER;
        buttonConstraints.insets = new Insets(10, 0, 10, 0);
        JPanel buttons = new JPanel(new GridLayout(0, 1, 0, 10));
        colorBoard = createButton("COLOR BOARD", buttonSize);
        colorP1 = createButton("COLOR P1", buttonSize);
        colorP2 = createButton("COLOR P2", buttonSize);
        colorB1 = createButton("COLOR B1", buttonSize);
        colorB2 = createButton("COLOR B2", buttonSize);
        buttons.add(colorBoard);
        buttons.add(colorP1);
        buttons.add(colorP2);
        buttons.add(colorB1);
        buttons.add(colorB2);
        settingsPanel.add(buttons, buttonConstraints);
        // Restricciones para el botón de aplicar
        GridBagConstraints applyConstraints = new GridBagConstraints();
        applyConstraints.gridx = 0;
        applyConstraints.gridy = 2;
        applyConstraints.anchor = GridBagConstraints.SOUTHEAST; // Alineación en la esquina inferior derecha
        applyConstraints.insets = new Insets(300, (screenSize.width)-250, 0, 0); // Espacio entre los botones y el borde inferior

        applySettings = createButton("APPLY", buttonSize);
        settingsPanel.add(applySettings, applyConstraints);
    }



    public void prepareSettingsWindowActions(){
        colorBoard.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Seleccionar Color", Color.WHITE);
                if (newColor == null){
                    JOptionPane.showMessageDialog(gui, "Color no valida, vuelva a seleccionar");
                }
                else {
                    String newColorString = Integer.toString(newColor.getRGB());
                }
            }
        });
        colorP1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Seleccionar Color", Color.WHITE);
                if (newColor == null){
                    JOptionPane.showMessageDialog(gui, "Color no valida, vuelva a seleccionar");
                }
                else {
                    String newColorString = Integer.toString(newColor.getRGB());
                }
            }
        });
        colorP2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Seleccionar Color", Color.WHITE);
                if (newColor == null){
                    JOptionPane.showMessageDialog(gui, "Color no valida, vuelva a seleccionar");
                }
                else {
                    String newColorString = Integer.toString(newColor.getRGB());
                }
            }
        });
        colorB1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Seleccionar Color", Color.WHITE);
                if (newColor == null){
                    JOptionPane.showMessageDialog(gui, "Color no valida, vuelva a seleccionar");
                }
                else {
                    String newColorString = Integer.toString(newColor.getRGB());
                }
            }
        });
        colorB2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Seleccionar Color", Color.WHITE);
                if (newColor == null){
                    JOptionPane.showMessageDialog(gui, "Color no valida, vuelva a seleccionar");
                }
                else {
                    String newColorString = Integer.toString(newColor.getRGB());
                }
            }
        });
        applySettings.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(newGameOp);
                revalidate();
                repaint();
            }
        });

    }
    public void prepareStartGameWindowElements(){
        gamePanel = new JPanel();
    }
    public void prepareStartGameWindowActions(){

    }
    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFont(new Font("Consolas", Font.BOLD, 30));
        button.setBackground(new Color(200, 173, 127));
        button.setForeground(Color.BLACK);
        return button;
    }
    private void prepareCustomizeWindowElements() {
        customPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Título
        JLabel titleLabel = new JLabel("CUSTOMIZES");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Ocupar tres columnas
        customPanel.add(titleLabel, gbc);

        // Panel para el tamaño del tablero
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel labelBoardSize = new JLabel("BOARD SIZE:");
        labelBoardSize.setFont(new Font("Consolas", Font.BOLD, 20));
        labelBoardSize.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(labelBoardSize);

        JTextField boardSizeField = new JTextField();
        boardSizeField.setPreferredSize(buttonSize);
        boardSizeField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(boardSizeField);
        customPanel.add(panel, gbc);

        // Panel para las opciones de personalización
        JPanel options = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Ocupar dos columnas
        customPanel.add(options, gbc);

        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        String[] labels = {"Normal:", "Normal:", "Temporal:", "Teletransportadora:", "Larga:", "Regresar:", "Aliada:", "Turno Doble:"};
        for (int i = 1; i <= 7; i+=2) {
            if(i == 1){
                gbc.gridy = 0;
                gbc.gridx = 0;
                JLabel barrierLabel = new JLabel("Barrera");
                barrierLabel.setFont(new Font("Consolas", Font.BOLD, 20));
                barrierLabel.setHorizontalAlignment(SwingConstants.CENTER);
                barrierLabel.setPreferredSize(buttonSize);
                options.add(barrierLabel, gbc);
                gbc.gridx = 2;
                JLabel squaresLabel = new JLabel("Casillas");
                squaresLabel.setFont(new Font("Consolas", Font.BOLD, 20));
                squaresLabel.setHorizontalAlignment(SwingConstants.CENTER);
                squaresLabel.setPreferredSize(buttonSize);
                options.add(squaresLabel, gbc);
            }
            gbc.gridx = 0; // Columna 0 para las etiquetas
            gbc.gridy = i; // Fila i
            gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
            JLabel label = new JLabel(labels[i-1]); // Crea una nueva etiqueta con el texto del array
            label.setFont(new Font("Consolas", Font.BOLD, 20));
            label.setPreferredSize(buttonSize);
            options.add(label, gbc); // Agrega la etiqueta con GridBagConstraints
            gbc.gridx = 1; // Columna 1 para los campos de texto
            gbc.anchor = GridBagConstraints.EAST; // Alinear a la derecha
            JTextField textField = new JTextField(20);
            // Crea un nuevo campo de texto con un ancho de 20 caracteres
            textField.setPreferredSize(buttonSize); // Establece el tamaño preferido del campo de texto
            options.add(textField, gbc);// Agrega el campo de texto con GridBagConstraints
            custumValuesBarriers.add(textField);
            gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
            gbc.gridx = 2; // Columna 1 para los campos de texto
            // Alinear a la derecha
            JLabel label2 = new JLabel(labels[i]);
            label2.setFont(new Font("Consolas", Font.BOLD, 20));// Crea una nueva etiqueta con el texto del array
            options.add(label2, gbc); // Agrega la etiqueta con GridBagConstraints
            gbc.gridx = 3;
            gbc.anchor = GridBagConstraints.EAST; // Alinear a la derecha
            JTextField textField2 = new JTextField(20);
            // Crea un nuevo campo de texto con un ancho de 20 caracteres
            textField2.setPreferredSize(buttonSize); // Establece el tamaño preferido del campo de texto
            options.add(textField2, gbc);// Agrega el campo de texto con GridBagConstraints
            custumValuesSquares.add(textField2);
        }

        // Botón
        applyCustoms = createButton("APPLY", buttonSize);
        gbc.gridx = 0; // Columna 0
        gbc.gridy++; // Siguiente fila
        gbc.gridwidth = 3; // Ocupar dos columnas
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Alinear al centro
        customPanel.add(applyCustoms, gbc);
    }


    private void prepareCustomizeWindowActions(){
        applyCustoms.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(newGameOp);
                revalidate();
                repaint();
            }
        });
    }

    public static void main(String args[]){
        QuoridorGUI gui = new QuoridorGUI();
        gui.setVisible(true);
    }




}