package src.presentation;

import src.domain.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;

import src.domain.Quoridor;
public class QuoridorGUI extends  JFrame{
    private Dimension screenSize;
    private final Dimension buttonSize = new Dimension(250,60);
    private final Font gameFont30 = new Font("Consolas", Font.BOLD, 30);
    private final Font gameFont20 = new Font("Consolas", Font.BOLD, 20);
    private final QuoridorGUI gui = this;
    private Integer turns;
    Quoridor quoridor;
    // Menu
    private JMenuItem nuevo, abrir, guardar, cerrar;
    // Principal
    private JPanel mainPanel;
    private JButton newGameButton;
    private JButton exitGameButton;
    private JButton loadGameButton;
    // Juego
    private JPanel newGameOp;
    private JComboBox nPlayers, modes, difficulty;
    private JButton startGameButton, settingsButton, customizeButton;
    private JPanel infPlayer, startOp;
    private JTextField namePlayer;
    private JTextField namePlayer2;
    //Ajustes
    private JPanel settingsPanel;
    private JButton colorBoard, colorP1, colorP2, applySettingsButton;
    private Color colorBoardSelected = Color.WHITE;
    private Color colorP1Selected = Color.WHITE;
    private Color colorP2Selected = Color.WHITE;
    // Customize window;
    private JPanel customPanel;
    private  JTextField boardSize ;
    private HashMap<String, JTextField> customs;
    private JButton applyCustomsButton;
    // Game window
    private JPanel[][] cells;
    private JPanel[][] horizontalBarriers;
    private JPanel[][] verticalBarriers;
    private JComboBox<String> barreraP1, barreraP2;
    private JLabel labelTurns;
    private JPanel gamePanel;
    private JPanel boardPanel;
    private JButton finishButton;
    private JButton homeButton;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton changeSizeButton;
    private JButton reStartButton;

    private QuoridorGUI()  {
        super("Quoridor");
        customs = new HashMap<>();
        boardSize = new JTextField();
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
        exitGameButton = createButton("   EXIT   ", buttonSize);
        loadGameButton = createButton(" LOAD GAME", buttonSize);
        buttonPanel.add(loadGameButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exitGameButton);
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
        exitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrar.doClick();
            }
        });
        loadGameButton.addActionListener(new ActionListener() {
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
        nPlayers.setFont(gameFont30);
        nPlayers.setFont(gameFont30);
        nPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        infPlayer.add(nPlayers);
        newGameOp.add(infPlayer);
        //Inicio y ajustes
        startGameButton = createButton("START", buttonSize);
        settingsButton = createButton("SETTINGS", buttonSize);
        customizeButton = createButton("CUSTOMIZE", buttonSize);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customizeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficulty = new JComboBox<String>();
        difficulty.setPreferredSize(buttonSize);
        difficulty.setFont(gameFont30);
        difficulty.addItem("NORMAL");
        difficulty.addItem("TIME TRIAL");
        difficulty.addItem("TIMED");
        difficulty.setPreferredSize(buttonSize);
        startOp.add(startGameButton);
        startOp.add(new Label());
        startOp.add(settingsButton);
        startOp.add(new Label());
        startOp.add(customizeButton);
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
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareSettingsWindowElements();
                prepareSettingsWindowActions();
                setContentPane(settingsPanel);
                revalidate();
                repaint();
            }
        });
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    quoridor = new Quoridor(
                            (String)(boardSize.getText().isEmpty() ? "9" :boardSize.getText()),
                            customs.containsKey("Normal B:") && !customs.get("Normal B:").getText().isEmpty() ? customs.get("Normal B:").getText().trim() : "0",
                            customs.containsKey("Temporal:") && !customs.get("Temporal:").getText().isEmpty() ? customs.get("Temporal:").getText().trim() : "0",
                            customs.containsKey("Larga:") && !customs.get("Larga:").getText().isEmpty() ? customs.get("Larga:").getText().trim() : "0",
                            customs.containsKey("Aliada:") && !customs.get("Aliada:").getText().isEmpty() ? customs.get("Aliada:").getText().trim() : "0",
                            customs.containsKey("Teletransportadora:") && !customs.get("Teletransportadora:").getText().isEmpty() ? customs.get("Teletransportadora:").getText().trim() : "0",
                            customs.containsKey("Regresar:") && !customs.get("Regresar:").getText().isEmpty() ? customs.get("Regresar:").getText().trim() : "0",
                            customs.containsKey("Turno Doble:") && !customs.get("Turno Doble:").getText().isEmpty() ? customs.get("Turno Doble:").getText().trim() : "0",
                            (String)(namePlayer.getText().trim()),
                            colorP1Selected == Color.WHITE ? Color.CYAN : colorP1Selected,
                            (String)(namePlayer2 != null ? namePlayer2.getText().trim() : ""),
                            colorP2Selected == Color.WHITE ? Color.RED : colorP2Selected,
                            (String)difficulty.getSelectedItem(),
                            "0",
                            (nPlayers.getSelectedItem().equals("1 PLAYERS")),
                            (String)modes.getSelectedItem());
                    turns = quoridor.getTurns();
                    colorP1Selected = colorP1Selected == Color.WHITE ? Color.CYAN : colorP1Selected;
                    colorP2Selected = colorP2Selected == Color.WHITE ? Color.RED : colorP2Selected;
                    barreraP1 = new JComboBox<String>();
                    barreraP2 = new JComboBox<String>();
                    barreraP1.addItem("Normales");
                    barreraP2.addItem("Normales");
                    if(!boardSize.getText().isEmpty()){
                        barreraP1.addItem("Larga");
                        barreraP2.addItem("Larga");
                        barreraP1.addItem("Temporal");
                        barreraP2.addItem("Temporal");
                        barreraP1.addItem("Aliada");
                        barreraP2.addItem("Aliada");
                    }
                    prepareStartGameWindowElements();
                    prepareStartGameWindowActions();
                    setContentPane(gamePanel);
                    revalidate();
                    repaint();
                }
                catch (QuoridorException ex) {
                    JOptionPane.showMessageDialog(gui, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        customizeButton.addActionListener(new ActionListener() {
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
        nameLabel.setFont(gameFont30);
        namePlayer = new JTextField(20);
        namePlayer.setFont(gameFont30);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel modos = new JPanel();
        modos.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel modsmaq = new JLabel("MODES");
        modsmaq.setPreferredSize(buttonSize);
        modsmaq.setFont(gameFont30);
        modes = new JComboBox<String>();
        modes.setPreferredSize(buttonSize);
        modes.setFont(gameFont30);
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
        dificult.setFont(gameFont30);
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
        nameLabel.setFont(gameFont30);
        namePlayer = new JTextField(20);
        namePlayer.setFont(gameFont30);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1.add(nameLabel);
        player1.add(namePlayer);
        JPanel player2 = new JPanel();
        player2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel nameLabel2 = new JLabel("NAME PLAYER");
        nameLabel2.setFont(gameFont30);
        namePlayer2 = new JTextField(20);
        namePlayer2.setFont(gameFont30);
        nameLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer2.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2.add(nameLabel2);
        player2.add(namePlayer2);
        JPanel dificultad = new JPanel();
        dificultad.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel dificult = new JLabel("DIFFICULTY");
        dificult.setFont(gameFont30);
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
        JPanel buttons = new JPanel(new GridLayout(0, 2, 0, 10));
        colorBoard = createButton("", buttonSize);
        JLabel colorB = new JLabel("COLOR BOARD ");
        colorB.setFont(gameFont30);
        colorP1 = createButton("", buttonSize);
        JLabel colorPe1 = new JLabel("COLOR P1 ");
        colorPe1.setFont(gameFont30);
        colorP2 = createButton("", buttonSize);
        JLabel colorPe2 = new JLabel("COLOR P2 ");
        colorPe2.setFont(gameFont30);
        buttons.add(colorB);
        buttons.add(colorBoard);
        buttons.add(colorPe1);
        buttons.add(colorP1);
        buttons.add(colorPe2);
        buttons.add(colorP2);
        settingsPanel.add(buttons, buttonConstraints);
        // Restricciones para el botón de aplicar
        GridBagConstraints applyConstraints = new GridBagConstraints();
        applyConstraints.gridx = 0;
        applyConstraints.gridy = 2;
        applyConstraints.anchor = GridBagConstraints.SOUTHEAST; // Alineación en la esquina inferior derecha
        applyConstraints.insets = new Insets(300, (screenSize.width)-250, 0, 0); // Espacio entre los botones y el borde inferior

        applySettingsButton = createButton("APPLY", buttonSize);
        settingsPanel.add(applySettingsButton, applyConstraints);
    }
    public void prepareSettingsWindowActions(){
        colorBoard.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Seleccionar Color", colorBoardSelected);
                if (newColor != null) {
                    colorBoardSelected = newColor;
                    colorBoard.setBackground(newColor);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes elegir un color");
                }
            }
        });
        colorP1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Seleccionar Color", colorP1Selected);
                if (newColor != null) {
                    colorP1Selected = newColor;
                    colorP1.setBackground(newColor);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Debes elegir un color");
                }
            }
        });
        colorP2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Seleccionar Color", colorP2Selected);
                if (newColor != null) {
                    colorP2Selected = newColor;
                    colorP2.setBackground(newColor);
                } else{
                    JOptionPane.showMessageDialog(null, "Debes elegir un color");
                }
            }
        });
        applySettingsButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!colorBoardSelected.equals(colorP1Selected) && !colorBoardSelected.equals(colorP2Selected) && !colorP1Selected.equals(colorP2Selected)) {
                    setContentPane(newGameOp);
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(gui, "Por favor, selecciona colores diferentes para cada opción.");
                }
            }
        });
    }
    public void prepareStartGameWindowElements(){
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        JPanel title = new JPanel();
        JLabel titleLabel = new JLabel("Quoridor");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        title.add(titleLabel);
        gamePanel.add(title, BorderLayout.NORTH);
        gamePanel.add(createBoard(colorBoardSelected), BorderLayout.CENTER);
        String players = (String) nPlayers.getSelectedItem();
        if(players.equals("1 PLAYER")){
            JPanel infoPlayerPanel = infoPlayerGame();
            infoPlayerPanel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Agregar relleno horizontal
            gamePanel.add(infoPlayerPanel, BorderLayout.EAST);

            JPanel infoMachinePanel = infoMachine();
            infoMachinePanel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Agregar relleno horizontal
            gamePanel.add(infoMachinePanel, BorderLayout.WEST);
        }
        else if(players.equals("2 PLAYERS")){
            JPanel infoPlayerPanel = infoPlayerGame();
            infoPlayerPanel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Agregar relleno horizontal
            gamePanel.add(infoPlayerPanel, BorderLayout.EAST);
            JPanel infoPlayer2Panel = infoPlayerGame2();
            infoPlayer2Panel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Agregar relleno horizontal
            gamePanel.add(infoPlayer2Panel, BorderLayout.WEST);
        }
        JPanel options = new JPanel(new BorderLayout());
        labelTurns = new JLabel("Turno :");
        labelTurns.setFont(gameFont20);
        finishButton = createButton("Finish", buttonSize);
        options.add(new Label(), BorderLayout.WEST);
        options.add(labelTurns, BorderLayout.CENTER);
        options.add(finishButton, BorderLayout.EAST);
        options.add(new Label(), BorderLayout.SOUTH);
        gamePanel.add(options, BorderLayout.SOUTH);
    }

    private JPanel infoPlayerGame(){
        int tampanel = (int)(screenSize.height * 0.5);
        JPanel inf = new JPanel(new GridLayout(5,1));
        JPanel infnombre = new JPanel(new FlowLayout());
        JLabel jnombre = new JLabel("Nombre P1:");
        jnombre.setFont(gameFont20);
        JLabel nombre = new JLabel();
        if(!namePlayer.getText().isEmpty()) {
            nombre = new JLabel(namePlayer.getText());
        }
        nombre.setFont(gameFont20);
        infnombre.add(jnombre);
        infnombre.add(nombre);
        inf.add(infnombre);
        JPanel colorP1 = new JPanel();
        if(!colorP1Selected.equals(Color.WHITE)){
            colorP1.setBackground(colorP1Selected);
        }
        else{
            colorP1.setBackground(Color.CYAN);
        }
        colorP1.setPreferredSize(new Dimension(60, 60));
        infnombre.add(colorP1);
        JPanel barrerasdis = new JPanel(new GridLayout(4,2));
        String[] key =  {"Normal B:",  "Temporal:",  "Larga:", "Aliada:"};
        if(!(customs.get(key[0]) == null)) {
            for (int i = 0; i < 4; i++) {
                String llave = key[i];
                String textField = customs.get(llave).getText();
                JLabel barrera = new JLabel(llave);
                barrera.setFont(gameFont20);
                barrera.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel cantb;
                if (!textField.equals("")) {
                    cantb = new JLabel(textField);
                } else {
                    cantb = new JLabel("0");
                }
                cantb.setFont(gameFont20);
                cantb.setHorizontalAlignment(SwingConstants.CENTER);
                barrerasdis.add(barrera);
                barrerasdis.add(cantb);
            }
        }else{
            JLabel barrera = new JLabel("Normales");
            barrera.setFont(gameFont20);
            barrera.setHorizontalAlignment(SwingConstants.CENTER);
            String cant = "10";
            JLabel cantb = new JLabel(cant);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            barrerasdis.add(barrera);
            barrerasdis.add(cantb);
        }
        JPanel barreraEsc = new JPanel(new FlowLayout());
        JLabel barrera = new JLabel("Barrera :");
        barrera.setFont(gameFont20);
        barreraEsc.add(barrera);
        barreraEsc.add(barreraP1);
        inf.add(barreraEsc);
        inf.add(barrerasdis);

        inf.setPreferredSize(new Dimension(tampanel, tampanel));
        return inf;

    }

    private JPanel infoMachine(){
        int tampanel = (int)(screenSize.height * 0.5);
        JPanel inf = new JPanel(new GridLayout(5,1));
        JPanel infnombre = new JPanel(new FlowLayout());
        JLabel jnombre = new JLabel("Maquina:");
        jnombre.setFont(gameFont20);
        infnombre.add(jnombre);
        JPanel colorP2 = new JPanel();
        if(!colorP2Selected.equals(Color.WHITE)){
            colorP2.setBackground(colorP2Selected);
        }
        else{
            colorP2.setBackground(Color.RED);
        }
        colorP2.setPreferredSize(new Dimension(60, 60));
        infnombre.add(colorP2);
        inf.add(infnombre);
        JPanel barrerasdis = new JPanel(new GridLayout(4,2));
        String[] key =  {"Normal B:",  "Temporal:",  "Larga:", "Aliada:"};
        if(!(customs.get(key[0]) == null)) {
            for (int i = 0; i < 4; i++) {
                String textField = customs.get(key[i]).getText();
                JLabel barrera = new JLabel(key[i]);
                barrera.setFont(gameFont20);
                barrera.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel cantb;
                if (!textField.equals("")) {
                    cantb = new JLabel(textField);
                } else {
                    cantb = new JLabel("0");
                }
                cantb.setFont(gameFont20);
                cantb.setHorizontalAlignment(SwingConstants.CENTER);
                barrerasdis.add(barrera);
                barrerasdis.add(cantb);
            }
        }
        else{
            JLabel barrera = new JLabel("Normales");
            barrera.setFont(gameFont20);
            barrera.setHorizontalAlignment(SwingConstants.CENTER);
            String cant = "10";
            JLabel cantb = new JLabel(cant);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            barrerasdis.add(barrera);
            barrerasdis.add(cantb);
        }
        JPanel barreraEsc = new JPanel(new FlowLayout());
        JLabel barrera = new JLabel("Barrera :");
        barrera.setFont(gameFont20);
        barreraEsc.add(barrera);
        barreraEsc.add(barreraP2);
        inf.add(barreraEsc);
        inf.add(barrerasdis);
        inf.setPreferredSize(new Dimension(tampanel, tampanel));
        return inf;
    }

    private JPanel infoPlayerGame2(){
        int tampanel = (int)(screenSize.height * 0.5);
        JPanel inf = new JPanel(new GridLayout(5,1));
        JPanel infnombre = new JPanel(new FlowLayout());
        JLabel jnombre = new JLabel("Nombre P2:");
        jnombre.setFont(gameFont20);
        JLabel nombre = new JLabel();
        if(!namePlayer2.getText().isEmpty()) {
            nombre = new JLabel(namePlayer2.getText());
        }
        nombre.setFont(gameFont20);
        infnombre.add(jnombre);
        infnombre.add(nombre);
        inf.add(infnombre);
        JPanel colorP2 = new JPanel();
        if(!colorP2Selected.equals(Color.WHITE)){
            colorP2.setBackground(colorP2Selected);
        }
        else{
            colorP2.setBackground(Color.RED);
        }
        colorP2.setPreferredSize(new Dimension(60, 60));
        infnombre.add(colorP2);
        JPanel barrerasdis = new JPanel(new GridLayout(4,2));
        String[] key =  {"Normal B:",  "Temporal:",  "Larga:", "Aliada:"};
        if(!(customs.get(key[0]) == null)) {
            for (int i = 0; i < 4; i++) {
                String textField = customs.get(key[i]).getText();
                JLabel barrera = new JLabel(key[i]);
                barrera.setFont(gameFont20);
                barrera.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel cantb;
                if (!textField.equals("")) {
                    cantb = new JLabel(textField);
                } else {
                    cantb = new JLabel("0");
                }
                cantb.setFont(gameFont20);
                cantb.setHorizontalAlignment(SwingConstants.CENTER);
                barrerasdis.add(barrera);
                barrerasdis.add(cantb);
            }
        }
        else{
            JLabel barrera = new JLabel("Normales");
            barrera.setFont(gameFont20);
            barrera.setHorizontalAlignment(SwingConstants.CENTER);
            String cant = "10";
            JLabel cantb = new JLabel(cant);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            barrerasdis.add(barrera);
            barrerasdis.add(cantb);
        }
        JPanel barreraEsc = new JPanel(new FlowLayout());
        JLabel barrera = new JLabel("Barrera :");
        barrera.setFont(gameFont20);
        barreraEsc.add(barrera);
        barreraEsc.add(barreraP2);
        inf.add(barreraEsc);
        inf.add(barrerasdis);
        inf.setPreferredSize(new Dimension(tampanel, tampanel));
        return inf;
    }

    private JPanel createBoard(Color board) {
        boardPanel = new JPanel(null);
        int tamBoard = screenSize.height * 4 / 5;
        int numero = boardSize.getText().isEmpty() ? 9 : Integer.parseInt(boardSize.getText().trim());
        int CELL_SIZE = (int)((float)(tamBoard*0.75)/numero);
        int BAR_WIDTH = (int)((float)(tamBoard*0.15)/numero);
        cells = new JPanel[numero][numero];
        horizontalBarriers = new JPanel[numero - 1][numero];
        verticalBarriers = new JPanel[numero][numero - 1];
        for (int i = 0; i < numero; i++) {
            for (int j = 0; j < numero; j++) {
                int midColumn = numero%2==0?(numero/2)-1:(numero/2);

                cells[i][j] = new JPanel(new BorderLayout());
                int cellX = (int) (j * CELL_SIZE + j * BAR_WIDTH);
                int cellY = (int) (i * CELL_SIZE + i * BAR_WIDTH);
                int cell = (int) (CELL_SIZE);
                cells[i][j].setBounds(cellX, cellY, cell, cell);
                cells[i][j].setBackground(board);

                if( i== 0 && j == midColumn) {
                    JPanel P1 = new JPanel();
                    P1.setPreferredSize(new Dimension(CELL_SIZE-5,CELL_SIZE-5));
                    P1.setBackground(colorP1Selected);
                    cells[i][midColumn].add(P1, BorderLayout.CENTER);
                }
                else if(i==numero-1 && j == midColumn) {
                    JPanel P2 = new JPanel();
                    P2.setBackground(colorP2Selected);
                    P2.setPreferredSize((new Dimension(CELL_SIZE-5,CELL_SIZE-5)));
                    cells[numero-1][midColumn].add(P2, BorderLayout.CENTER);
                }

                boardPanel.add(cells[i][j]);
                if (j < numero - 1 ) { // Evitar la última fila de barreras verticales
                    verticalBarriers[i][j] = createBarrier(Color.GRAY, BAR_WIDTH, CELL_SIZE);
                    int barrierX = (int) ((j + 1) * CELL_SIZE + j * BAR_WIDTH);
                    int barrierY = (int) (i * CELL_SIZE + i * BAR_WIDTH);
                    verticalBarriers[i][j].setBounds(barrierX, barrierY, BAR_WIDTH, CELL_SIZE);
                    // Evitar añadir MouseListener a las barreras verticales de la última fila
                    if (i < verticalBarriers.length - 1) {
                        verticalBarriers[i][j].addMouseListener(createBarrierMouseListener(verticalBarriers[i][j], i, j));
                    }
                    boardPanel.add(verticalBarriers[i][j]);
                }
            }
            if (i < numero - 1) {
                for (int j = 0; j < numero; j++) {
                    horizontalBarriers[i][j] = createBarrier(Color.GRAY, CELL_SIZE, BAR_WIDTH);
                    int barrierX = (int) (j * CELL_SIZE + j * BAR_WIDTH);
                    int barrierY = (int) ((i + 1) * CELL_SIZE + i * BAR_WIDTH);
                    horizontalBarriers[i][j].setBounds(barrierX, barrierY, CELL_SIZE, BAR_WIDTH);
                    // Evitar añadir MouseListener a las barreras horizontales de la última columna
                    if (j < horizontalBarriers[i].length - 1) {
                        horizontalBarriers[i][j].addMouseListener(createBarrierMouseListener(horizontalBarriers[i][j], i, j));
                    }
                    boardPanel.add(horizontalBarriers[i][j]);
                    if (j < numero - 1) {
                        JPanel emptyPanel = new JPanel();
                        emptyPanel.setBackground(boardPanel.getBackground());
                        int emptyX = (int) ((j + 1) * CELL_SIZE + j * BAR_WIDTH);
                        int emptyY = (int) ((i + 1) * CELL_SIZE + i * BAR_WIDTH);
                        int emptyWidth = (int) (BAR_WIDTH);
                        int emptyHeight = (int) (BAR_WIDTH);
                        emptyPanel.setBounds(emptyX, emptyY, emptyWidth, emptyHeight);
                        emptyPanel.setBackground(board);
                        boardPanel.add(emptyPanel);
                    }
                }
            }
        }
        boardPanel.setPreferredSize(new Dimension(tamBoard, tamBoard));
        return boardPanel;
    }
    private JPanel createBarrier(Color color, int width, int height) {
        JPanel barrierPanel = new JPanel();
        barrierPanel.setBackground(color);
        barrierPanel.setPreferredSize(new Dimension(width, height));
        return barrierPanel;
    }
    private MouseAdapter createBarrierMouseListener(JPanel barrier, int row, int col) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                barrier.setBackground(Color.BLUE);
                // Verificar si la barrera es vertical
                if (e.getComponent() == verticalBarriers[row][col]  && row != verticalBarriers.length -1) {
                    verticalBarriers[row + 1][col].setBackground(Color.BLUE);
                }
                // Verificar si la barrera es horizontal
                else if (e.getComponent() == horizontalBarriers[row][col] && col != horizontalBarriers[row].length - 1) {
                    horizontalBarriers[row][col + 1].setBackground(Color.BLUE);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                barrier.setBackground(Color.GRAY);
                // Verificar si la barrera es vertical
                if (e.getComponent() == verticalBarriers[row][col]  && row != verticalBarriers.length-1) {
                    verticalBarriers[row + 1][col].setBackground(Color.GRAY);
                }
                // Verificar si la barrera es horizontal
                else if (e.getComponent() == horizontalBarriers[row][col] && col != horizontalBarriers[row].length - 1) {
                    horizontalBarriers[row][col + 1].setBackground(Color.GRAY);
                }
            }
        };
    }
    public void prepareStartGameWindowActions(){
        finishButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(newGameOp);
                revalidate();
                repaint();
            }
        });
    }
    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFont(gameFont30);
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
        titleLabel.setFont(gameFont30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Ocupar tres columnas
        customPanel.add(titleLabel, gbc);
        // Panel para el tamaño del tablero
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel labelBoardSize = new JLabel("BOARD SIZE:");
        labelBoardSize.setFont(gameFont20);
        labelBoardSize.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(labelBoardSize);
        //Tamaño Board
        boardSize.setPreferredSize(buttonSize);
        boardSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(boardSize);
        customPanel.add(panel, gbc);
        // Panel para las opciones de personalización
        JPanel options = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // Ocupar dos columnas
        customPanel.add(options, gbc);
        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        String[] labels = {"Normal B:", "Normal C:", "Temporal:", "Teletransportadora:", "Larga:", "Regresar:", "Aliada:", "Turno Doble:"};
        for (int i = 1; i <= 7; i+=2) {
            if(i == 1){
                gbc.gridy = 0;
                gbc.gridx = 0;
                JLabel barrierLabel = new JLabel("Barrera");
                barrierLabel.setFont(gameFont20);
                barrierLabel.setHorizontalAlignment(SwingConstants.CENTER);
                barrierLabel.setPreferredSize(buttonSize);
                options.add(barrierLabel, gbc);
                gbc.gridx = 2;
                JLabel squaresLabel = new JLabel("Casillas");
                squaresLabel.setFont(gameFont20);
                squaresLabel.setHorizontalAlignment(SwingConstants.CENTER);
                squaresLabel.setPreferredSize(buttonSize);
                options.add(squaresLabel, gbc);
            }
            gbc.gridx = 0; // Columna 0 para las etiquetas
            gbc.gridy = i; // Fila i
            gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
            JLabel label = new JLabel(labels[i-1]); // Crea una nueva etiqueta con el texto del array
            label.setFont(gameFont20);
            label.setPreferredSize(buttonSize);
            options.add(label, gbc); // Agrega la etiqueta con GridBagConstraints
            gbc.gridx = 1; // Columna 1 para los campos de texto
            gbc.anchor = GridBagConstraints.EAST; // Alinear a la derecha
            JTextField textField = new JTextField(20);
            // Crea un nuevo campo de texto con un ancho de 20 caracteres
            textField.setPreferredSize(buttonSize); // Establece el tamaño preferido del campo de texto
            options.add(textField, gbc);// Agrega el campo de texto con GridBagConstraints
            customs.put(labels[i-1], textField);
            gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
            gbc.gridx = 2; // Columna 1 para los campos de texto
            // Alinear a la derecha
            JLabel label2 = new JLabel(labels[i]);
            label2.setFont(gameFont20);// Crea una nueva etiqueta con el texto del array
            options.add(label2, gbc); // Agrega la etiqueta con GridBagConstraints
            gbc.gridx = 3;
            gbc.anchor = GridBagConstraints.EAST; // Alinear a la derecha
            JTextField textField2 = new JTextField(20);
            // Crea un nuevo campo de texto con un ancho de 20 caracteres
            textField2.setPreferredSize(buttonSize); // Establece el tamaño preferido del campo de texto
            options.add(textField2, gbc);// Agrega el campo de texto con GridBagConstraints
            customs.put(labels[i], textField);
        }
        // Botón
        applyCustomsButton = createButton("APPLY", buttonSize);
        gbc.gridx = 0; // Columna 0
        gbc.gridy++; // Siguiente fila
        gbc.gridwidth = 3; // Ocupar dos columnas
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Alinear al centro
        customPanel.add(applyCustomsButton, gbc);
    }
    private void prepareCustomizeWindowActions(){
        applyCustomsButton.addActionListener(new ActionListener(){
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