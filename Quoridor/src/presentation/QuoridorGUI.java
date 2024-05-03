package src.presentation;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.desktop.QuitEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;

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
    private JButton startGame;
    private JButton settings;
    private JPanel infPlayer, startOp;
    private JTextField namePlayer;
    private JTextField namePlayer2;
    //Ajustes
    private JPanel settingsPanel;
    private JButton colorBoard, colorP1, colorP2, colorB1, colorB2, apply;
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
        mainPanel.setLayout(new GridLayout(3, 1, 0, 10));
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
        buttonPanel.add(exitGame);
        buttonPanel.add(Box.createVerticalStrut(10));
        loadGame = createButton(" LOAD GAME", buttonSize);
        buttonPanel.add(loadGame);
        buttonPanel.add(Box.createVerticalGlue());
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
        JLabel numPlayers = new JLabel("NUMBER PLAYERS");
        numPlayers.setPreferredSize(buttonSize);
        numPlayers.setFont(new Font("Consolas", Font.BOLD, 30));
        nPlayers = new JComboBox<String>();
        nPlayers.setPreferredSize(buttonSize);
        nPlayers.addItem("1 PLAYERS");
        nPlayers.addItem("2 PLAYERS");
        nPlayers.setPreferredSize(buttonSize);
        nPlayers.setFont(new Font("Consolas", Font.BOLD, 30));

        nPlayers.setFont(new Font("Consolas", Font.BOLD, 30));
        nPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        numPlayers.setAlignmentX(Component.CENTER_ALIGNMENT);
        infPlayer.add(numPlayers);
        infPlayer.add(nPlayers);
        newGameOp.add(infPlayer);
        //Inicio y ajustes
        startGame = createButton("START", buttonSize);
        settings = createButton("SETTINGS", buttonSize);
        settings.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);


        startOp.add(startGame);
        startOp.add(new Label());
        startOp.add(settings);
        newGameOp.add(startOp);
    }
    public void prepareNewGameWindowActions(){
        nPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) nPlayers.getSelectedItem();
                if (selectedOption.equals("1 PLAYERS")) {
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


    }
    private JPanel infoPlayer(){
        infPlayer.removeAll();
        infPlayer.setLayout(new GridLayout(3, 1, 0, 10));
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
        infPlayer.add(panel);
        infPlayer.add(modos);
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
        difficulty = new JComboBox<String>();
        difficulty.setPreferredSize(buttonSize);
        difficulty.setFont(new Font("Consolas", Font.BOLD, 30));
        difficulty.addItem("NORMAL");
        difficulty.addItem("TIME TRIAL");
        difficulty.addItem("TIMED");
        difficulty.setPreferredSize(buttonSize);
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
        applyConstraints.insets = new Insets(200, (screenSize.width)-250, 0, 0); // Espacio entre los botones y el borde inferior

        apply = createButton("APPLY", buttonSize);
        settingsPanel.add(apply, applyConstraints);
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
        apply.addActionListener(new ActionListener(){
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

    public static void main(String args[]){
        QuoridorGUI gui = new QuoridorGUI();
        gui.setVisible(true);
    }




}