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

public class QuoridorGUI extends JFrame implements QuoridorObserver{
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
    private JSlider seconds;
    private JPanel panelSegunds;
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
    private JLabel timeTurno;
    private Timer tiempoDis;
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
    private ArrayList<JLabel> casillasVisP1, casillasVisP2;
    private Color regresar = Color.green;
    private Color dobleturno = Color.pink;
    private Color playerTurno;

    private QuoridorGUI() {
        super("Quoridor");
        customsElements = new HashMap<>();
        barrerasDisP1 = new HashMap<>();
        barrerasDisP2 = new HashMap<>();
        casillasVisP1 = new ArrayList<>();
        casillasVisP2 = new ArrayList<>();
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
        abrir.addActionListener(optionOpen());
        guardar.addActionListener(optionSave());
    }
    private void prepareMainWindowElements() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); // Espacio entre componentes
        // Title label
        JLabel titleLabel = new JLabel("QUORIDOR");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0; //Use todo el espacio horizontal
        gbc.weighty = 0.2;//20% del espacio vertical
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleLabel, gbc);
        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        // New game button
        newGameButton = createButton(" NEW GAME ", buttonSize);
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Espacio entre botones
        // Load game button
        loadGameButton = createButton(" LOAD GAME", buttonSize);
        buttonPanel.add(loadGameButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Espacio entre botones
        // Exit button
        exitGameButton = createButton("   EXIT   ", buttonSize);
        buttonPanel.add(exitGameButton);
        // Panel que centra los botones horizontalmente
        JPanel newGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newGamePanel.add(buttonPanel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(newGamePanel, gbc);

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
        newGameOp = new JPanel(new GridBagLayout());
        newGameOp.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        JLabel titleLabel = new JLabel("NEW GAME");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        newGameOp.add(titleLabel, gbc);
        infPlayer = new JPanel();
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
        gbc.gridy = 1;
        gbc.weighty = 0.4;
        newGameOp.add(infPlayer, gbc);
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
        startOptions = new JPanel();
        startOptions.setLayout(new GridBagLayout());
        GridBagConstraints gbcOp = new GridBagConstraints();
        gbcOp.gridx = 0;
        gbcOp.gridy = 0;
        gbcOp.gridwidth = 1;
        startOptions.add(startGameButton,gbcOp);
        gbcOp.gridx = 2;
        startOptions.add(settingsButton,gbcOp);
        gbcOp.gridx = 4;
        startOptions.add(customizeButton,gbcOp);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        newGameOp.add(startOptions, gbc);
    }
    public void prepareNewGameWindowActions() {

        numberPlayersCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) numberPlayersCB.getSelectedItem();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                gbc.weightx = 1.0;
                gbc.weighty = 0.4;
                if (selectedOption.equals("1 PLAYER")) {
                    newGameOp.remove(startOptions);
                    newGameOp.add(infoOnePlayerModality(), gbc);
                    boolean cambiar = false;
                    if(panelSegunds != null){
                        gbc.gridy = 2;
                        gbc.weighty = 0.1;
                        newGameOp.remove(panelSegunds);
                        newGameOp.add(panelSegunds, gbc);
                        cambiar = true;
                    }
                    (gbc.gridy) = (cambiar)?3:2;
                    gbc.weighty = 0.2;
                    newGameOp.add(startOptions, gbc);
                    newGameOp.revalidate();
                    newGameOp.repaint();
                } else if (selectedOption.equals("2 PLAYERS")) {
                    newGameOp.remove(startOptions);
                    newGameOp.add(infoTwoPlayersModality(), gbc);
                    boolean cambiar = false;
                    if(panelSegunds != null){
                        gbc.gridy = 2;
                        gbc.weighty = 0.1;
                        newGameOp.remove(panelSegunds);
                        newGameOp.add(panelSegunds, gbc);
                        cambiar = true;
                    }
                    (gbc.gridy) = (cambiar)?3:2;
                    gbc.weighty = 0.2;
                    newGameOp.add(startOptions, gbc);
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
        difficulties.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) difficulties.getSelectedItem();
                if (!selectedOption.equals("NORMAL")) {
                    boolean panelExists = false;
                    for (Component component : newGameOp.getComponents()) {
                        if (component == panelSegunds) {
                            panelExists = true;
                            break;
                        }
                    }
                    if (!panelExists) {
                        newGameOp.remove(startOptions);
                        panelSegunds = new JPanel(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.insets = new Insets(1, 1, 10, 10);
                        gbc.anchor = GridBagConstraints.CENTER;
                        JLabel title = new JLabel("TIEMPO EN SEGUNDOS");
                        title.setFont(new Font("Consolas", Font.BOLD, 25));
                        title.setHorizontalAlignment(SwingConstants.CENTER);
                        title.setHorizontalTextPosition(SwingConstants.CENTER);
                        panelSegunds.add(title, gbc);
                        gbc.gridy = 1;
                        seconds = new JSlider(JSlider.HORIZONTAL, 0, 720, 0);
                        seconds.setPreferredSize(new Dimension(700, 40));
                        seconds.setMajorTickSpacing(30);
                        seconds.setPaintTicks(true);
                        seconds.setPaintLabels(true);
                        panelSegunds.add(seconds, gbc);
                        GridBagConstraints gbcGame = new GridBagConstraints();
                        gbcGame.gridx = 0;
                        gbcGame.gridy = 2;
                        newGameOp.add(panelSegunds, gbcGame);
                        gbcGame.gridy = 3;
                        newGameOp.add(startOptions, gbcGame);
                        newGameOp.revalidate();
                        newGameOp.repaint();
                    }
                } else {
                    if (panelSegunds != null) {
                        newGameOp.remove(panelSegunds);
                        panelSegunds = null;
                        seconds = null;
                        newGameOp.revalidate();
                        newGameOp.repaint();
                    }
                }
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
                            (seconds != null) ? seconds.getValue() : 0,
                            (String)modalities.getSelectedItem());
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
        modalities.addItem("BEGINNER");
        modalities.addItem("MEDIUM");
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
        GridBagConstraints titleConstraints = new GridBagConstraints(); // Restricciones para el título
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.gridwidth = GridBagConstraints.REMAINDER;
        titleConstraints.insets = new Insets(0, 0, 20, 0);
        JLabel titleLabel = new JLabel("SETTINGS");
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        settingsPanel.add(titleLabel, titleConstraints);
        GridBagConstraints buttonConstraints = new GridBagConstraints();  // Restricciones para los botones
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
        GridBagConstraints applyConstraints = new GridBagConstraints(); // Restricciones para el botón de aplicar
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
        JPanel options = new JPanel(new GridBagLayout());
        GridBagConstraints gbcOptions = new GridBagConstraints();
        gbcOptions.gridx = 0;
        gbcOptions.gridy = 0;
        gbcOptions.weightx = 1;
        JPanel turnos = new JPanel(new GridBagLayout());
        GridBagConstraints gbcTurnos = new GridBagConstraints();
        gbcTurnos.gridx = 0;
        gbcTurnos.gridy = 0;
        String playerName = turns % 2 == 0 ? namePlayer1.getText() : namePlayer2.getText();
        labelTurns = new JLabel("Turno :" + playerName);
        labelTurns.setFont(gameFont20);
        labelTurns.setPreferredSize(new Dimension(200, 60));
        panelTurns = new JPanel();
        playerTurno = player1Color;
        panelTurns.setBackground(playerTurno);
        panelTurns.setPreferredSize(new Dimension(60,60));
        finishButton = createButton("Finish", buttonSize);
        turnos.add(labelTurns,gbcTurnos);
        gbcTurnos.gridx = 1;
        turnos.add(panelTurns, gbcTurnos);
        gbcOptions.anchor = GridBagConstraints.NORTHWEST;
        options.add(turnos, gbcOptions);
        gbcOptions.gridx = 1;
        gbcOptions.anchor = GridBagConstraints.CENTER;
        String gameMode = (String) difficulties.getSelectedItem();
        if(!gameMode.equals("NORMAL")){
            String time = String.valueOf(seconds.getValue());
            timeTurno = new JLabel();
            int segundos = quoridor.getTimePlayer(player1Color);
            tiempoDis = createTimer(segundos, player1Color);
            timeTurno.setFont(gameFont20);
            tiempoDis.start();
            options.add(timeTurno, gbcOptions);
        }else{
            options.add(new Label(), gbcOptions);}
        gbcOptions.gridx = 2;
        gbcOptions.anchor = GridBagConstraints.NORTHEAST;
        options.add(finishButton, gbcOptions);

        gamePanel.add(options, BorderLayout.SOUTH);
    }

    private Timer createTimer(int tiempo, Color Player) {
        return new Timer(1000, new ActionListener() {
            int time = tiempo;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Player.equals(playerTurno)) {
                    if (time > 0) {
                        timeTurno.setText("Tiempo Disponible " + time);
                        time--;
                    } else {
                        timeTurno.setText("Se acabo el tiempo ");
                        ((Timer) e.getSource()).stop();
                    }
                }
                else{
                    ((Timer) e.getSource()).stop();
                }
            }
        });
    }

    private void actualizarTurnos() {
        turns = quoridor.getTurns();
        playerTurno = (turns%2==0)?player1Color:player2Color;
        labelTurns.setText("Turno de: " );
        if(timeTurno!= null){
            int segundos = quoridor.getTimePlayer(player1Color);
            tiempoDis = createTimer(segundos, playerTurno);
            tiempoDis.start();
        }
        panelTurns.setBackground(playerTurno);
        panelTurns.revalidate();
        panelTurns.repaint();
    }
    private JPanel infoPlayerGame() {
        int tampanel = (int) (screenSize.height * 0.5);
        JPanel inf = new JPanel(new GridLayout(5, 1));
        JPanel infnombre = new JPanel(new FlowLayout());
        JLabel jnombre = new JLabel(namePlayer1.getText().isEmpty() ? "Player 1: " : namePlayer1.getText());
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
        JPanel casillasDis = new JPanel(new GridLayout(5, 2));
        casillasDis.add(new Label());
        casillasDis.add(new Label());
        String[] casillas = {"Normal C:", "Teletransportadora:", "Regresar:", "Turno Doble:"};
        if (!(customsElements.get(casillas[0]) == null)) {
            for (int i = 0; i < casillas.length; i++) {
                String llave = casillas[i];
                JLabel casilla = new JLabel(llave);
                casilla.setFont(gameFont20);
                casilla.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel cantb = new JLabel("0");
                casillasVisP1.add(cantb);
                cantb.setFont(gameFont20);
                cantb.setHorizontalAlignment(SwingConstants.CENTER);
                casillasDis.add(casilla);
                casillasDis.add(cantb);
            }
        }
        else{
            JLabel casilla = new JLabel("Normal C");
            casilla.setFont(gameFont20);
            casilla.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel cantb = new JLabel("0");
            casillasVisP1.add(cantb);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            casillasDis.add(casilla);
            casillasDis.add(cantb);
        }
        inf.add(casillasDis);
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
        JPanel casillasDis = new JPanel(new GridLayout(5, 2));
        casillasDis.add(new Label());
        casillasDis.add(new Label());
        String[] casillas = {"Normal C:", "Teletransportadora:", "Regresar:", "Turno Doble:"};
        if (!(customsElements.get(casillas[0]) == null)) {
            for (int i = 0; i < casillas.length; i++) {
                String llave = casillas[i];
                JLabel casilla = new JLabel(llave);
                casilla.setFont(gameFont20);
                casilla.setHorizontalAlignment(SwingConstants.CENTER);
                JLabel cantb = new JLabel("0");
                casillasVisP2.add(cantb);
                cantb.setFont(gameFont20);
                cantb.setHorizontalAlignment(SwingConstants.CENTER);
                casillasDis.add(casilla);
                casillasDis.add(cantb);
            }
        }
        else{
            JLabel casilla = new JLabel("Normal C");
            casilla.setFont(gameFont20);
            casilla.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel cantb = new JLabel("0");
            casillasVisP2.add(cantb);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            casillasDis.add(casilla);
            casillasDis.add(cantb);
        }
        inf.add(casillasDis);
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
        JPanel casillasDis = new JPanel(new GridLayout(5, 2));
        casillasDis.add(new Label());
        casillasDis.add(new Label());
        String[] casillas = {"Normal C:", "Teletransportadora:", "Regresar:", "Turno Doble:"};
        if (!(customsElements.get(casillas[0]) == null)) {
            for (int i = 0; i <= casillas.length-1; i++) {
                if(i <= 3) {
                    String llave = casillas[i];
                    JLabel casilla = new JLabel(llave);
                    casilla.setFont(gameFont20);
                    casilla.setHorizontalAlignment(SwingConstants.CENTER);
                    JLabel cantb = new JLabel("0");
                    casillasVisP2.add(cantb);
                    cantb.setFont(gameFont20);
                    cantb.setHorizontalAlignment(SwingConstants.CENTER);
                    casillasDis.add(casilla);
                    casillasDis.add(cantb);
                }
            }
        }
        else{
            JLabel casilla = new JLabel("Normal C");
            casilla.setFont(gameFont20);
            casilla.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel cantb = new JLabel("0");
            casillasVisP2.add(cantb);
            cantb.setFont(gameFont20);
            cantb.setHorizontalAlignment(SwingConstants.CENTER);
            casillasDis.add(casilla);
            casillasDis.add(cantb);
        }
        inf.add(casillasDis);
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
                    String type = quoridor.getTypeOfField(i, j);
                    this.board[i][j] = new JPanel(new FlowLayout());
                    if(type.equals("Empty")){this.board[i][j].setBackground(boardColor);}
                    else if(type.equals("Rewind")){this.board[i][j].setBackground(regresar);}
                    else if(type.equals("SkipTurn")){this.board[i][j].setBackground(dobleturno);}
                    else if(type.equals("Teleporter")){ Color color = quoridor.getFieldColor(i,j);
                        this.board[i][j].setBackground(color);}
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
                } else if (i % 2 == 0 && j % 2 == 1) { // verticales
                    this.board[i][j] = createBarrier(Color.GRAY, BARRIER_WIDTH, SQUARE_SIZE);
                    int barrierX = (int) ((j + 1) * SQUARE_SIZE + j * BARRIER_WIDTH);
                    int barrierY = (int) (i * SQUARE_SIZE + i * BARRIER_WIDTH);
                    this.board[i][j].setBounds(barrierX, barrierY, BARRIER_WIDTH, SQUARE_SIZE);
                    this.board[i][j].addMouseListener(createBarrierMouseListener(this.board[i][j], i, j, true));
                } else if (i % 2 == 1 && j % 2 == 0) {// horizontales
                    this.board[i][j] = createBarrier(Color.GRAY, SQUARE_SIZE, BARRIER_WIDTH); // Cambia el orden de SQUARE_SIZE y BAR_WIDTH
                    int barrierX = (int) (j * SQUARE_SIZE + j * BARRIER_WIDTH);
                    int barrierY = (int) ((i + 1) * SQUARE_SIZE + i * BARRIER_WIDTH);
                    this.board[i][j].setBounds(barrierX, barrierY, SQUARE_SIZE, BARRIER_WIDTH);
                    this.board[i][j].addMouseListener(createBarrierMouseListener(this.board[i][j], i, j, false));
                } else {
                    JPanel espacio = new JPanel();// espacio vacío
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
                    if (vertical) { // vertical
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
                    else {// horizontal
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
                    if (vertical) {  // Verificar si la barrera es vertical
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
                    else {// Verificar si la barrera es horizontal
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
                }
                catch (QuoridorException ex) {
                    if(ex.getMessage().equals(QuoridorException.ERASE_TEMPORARY_BARRIER)){
                        boolean horizontal = quoridor.getOrientationDeletedTemporary();
                        int[] ubicacion = quoridor.getPositionDeletedTemporary();
                        eliminarTemporal(ubicacion[0],ubicacion[1], horizontal);
                        Color colorPlayer = turns % 2 == 0 ? player1Color : player2Color;
                        String type = (turns % 2 == 0) ? String.valueOf(Objects.requireNonNull(QuoridorGUI.this.barrierTypePlayer1.getSelectedItem()).toString().toLowerCase().charAt(0)) : String.valueOf(Objects.requireNonNull(barrierTypePlayer2.getSelectedItem()).toString().toLowerCase().charAt(0));
                        changeColorBarrier(colorPlayer, vertical, type, row, col);
                        actualizarNumeroBarreras(colorPlayer, type);
                        actualizarTurnos();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        };
    }
    private void actualizarNumeroBarreras(Color judaor, String type){
        JLabel cambio = (turns%2==0)?barrerasDisP1.get(type):barrerasDisP2.get(type);
        int numero = quoridor.getNumberBarrier(judaor, type);
        cambio.setText(String.valueOf(numero));
    }
    private void actualizarCasillasVisitadas(){
        String[] key = {"N","T","R","S"};
        playerTurno = (turns%2==0)?player1Color:player2Color;
        if(casillasVisP1.size() > 1){
            int count = 0;
            ArrayList<JLabel> actualizar =  (turns%2==0)?casillasVisP1:casillasVisP2;
            for(int i = 0; i < actualizar.size();i++){
                if(count <=3) {
                    JLabel l = actualizar.get(count);
                    int numero = quoridor.squaresVisited(playerTurno, key[count]);
                    l.setText(String.valueOf(numero));
                    count += 1;
                }
            }
        }
        else{
            JLabel actualizar = (turns%2==0)? casillasVisP1.get(0):casillasVisP2.get(0);
            int numero = quoridor.squaresVisited(playerTurno, "N");
            actualizar.setText(String.valueOf(numero));

        }
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
                int[][] ubicacioon = quoridor.getPeonsPositions();
                int[] peon = (turns%2==0)?ubicacioon[0]:ubicacioon[1];
                JPanel player = (turns % 2 == 0) ? P1 : P2;
                try {
                    quoridor.movePeon(playerTurno, direction);
                }
                catch (QuoridorException ex) {
                    if(ex.getMessage().equals(QuoridorException.ERASE_TEMPORARY_BARRIER)){
                        boolean horizontal = quoridor.getOrientationDeletedTemporary();
                        int[] ubicacion = quoridor.getPositionDeletedTemporary();
                        eliminarTemporal(ubicacion[0],ubicacion[1], horizontal);
                    }
                    else if(ex.getMessage().equals(QuoridorException.PLAYER_PLAYS_TWICE) ||
                            ex.getMessage().equals(QuoridorException.PEON_HAS_BEEN_TELEPORTED)){}
                    else if(ex.getMessage().equals(QuoridorException.PEON_STEPPED_BACK)){
                        ubicacioon = quoridor.getPeonsPositions();
                        peon = (turns%2==0)?ubicacioon[0]:ubicacioon[1];
                    }
                    else {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }finally {
                    eliminarOpciones();
                    eliminarPeon(peon[0], peon[1]);
                    ubicacioon = quoridor.getPeonsPositions();
                    peon = (turns%2==0)?ubicacioon[0]:ubicacioon[1];
                    agregarPeon(peon[0], peon[1], player);
                    actualizarCasillasVisitadas();
                    actualizarTurnos();
                }
            }
        };
    }
    private void eliminarTemporal(int row, int col, boolean horizontal){
        if(horizontal) {
            if(col +2 <= board.length-1) {
                for (int j = col; j <= col + 2; j++) {
                    board[row][j].setBackground(Color.GRAY);
                }
            }
            else{
                for (int j = col-2; j <= col ; j++) {
                    board[row][j].setBackground(Color.GRAY);
                }
            }
        }
        else{
            if(row +2 <= board.length-1) {
                for (int i = row; i <= row + 2; i++) {
                    board[i][col].setBackground(Color.GRAY);
                }
            }else{
                for (int i = row-2; i <= row; i++) {
                    board[i][col].setBackground(Color.GRAY);
                }
            }
        }
    }

    private void eliminarPeon(int row, int col){
        Color ant = board[row][col].getBackground();
        board[row][col].removeAll();
        board[row][col].setBackground(ant);
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
                casillasVisP1.clear();
                casillasVisP2.clear();
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
            customsElements.put(labels[i], textField2);
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

    private ActionListener optionSave(){
        return (event) -> {
            // Crear un JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showSaveDialog(this);
            if(option == JFileChooser.APPROVE_OPTION) {
                java.io.File selectedFolder = fileChooser.getSelectedFile();
                JTextField nombreArchivoField = new JTextField();
                int result = JOptionPane.showConfirmDialog(this, new Object[]{"Ingrese el nombre del archivo:", nombreArchivoField}, "Guardar jardin", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION && !nombreArchivoField.getText().isEmpty()) {
                    try {
                        String nombreArchivo = nombreArchivoField.getText() + ".dat";
                        java.io.File archivo = new java.io.File(selectedFolder.getAbsolutePath() + java.io.File.separator + nombreArchivo);
                        quoridor.save(archivo);
                    }
                    catch (QuoridorException e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Guardar jardin", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(this, "Nombre de archivo inválido", "Guardar jardin", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(this, "Cancelado por el usuario", "Guardar jardin", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }
    private ActionListener optionOpen(){
        return (event)->{
            JFileChooser openFileChooser = new JFileChooser();
            int result = openFileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = openFileChooser.getSelectedFile();
                try {
                    quoridor = Quoridor.open(selectedFile);
                    repaint();
                    revalidate();
                }
                catch (QuoridorException e){
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Abrir jardin", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Error al abrir archivo", "Abrir jardin", JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }

    @Override
    public void timesUp(String message) {
        JOptionPane.showMessageDialog(this, message, "Fin del juego", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String args[]){
        QuoridorGUI gui = new QuoridorGUI();
        gui.setVisible(true);
    }
}