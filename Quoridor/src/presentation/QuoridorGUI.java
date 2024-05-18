package src.presentation;

import src.domain.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import src.domain.Quoridor;
public class QuoridorGUI extends  JFrame {
    private Dimension screenSize;
    private final Dimension buttonSize = new Dimension(250, 60);
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
    private JComboBox numberPlayersCB, modalities, difficulties;
    private JButton startGameButton, settingsButton, customizeButton;
    private JPanel infPlayer, startOptions;
    private JTextField namePlayer1;
    private JTextField namePlayer2;
    //Ajustes
    private JPanel settingsPanel;
    private JButton boardColorButton, player1ColorButton, player2ColorButton, applySettingsButton;
    private Color boardColor = Color.WHITE;
    private Color player1Color = Color.WHITE;
    private Color player2Color = Color.WHITE;
    // Customize window;
    private JPanel customPanel;
    private JTextField boardSize;
    private HashMap<String, JTextField> customsElements;
    private JButton applyCustomsButton;
    // Game window
    private JPanel P1,P2;
    private Color barrierColor;
    private HashMap<String, JButton> buttonsMovements;
    private ArrayList<JPanel> possibleMovements;
    private JPanel[][] board;
    private JComboBox<String> barrierTypePlayer1, barrierTypePlayer2;
    private JPanel panelTurns;
    private JLabel labelTurns;
    private JPanel gamePanel;
    private JPanel boardPanel;
    private JButton finishButton;
    private HashMap<String, JLabel> barrerasDisP1, barrerasDisP2;
    private JPanel colorTurn;
    private JButton homeButton;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton changeSizeButton;
    private JButton reStartButton;
    private Color playerTurno;

    private QuoridorGUI() {
        super("Quoridor");
        customsElements = new HashMap<>();
        barrerasDisP1 = new HashMap<>();
        barrerasDisP2 = new HashMap<>();
        buttonsMovements = new HashMap<>();
        boardSize = new JTextField();
        possibleMovements = new ArrayList<>();
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        prepareMenuElements();
        prepareMainWindowElements();
    }

    private void prepareActions() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                int option = JOptionPane.showConfirmDialog(gui, "¿Estás seguro de que quieres cerrar?", "Cofirmar cierre", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    gui.setVisible(false);
                    System.exit(0);
                }
            }
        });
        prepareMenuActions();
        prepareMainWindowActions();
    }

    private void prepareMenuElements() {
        // Botones-Opciones Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu opciones = new JMenu("Archivo");
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

    private void prepareMenuActions() {
        cerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                int option = JOptionPane.showConfirmDialog(gui, "¿Estás seguro de que quieres cerrar?", "Cofirmar cierre", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
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
                if (option == JFileChooser.APPROVE_OPTION) {
                    java.io.File selectedFolder = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(gui, "Funcionalidad de guardar en construcción. Carpeta seleccionada: " + selectedFolder.getName(), "Guardar Juego", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void prepareMainWindowElements() {
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
        // load game button
        loadGameButton = createButton(" LOAD GAME", buttonSize);
        buttonPanel.add(loadGameButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(Box.createVerticalStrut(10));
        // exit button
        exitGameButton = createButton("   EXIT   ", buttonSize);
        buttonPanel.add(exitGameButton);
        newGamePanel.add(buttonPanel);
        mainPanel.add(newGamePanel);
        add(mainPanel);
    }

    private void prepareMainWindowActions() {
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareNewGameWindowElements();
                prepareNewGameWindowActions();
                setContentPane(newGameOp);
                revalidate();
                repaint();
                numberPlayersCB.setSelectedIndex(0);
                numberPlayersCB.setSelectedIndex(1);
                numberPlayersCB.setSelectedIndex(0);
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
                abrir.doClick();
            }
        });
    }

    public void prepareNewGameWindowElements() {
        newGameOp = new JPanel();
        newGameOp.setLayout(new GridLayout(3, 1, 0, 10));
        newGameOp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("NEW GAME");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newGameOp.add(titleLabel);
        startOptions = new JPanel();
        startOptions.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 50));
        infPlayer = new JPanel();
        startOptions.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        // number players
        numberPlayersCB = new JComboBox<String>();
        numberPlayersCB.setPreferredSize(buttonSize);
        numberPlayersCB.addItem("1 PLAYER");
        numberPlayersCB.addItem("2 PLAYERS");
        numberPlayersCB.setPreferredSize(buttonSize);
        numberPlayersCB.setFont(gameFont30);
        numberPlayersCB.setFont(gameFont30);
        numberPlayersCB.setAlignmentX(Component.CENTER_ALIGNMENT);
        infPlayer.add(numberPlayersCB);
        newGameOp.add(infPlayer);
        //Inicio y ajustes
        startGameButton = createButton("START", buttonSize);
        settingsButton = createButton("SETTINGS", buttonSize);
        customizeButton = createButton("CUSTOMIZE", buttonSize);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customizeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // difficulties levels
        difficulties = new JComboBox<String>();
        difficulties.setPreferredSize(buttonSize);
        difficulties.setFont(gameFont30);
        difficulties.addItem("NORMAL");
        difficulties.addItem("TIME TRIAL");
        difficulties.addItem("TIMED");
        difficulties.setPreferredSize(buttonSize);
        startOptions.add(startGameButton);
        startOptions.add(new Label());
        startOptions.add(settingsButton);
        startOptions.add(new Label());
        startOptions.add(customizeButton);
        newGameOp.add(startOptions);
    }

    public void prepareNewGameWindowActions() {
        numberPlayersCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) numberPlayersCB.getSelectedItem();
                if (selectedOption.equals("1 PLAYER")) {
                    newGameOp.remove(startOptions);
                    newGameOp.add(infoOnePlayerModality());
                    newGameOp.add(startOptions);
                    newGameOp.revalidate();
                    newGameOp.repaint();
                } else if (selectedOption.equals("2 PLAYERS")) {
                    newGameOp.remove(startOptions);
                    newGameOp.add(infoTwoPlayersModality());
                    newGameOp.add(startOptions);
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
                    String player2Name = "Second player";
                    boolean vsMachine =  numberPlayersCB.getSelectedItem().equals("1 PLAYERS");
                    player1Color = player1Color.equals(Color.WHITE) ? Color.YELLOW : player1Color;
                    player2Color = player2Color.equals(Color.WHITE) ? Color.RED : player2Color;
                    String player1Name = namePlayer1.getText().isEmpty() ? "Player 1" : namePlayer1.getText();
                    if ((numberPlayersCB.getSelectedItem().equals("2 PLAYERS"))){
                        player2Name = namePlayer2.getText().isEmpty() ? "Player 2" : namePlayer2.getText();
                    }
                    player2Name = vsMachine ? "Machine" : player2Name;

                    quoridor = new Quoridor(
                            (boardSize.getText().isEmpty() ? "9" : boardSize.getText()),
                            customsElements.containsKey("Normal B:") && !customsElements.get("Normal B:").getText().isEmpty() ? customsElements.get("Normal B:").getText().trim() : "0",
                            customsElements.containsKey("Temporal:") && !customsElements.get("Temporal:").getText().isEmpty() ? customsElements.get("Temporal:").getText().trim() : "0",
                            customsElements.containsKey("Larga:") && !customsElements.get("Larga:").getText().isEmpty() ? customsElements.get("Larga:").getText().trim() : "0",
                            customsElements.containsKey("Aliada:") && !customsElements.get("Aliada:").getText().isEmpty() ? customsElements.get("Aliada:").getText().trim() : "0",
                            customsElements.containsKey("Teletransportadora:") && !customsElements.get("Teletransportadora:").getText().isEmpty() ? customsElements.get("Teletransportadora:").getText().trim() : "0",
                            customsElements.containsKey("Regresar:") && !customsElements.get("Regresar:").getText().isEmpty() ? customsElements.get("Regresar:").getText().trim() : "0",
                            customsElements.containsKey("Turno Doble:") && !customsElements.get("Turno Doble:").getText().isEmpty() ? customsElements.get("Turno Doble:").getText().trim() : "0",
                            (numberPlayersCB.getSelectedItem().equals("1 PLAYERS")),
                            player1Name,
                            player1Color,
                            player2Name,
                            player2Color,
                            (String) difficulties.getSelectedItem(),
                            "0",
                            (String) modalities.getSelectedItem());
                    turns = quoridor.getTurns();
                    barrierTypePlayer1 = new JComboBox<String>();
                    barrierTypePlayer2 = new JComboBox<String>();
                    barrierTypePlayer1.addItem("Normales");
                    barrierTypePlayer2.addItem("Normales");
                    if (!boardSize.getText().isEmpty()) {
                        barrierTypePlayer1.addItem("Larga");
                        barrierTypePlayer2.addItem("Larga");
                        barrierTypePlayer1.addItem("Temporal");
                        barrierTypePlayer2.addItem("Temporal");
                        barrierTypePlayer1.addItem("Aliada");
                        barrierTypePlayer2.addItem("Aliada");
                    }
                    prepareStartGameWindowElements();
                    prepareStartGameWindowActions();
                    setContentPane(gamePanel);
                    creteButtonsMovements();
                    revalidate();
                    repaint();
                } catch (QuoridorException ex) {
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

    private JPanel infoOnePlayerModality() {
        infPlayer.removeAll();
        infPlayer.setLayout(new GridLayout(4, 1, 0, 10));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        numberPlayersCB.setPreferredSize(buttonSize);
        panel.add(numberPlayersCB);
        JPanel info = new JPanel();
        info.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel nameLabel = new JLabel("NAME PLAYER");
        nameLabel.setFont(gameFont30);
        namePlayer1 = new JTextField(20);
        namePlayer1.setFont(gameFont30);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel mode = new JPanel();
        mode.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel machineModes = new JLabel("MODES");
        machineModes.setPreferredSize(buttonSize);
        machineModes.setFont(gameFont30);
        modalities = new JComboBox<String>();
        modalities.setPreferredSize(buttonSize);
        modalities.setFont(gameFont30);
        modalities.addItem("AMATEUR");
        modalities.addItem("MEDIAN");
        modalities.addItem("ADVANCED");
        modalities.setPreferredSize(buttonSize);
        machineModes.setAlignmentX(Component.CENTER_ALIGNMENT);
        modalities.setAlignmentX(Component.CENTER_ALIGNMENT);
        mode.add(machineModes);
        mode.add(modalities);
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel difficulty = new JLabel("DIFFICULTY");
        difficulty.setFont(gameFont30);
        difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficulties.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyPanel.add(difficulty);
        difficultyPanel.add(difficulties);
        infPlayer.add(panel);
        infPlayer.add(mode);
        infPlayer.add(difficultyPanel);
        info.add(nameLabel);
        info.add(namePlayer1);
        infPlayer.add(info);
        return infPlayer;
    }

    private JPanel infoTwoPlayersModality() {
        infPlayer.removeAll();
        infPlayer.setLayout(new GridLayout(4, 1, 0, 10));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        numberPlayersCB.setPreferredSize(buttonSize);
        panel.add(numberPlayersCB);
        JPanel player1 = new JPanel();
        player1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel nameLabel = new JLabel("NAME PLAYER 1  ");
        nameLabel.setFont(gameFont30);
        namePlayer1 = new JTextField(20);
        namePlayer1.setFont(gameFont30);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer1.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1.add(nameLabel);
        player1.add(namePlayer1);
        JPanel player2 = new JPanel();
        player2.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel nameLabel2 = new JLabel("NAME PLAYER 2  ");
        nameLabel2.setFont(gameFont30);
        namePlayer2 = new JTextField(20);
        namePlayer2.setFont(gameFont30);
        nameLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePlayer2.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2.add(nameLabel2);
        player2.add(namePlayer2);
        JPanel difficultyLevel = new JPanel();
        difficultyLevel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel dificult = new JLabel("DIFFICULTY");
        dificult.setFont(gameFont30);
        dificult.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficulties.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyLevel.add(dificult);
        difficultyLevel.add(difficulties);
        infPlayer.add(panel);
        infPlayer.add(difficultyLevel);
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
        boardColorButton = createButton("", buttonSize);
        JLabel colorB = new JLabel("COLOR BOARD ");
        colorB.setFont(gameFont30);
        player1ColorButton = createButton("", buttonSize);
        JLabel colorPe1 = new JLabel("COLOR P1 ");
        colorPe1.setFont(gameFont30);
        player2ColorButton = createButton("", buttonSize);
        JLabel colorPe2 = new JLabel("COLOR P2 ");
        colorPe2.setFont(gameFont30);
        buttons.add(colorB);
        buttons.add(boardColorButton);
        buttons.add(colorPe1);
        buttons.add(player1ColorButton);
        buttons.add(colorPe2);
        buttons.add(player2ColorButton);
        settingsPanel.add(buttons, buttonConstraints);
        // Restricciones para el botón de aplicar
        GridBagConstraints applyConstraints = new GridBagConstraints();
        applyConstraints.gridx = 0;
        applyConstraints.gridy = 2;
        applyConstraints.anchor = GridBagConstraints.SOUTHEAST; // Alineación en la esquina inferior derecha
        applyConstraints.insets = new Insets(300, (screenSize.width) - 250, 0, 0); // Espacio entre los botones y el borde inferior

        applySettingsButton = createButton("APPLY", buttonSize);
        settingsPanel.add(applySettingsButton, applyConstraints);
    }

    public void prepareSettingsWindowActions() {
        boardColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Choose a color", boardColor);
                if (newColor != null) {
                    boardColor = newColor;
                    boardColorButton.setBackground(newColor);
                } else {
                    JOptionPane.showMessageDialog(null, "You must choose a color");
                }
            }
        });
        player1ColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Choose a color", player1Color);
                if (newColor != null) {
                    player1Color = newColor;
                    player1ColorButton.setBackground(newColor);
                } else {
                    JOptionPane.showMessageDialog(null, "You must choose a color");
                }
            }
        });
        player2ColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(gui, "Choose a color", player2Color);
                if (newColor != null) {
                    player2Color = newColor;
                    player2ColorButton.setBackground(newColor);
                } else {
                    JOptionPane.showMessageDialog(null, "You must choose a color");
                }
            }
        });
        applySettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!boardColor.equals(player1Color) && !boardColor.equals(player2Color) && !player1Color.equals(player2Color)) {
                    setContentPane(newGameOp);
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(gui, "Choose again, each color must be different");
                }
            }
        });
    }

    public void prepareStartGameWindowElements() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        JPanel title = new JPanel();
        JLabel titleLabel = new JLabel("Quoridor");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        title.add(titleLabel);
        gamePanel.add(title, BorderLayout.NORTH);
        gamePanel.add(createBoard(boardColor), BorderLayout.CENTER);
        String players = (String) numberPlayersCB.getSelectedItem();
        if (players.equals("1 PLAYER")) {
            JPanel infoPlayerPanel = infoPlayerGame();
            infoPlayerPanel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Agregar relleno horizontal
            gamePanel.add(infoPlayerPanel, BorderLayout.EAST);

            JPanel infoMachinePanel = infoMachine();
            infoMachinePanel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Agregar relleno horizontal
            gamePanel.add(infoMachinePanel, BorderLayout.WEST);
        } else if (players.equals("2 PLAYERS")) {
            JPanel infoPlayerPanel = infoPlayerGame();
            infoPlayerPanel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Agregar relleno horizontal
            gamePanel.add(infoPlayerPanel, BorderLayout.EAST);
            JPanel infoPlayer2Panel = infoPlayerGame2();
            infoPlayer2Panel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Agregar relleno horizontal
            gamePanel.add(infoPlayer2Panel, BorderLayout.WEST);
        }
        JPanel options = new JPanel(new BorderLayout());
        JPanel turnos = new JPanel(new BorderLayout());
        String playerName = turns % 2 == 0 ? namePlayer1.getText() : namePlayer2.getText();
        labelTurns = new JLabel("Turno :" + playerName);
        labelTurns.setFont(gameFont20);
        panelTurns = new JPanel();
        playerTurno = player1Color;
        panelTurns.setBackground(playerTurno);
        panelTurns.setPreferredSize(new Dimension(60,60));
        finishButton = createButton("Finish", buttonSize);
        turnos.add(labelTurns, BorderLayout.WEST);
        turnos.add(panelTurns, BorderLayout.CENTER);
        options.add(turnos, BorderLayout.CENTER);
        options.add(finishButton, BorderLayout.EAST);
        options.add(new Label(), BorderLayout.SOUTH);
        gamePanel.add(options, BorderLayout.SOUTH);
    }

    private void actualizarTurnos() {
        turns = quoridor.getTurns();
        playerTurno = (turns%2==0)?player1Color:player2Color;
        String playerName = turns % 2 == 0 ? namePlayer1.getText() : namePlayer2.getText();
        labelTurns.setText("Turno de: " + playerName + "             " +" Numero de turnos: " + turns);
        panelTurns.setBackground(playerTurno);
        panelTurns.revalidate();
        panelTurns.repaint();
    }

    private JPanel infoPlayerGame() {
        int tampanel = (int) (screenSize.height * 0.5);
        JPanel inf = new JPanel(new GridLayout(5, 1));
        JPanel infnombre = new JPanel(new FlowLayout());
        JLabel jnombre = new JLabel(namePlayer2.getText().isEmpty() ? "Player 2: " : namePlayer2.getText());
        jnombre.setFont(gameFont20);
        JLabel nombre = new JLabel();
        if (!namePlayer1.getText().isEmpty()) {
            nombre = new JLabel(namePlayer1.getText());
        }
        nombre.setFont(gameFont20);
        infnombre.add(jnombre);
        infnombre.add(nombre);
        inf.add(infnombre);
        JPanel colorP1 = new JPanel();
        if (!player1Color.equals(Color.WHITE)) {
            colorP1.setBackground(player1Color);
        } else {
            colorP1.setBackground(Color.YELLOW);
        }
        colorP1.setPreferredSize(new Dimension(60, 60));
        infnombre.add(colorP1);
        JPanel barrerasdis = new JPanel(new GridLayout(4, 2));
        String[] key = {"Normal B:", "Temporal:", "Larga:", "Aliada:"};
        System.out.println(customsElements.keySet());
        if (!(customsElements.get(key[1]) == null)) {
            for (int i = 0; i < key.length; i++) {
                String llave = key[i];
                String llaveMapa = String.valueOf(Character.toLowerCase(key[i].charAt(0)));
                String textField = customsElements.get(llave).getText();
                JLabel barrera = new JLabel(llave);
                barrera.setFont(gameFont20);
                barrera.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel cantb;
                if (!textField.isEmpty()) {
                    cantb = new JLabel(textField);
                } else {
                    cantb = new JLabel("0");
                }
                barrerasDisP1.put(llaveMapa, cantb);
                cantb.setFont(gameFont20);
                cantb.setHorizontalAlignment(SwingConstants.CENTER);
                barrerasdis.add(barrera);
                barrerasdis.add(cantb);
            }
        }
        else {
            JLabel barrera = new JLabel("Normales");
            barrera.setFont(gameFont20);
            barrera.setHorizontalAlignment(SwingConstants.CENTER);
            String cant = "10";
            JLabel cantb = new JLabel(cant);
            barrerasDisP1.put("n", cantb);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            barrerasdis.add(barrera);
            barrerasdis.add(cantb);
        }
        JPanel barreraEsc = new JPanel(new FlowLayout());
        JLabel barrera = new JLabel("Barrera :");
        barrera.setFont(gameFont20);
        barreraEsc.add(barrera);
        barreraEsc.add(barrierTypePlayer1);
        inf.add(barreraEsc);
        inf.add(barrerasdis);

        inf.setPreferredSize(new Dimension(tampanel, tampanel));
        return inf;

    }

    private JPanel infoMachine() {
        int tampanel = (int) (screenSize.height * 0.5);
        JPanel inf = new JPanel(new GridLayout(5, 1));
        JPanel infnombre = new JPanel(new FlowLayout());
        JLabel jnombre = new JLabel("Maquina:");
        jnombre.setFont(gameFont20);
        infnombre.add(jnombre);
        JPanel colorP2 = new JPanel();
        if (!player2Color.equals(Color.WHITE)) {
            colorP2.setBackground(player2Color);
        } else {
            colorP2.setBackground(Color.RED);
        }
        colorP2.setPreferredSize(new Dimension(60, 60));
        infnombre.add(colorP2);
        inf.add(infnombre);
        JPanel barrerasdis = new JPanel(new GridLayout(4, 2));
        String[] key = {"Normal B:", "Temporal:", "Larga:", "Aliada:"};
        if (!(customsElements.get(key[1]) == null)) {
            for (int i = 0; i < 4; i++) {
                String textField = customsElements.get(key[i]).getText();
                String llaveMapa = String.valueOf(Character.toLowerCase(key[i].charAt(0)));
                JLabel barrera = new JLabel(key[i]);
                barrera.setFont(gameFont20);
                barrera.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel cantb;
                if (!textField.equals("")) {
                    cantb = new JLabel(textField);
                } else {
                    cantb = new JLabel("0");
                }
                barrerasDisP2.put(llaveMapa, cantb);
                cantb.setFont(gameFont20);
                cantb.setHorizontalAlignment(SwingConstants.CENTER);
                barrerasdis.add(barrera);
                barrerasdis.add(cantb);
            }
        }
        else {
            JLabel barrera = new JLabel("Normales");
            barrera.setFont(gameFont20);
            barrera.setHorizontalAlignment(SwingConstants.CENTER);
            String cant = "10";
            JLabel cantb = new JLabel(cant);
            barrerasDisP2.put("n", cantb);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            barrerasdis.add(barrera);
            barrerasdis.add(cantb);
        }
        JPanel barreraEsc = new JPanel(new FlowLayout());
        JLabel barrera = new JLabel("Barrera :");
        barrera.setFont(gameFont20);
        barreraEsc.add(barrera);
        barreraEsc.add(barrierTypePlayer2);
        inf.add(barreraEsc);
        inf.add(barrerasdis);
        inf.setPreferredSize(new Dimension(tampanel, tampanel));
        return inf;
    }

    private JPanel infoPlayerGame2() {
        int tampanel = (int) (screenSize.height * 0.5);
        JPanel inf = new JPanel(new GridLayout(5, 1));
        JPanel infnombre = new JPanel(new FlowLayout());
        JLabel jnombre = new JLabel("Nombre P2:");
        jnombre.setFont(gameFont20);
        JLabel nombre = new JLabel();
        if (!namePlayer2.getText().isEmpty()) {
            nombre = new JLabel(namePlayer2.getText());
        }
        nombre.setFont(gameFont20);
        infnombre.add(jnombre);
        infnombre.add(nombre);
        inf.add(infnombre);
        JPanel colorP2 = new JPanel();
        if (!player2Color.equals(Color.WHITE)) {
            colorP2.setBackground(player2Color);
        }
        else {
            colorP2.setBackground(Color.RED);
        }
        colorP2.setPreferredSize(new Dimension(60, 60));
        infnombre.add(colorP2);
        JPanel barrerasdis = new JPanel(new GridLayout(4, 2));
        String[] key = {"Normal B:", "Temporal:", "Larga:", "Aliada:"};
        if (!(customsElements.get(key[1]) == null)) {
            for (int i = 0; i < customsElements.size(); i++) {
                String textField = customsElements.get(key[i]).getText();
                String llaveMapa = String.valueOf(Character.toLowerCase(key[i].charAt(0)));
                JLabel barrera = new JLabel(key[i]);
                barrera.setFont(gameFont20);
                barrera.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel cantb;
                if (!textField.isEmpty()) {
                    cantb = new JLabel(textField);
                } else {
                    cantb = new JLabel("0");
                }
                barrerasDisP2.put(llaveMapa, cantb);
                cantb.setFont(gameFont20);
                cantb.setHorizontalAlignment(SwingConstants.CENTER);
                barrerasdis.add(barrera);
                barrerasdis.add(cantb);
            }
        }
        else {
            JLabel barrera = new JLabel("Normales");
            barrera.setFont(gameFont20);
            barrera.setHorizontalAlignment(SwingConstants.CENTER);
            String cant = "10";
            JLabel cantb = new JLabel(cant);
            barrerasDisP2.put("n", cantb);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            barrerasdis.add(barrera);
            barrerasdis.add(cantb);
        }
        JPanel barreraEsc = new JPanel(new FlowLayout());
        JLabel barrera = new JLabel("Barrera :");
        barrera.setFont(gameFont20);
        barreraEsc.add(barrera);
        barreraEsc.add(barrierTypePlayer2);
        inf.add(barreraEsc);
        inf.add(barrerasdis);
        inf.setPreferredSize(new Dimension(tampanel, tampanel));
        return inf;
    }

    private JPanel createBoard(Color boardColor) {
        boardPanel = new JPanel(new GridBagLayout());
        int tamBoard = screenSize.height;
        int numero = boardSize.getText().isEmpty() ? 9 : Integer.parseInt(boardSize.getText().trim());
        int SQUARE_SIZE = (int) ((tamBoard) / (2 * numero - 1));
        int BARRIER_WIDTH = (int) ((float) (tamBoard * 0.15) / numero); // Ancho de la barrera ajustado para ser proporcional al tamaño de la celda
        this.board = new JPanel[2 * numero - 1][2 * numero - 1];
        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < 2 * numero - 1; i++) {
            for (int j = 0; j < 2 * numero - 1; j++) {
                gbc.gridx = j;
                gbc.gridy = i;
                int midColumn = numero % 2 == 0 ? numero - 2 : numero - 1;

                if (i % 2 == 0 && j % 2 == 0) {
                    this.board[i][j] = new JPanel(new FlowLayout());
                    this.board[i][j].setBackground(boardColor);
                    this.board[i][j].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));

                    if (i == 0 && j == midColumn) {
                        P2 = new JPanel();
                        P2.setBackground(player2Color);
                        P2.setPreferredSize(new Dimension((int) (SQUARE_SIZE * 0.8), (int) (SQUARE_SIZE * 0.8))); // Reduce size
                        P2.addMouseListener(movePlayer(player2Color));
                        this.board[i][midColumn].add(P2);

                    } else if (i == this.board.length - 1 && j == midColumn) {
                        P1 = new JPanel();
                        P1.setBackground(player1Color);
                        P1.setPreferredSize(new Dimension((int) (SQUARE_SIZE * 0.8), (int) (SQUARE_SIZE * 0.8))); // Reduce size
                        P1.addMouseListener(movePlayer(player1Color));
                        this.board[this.board.length - 1][midColumn].add(P1);
                    }

                } else if (i % 2 == 0 && j % 2 == 1) {
                    this.board[i][j] = createBarrier(Color.GRAY, BARRIER_WIDTH, SQUARE_SIZE);
                    int barrierX = (int) ((j + 1) * SQUARE_SIZE + j * BARRIER_WIDTH);
                    int barrierY = (int) (i * SQUARE_SIZE + i * BARRIER_WIDTH);
                    this.board[i][j].setBounds(barrierX, barrierY, BARRIER_WIDTH, SQUARE_SIZE);
                    // verticales
                    this.board[i][j].addMouseListener(createBarrierMouseListener(this.board[i][j], i, j, true));
                } else if (i % 2 == 1 && j % 2 == 0) {
                    this.board[i][j] = createBarrier(Color.GRAY, SQUARE_SIZE, BARRIER_WIDTH); // Cambia el orden de SQUARE_SIZE y BAR_WIDTH
                    int barrierX = (int) (j * SQUARE_SIZE + j * BARRIER_WIDTH);
                    int barrierY = (int) ((i + 1) * SQUARE_SIZE + i * BARRIER_WIDTH);
                    this.board[i][j].setBounds(barrierX, barrierY, SQUARE_SIZE, BARRIER_WIDTH);
                    // horizontales
                    this.board[i][j].addMouseListener(createBarrierMouseListener(this.board[i][j], i, j, false));
                } else {
                    // espacio vacío
                    JPanel espacio = new JPanel();
                    espacio.setPreferredSize(new Dimension(BARRIER_WIDTH, BARRIER_WIDTH));
                    espacio.setBackground(Color.GRAY);
                    this.board[i][j] = espacio; // Cambia el orden de SQUARE_SIZE y BARRIER_WIDTH
                }
                boardPanel.add(this.board[i][j], gbc);
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

    private MouseAdapter createBarrierMouseListener(JPanel barrierPanel, int row, int col, boolean vertical) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                String type = (turns % 2 == 0) ? String.valueOf(Objects.requireNonNull(QuoridorGUI.this.barrierTypePlayer1.getSelectedItem()).toString().toLowerCase().charAt(0)) :
                        String.valueOf(Objects.requireNonNull(barrierTypePlayer2.getSelectedItem()).toString().toLowerCase().charAt(0));
                int n = (type.charAt(0) == 'l') ? 4 : 2;
                Color overlapColor = Color.blue;
                if (!isCreateBarrier(row, col, vertical, type)) {
                    barrierPanel.setBackground(overlapColor);
                    // vertical
                    if (vertical) {
                        if (e.getComponent() == board[row][col] && row + n <= board.length - 1) {
                            for (int i = row + 1; i <= row + n; i++) {
                                board[i][col].setBackground(overlapColor);
                            }
                        } else if (e.getComponent() == board[row][col] && row + n > board.length - 1) {
                            for (int i = row - 1; i >= row - n; i--) {
                                board[i][col].setBackground(overlapColor);
                            }
                        }
                    }
                    // horizontal
                    else {
                        if (e.getComponent() == board[row][col] && col + n <= board[row].length - 1) {
                            for (int j = col + 1; j <= col + n; j++) {
                                board[row][j].setBackground(overlapColor);
                            }
                        } else if (e.getComponent() == board[row][col] && col + n > board[row].length - 1) {
                            for (int j = col - 1; j >= col - n; j--) {
                                board[row][j].setBackground(overlapColor);
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                String type = (turns % 2 == 0) ? String.valueOf(Objects.requireNonNull(QuoridorGUI.this.barrierTypePlayer1.getSelectedItem()).toString().toLowerCase().charAt(0)) :
                        String.valueOf(Objects.requireNonNull(barrierTypePlayer2.getSelectedItem()).toString().toLowerCase().charAt(0));
                int n = (type.charAt(0) == 'l') ? 4 : 2;
                if (!isCreateBarrier(row, col, vertical, type)) {
                    barrierPanel.setBackground(Color.GRAY);
                    // Verificar si la barrera es vertical
                    if (vertical) {
                        if (e.getComponent() == board[row][col] && row + n <= board.length - 1) {
                            for (int i = row + 1; i <= row + n; i++) {
                                board[i][col].setBackground(Color.GRAY);
                            }
                        } else if (e.getComponent() == board[row][col] && row + n > board.length - 1) {
                            for (int i = row - 1; i >= row - n; i--) {
                                board[i][col].setBackground(Color.GRAY);
                            }
                        }
                    }
                    // Verificar si la barrera es horizontal
                    else {
                        if (e.getComponent() == board[row][col] && col + n <= board[row].length - 1) {
                            for (int j = col + 1; j <= col + n; j++) {
                                board[row][j].setBackground(Color.GRAY);
                            }
                        } else if (e.getComponent() == board[row][col] && col + n > board[row].length - 1) {
                            for (int j = col - 1; j >= col - n; j--) {
                                board[row][j].setBackground(Color.GRAY);
                            }
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    eliminarOpciones();
                    Color colorPlayer = turns % 2 == 0 ? player1Color : player2Color;
                    String type = (turns % 2 == 0) ? String.valueOf(Objects.requireNonNull(QuoridorGUI.this.barrierTypePlayer1.getSelectedItem()).toString().toLowerCase().charAt(0)) :
                            String.valueOf(Objects.requireNonNull(barrierTypePlayer2.getSelectedItem()).toString().toLowerCase().charAt(0));
                    barrierColor = barrierPanel.getBackground();
                    quoridor.addBarrier(colorPlayer, row, col, !vertical, type);
                    changeColorBarrier(colorPlayer, vertical, type, row, col);
                    actualizarNumeroBarreras(colorPlayer, type);
                    actualizarTurnos();


                } catch (QuoridorException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        };
    }
    private void actualizarNumeroBarreras(Color judaor, String type){
        JLabel cambio = (turns%2==0)?barrerasDisP1.get(type):barrerasDisP2.get(type);
        int numero = quoridor.getNumberBarrier(judaor, type);
        cambio.setText(String.valueOf(numero));
    }

    private MouseAdapter movePlayer(Color colorPlayerPeon) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                ArrayList<String> validMovements;
                if(colorPlayerPeon.equals(playerTurno)) {
                    int[][] ubicacioon = quoridor.getPeonsPositions();
                    int[] peon = (turns%2==0)?ubicacioon[0]:ubicacioon[1];
                    validMovements = quoridor.getPeonValidMovements(playerTurno);
                    createvalidMovements(peon[0], peon[1], validMovements, playerTurno);
                }
            }
        };
    }

    private MouseAdapter movePeon(String direction) {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    int[][] ubicacioon = quoridor.getPeonsPositions();
                    int[] peon = (turns%2==0)?ubicacioon[0]:ubicacioon[1];
                    quoridor.movePeon(playerTurno, direction);
                    eliminarOpciones();
                    eliminarPeon(peon[0], peon[1]);
                    ubicacioon = quoridor.getPeonsPositions();
                    peon = (turns%2==0)?ubicacioon[0]:ubicacioon[1];
                    JPanel player = (turns % 2 == 0) ? P1 : P2;
                    agregarPeon(peon[0], peon[1], player);
                    actualizarTurnos();

                } catch (QuoridorException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        };
    }
    private void eliminarPeon(int row, int col){
        board[row][col].removeAll();
        board[row][col].setBackground(boardColor);
        board[row][col].revalidate();
        board[row][col].repaint();
    }
    private void agregarPeon(int row, int column, JPanel player){
        board[row][column].add(player);
        board[row][column].revalidate();
        board[row][column].repaint();

    }
    private void creteButtonsMovements(){
        String[] movements = {"s", "n", "w", "e", "jn", "jw","je","js", "ne","nw", "se", "sw"};
        int ancho = P1.getWidth();
        int altura = P1.getHeight();
        System.out.println("ancho es " + ancho + "altura es " + altura);
        for(int i = 0; i < movements.length; i++){
            JButton move = new JButton();
            move.setPreferredSize(new Dimension(ancho, altura));
            move.setBackground(Color.black);
            move.setForeground(Color.black);
            /// Para que el boton no cambie de color cuando se le da Click
            move.setFocusPainted(false);
            move.setContentAreaFilled(false);
            move.setBorderPainted(false);
            move.setOpaque(true);
            move.addMouseListener(movePeon(movements[i]));
            buttonsMovements.put(movements[i], move);
        }
    }
    private void eliminarOpciones(){
        for(JPanel p: possibleMovements) {
            p.removeAll();
            p.revalidate();
            p.repaint();
        }
        possibleMovements.clear();
    }

    private void createvalidMovements(int row, int column, ArrayList<String> movements, Color playerColorP){
        possibleMovements.clear();
        for(String s : movements){
            if(s.equals("s")){
                board[row+2][column].add(buttonsMovements.get(s));
                board[row+2][column].revalidate();
                board[row+2][column].repaint();
                possibleMovements.add(board[row+2][column]);
            }
            else if(s.equals("n")){
                board[row-2][column].add(buttonsMovements.get(s));
                board[row-2][column].revalidate();
                board[row-2][column].repaint();
                possibleMovements.add(board[row-2][column]);
            }
            else if(s.equals("e")){
                board[row][column+2].add(buttonsMovements.get(s));
                board[row][column+2].revalidate();
                board[row][column+2].repaint();
                possibleMovements.add(board[row][column+2]);
            }
            else if(s.equals("w")){
                board[row][column-2].add(buttonsMovements.get(s));
                board[row][column-2].revalidate();
                board[row][column-2].repaint();
                possibleMovements.add(board[row][column-2]);

            }
            else if(s.equals("js")){
                board[row+4][column].add(buttonsMovements.get(s));
                board[row+4][column].revalidate();
                board[row+4][column].repaint();
                possibleMovements.add(board[row+4][column]);
            }
            else if(s.equals("jn")){
                board[row-4][column].add(buttonsMovements.get(s));
                board[row-4][column].revalidate();
                board[row-4][column].repaint();
                possibleMovements.add(board[row-4][column]);
            }
            else if(s.equals("je")){
                board[row][column+4].add(buttonsMovements.get(s));
                board[row][column+4].revalidate();
                board[row][column+4].repaint();
                possibleMovements.add(board[row][column+4]);
            }
            else if(s.equals("jw")){
                board[row][column-4].add(buttonsMovements.get(s));
                board[row][column-4].revalidate();
                board[row][column-4].repaint();
                possibleMovements.add(board[row][column-4]);
            }
            else if(s.equals("ne")){
                board[row-2][column+2].add(buttonsMovements.get(s));
                board[row-2][column+2].revalidate();
                board[row-2][column+2].repaint();
                possibleMovements.add(board[row-2][column+2]);
            }
            else if(s.equals("nw")){
                board[row-2][column-2].add(buttonsMovements.get(s));
                board[row-2][column-2].revalidate();
                board[row-2][column-2].repaint();
                possibleMovements.add(board[row-2][column-2]);
            }
            else if(s.equals("se")){
                board[row+2][column+2].add(buttonsMovements.get(s));
                board[row+2][column+2].revalidate();
                board[row+2][column+2].repaint();
                possibleMovements.add(board[row+2][column+2]);
            }
            else if(s.equals("sw")){
                board[row+2][column-2].add(buttonsMovements.get(s));
                board[row+2][column-2].revalidate();
                board[row+2][column-2].repaint();
                possibleMovements.add(board[row+2][column-2]);
            }
        }

    }
    public boolean isCreateBarrier(int row, int col, boolean vert,  String type){
        int n = (type.charAt(0) == 'l') ? 4 : 2;
        if(vert) {
            if(row + n <= board.length-1) {
                for (int i = row; i <= row + n; i++) {
                    if (i < board.length - 1 && (board[i][col].getBackground() == player1Color || board[i][col].getBackground() == player2Color)) {
                        return true;
                    }
                }
            }
            else{
                for (int i = row; i >= row - n; i--) {
                    if (i < board.length - 1 && (board[i][col].getBackground() == player1Color || board[i][col].getBackground() == player2Color)) {
                        return true;
                    }
                }
            }
        }
        else{
            if(col + n <= board[row].length-1) {
                for (int j = col; j <= col + n; j++) {
                    if (j < board[row].length - 1 && (board[row][j].getBackground() == player1Color || board[row][j].getBackground() == player2Color)) {
                        return true;
                    }
                }
            }
            else{
                for (int j = col; j >= col - n; j--) {
                    if (j >= 0 && (board[row][j].getBackground() == player1Color || board[row][j].getBackground() == player2Color)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void changeColorBarrier(Color newColor, boolean vertical, String type, int row, int column) {
        int n = (type.charAt(0) == 'l') ? 4 : 2;
        System.out.println(type);
        if(!isCreateBarrier(row,column,vertical, type))
        {
            if (vertical) {
                if(row + n <= board.length-1) {
                    for (int i = row; i <= row + n; i++) {
                        board[i][column].setBackground(newColor);
                    }
                }else{
                    for (int i = row; i >= row - n; i--) {
                        board[i][column].setBackground(newColor);
                    }
                }
            }
            else{
                if(column + n <= board.length-1) {
                    for (int j = column; j <= column + n; j++) {
                        board[row][j].setBackground(newColor);
                    }
                }
                else{
                    for (int j = column; j >= column - n; j--) {
                        board[row][j].setBackground(newColor);
                    }

                }
            }
        }

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
            customsElements.put(labels[i-1], textField);
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
            customsElements.put(labels[i], textField);
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
                if(!boardSize.getText().isEmpty()) {
                    setContentPane(newGameOp);
                    revalidate();
                    repaint();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Debe llenar el tamaño del tablero");
                }
            }
        });
    }

    public static void main(String args[]){
        QuoridorGUI gui = new QuoridorGUI();
        gui.setVisible(true);
    }
}