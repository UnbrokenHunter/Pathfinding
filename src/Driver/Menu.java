package Driver;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Cells.Maze.Maze;
import Pathfind.Algorithm;
import Utilities.ColorInterpolator;
import Utilities.Vector2;

public class Menu {

    private Window window;
    private JPanel menuPanel;
    private JButton saveSettingsButton;

    private JCheckBox developerModeCheckbox;
    private JTextField startCellXField, startCellYField, endCellXField, endCellYField;
    private JComboBox<Algorithm> algorithmComboBox;
    private JTextField actionTimeField;
    private JComboBox<Maze> mazeComboBox;
    private JTextField numCellRowsField, numCellColsField;
    private JTextField numColorGradientField;
    private JComboBox<String> colorPaletteDropdown;

    public Menu(Window window) {
        this.window = window;
        this.menuPanel = new JPanel(new GridBagLayout()); // This panel holds the menu's UI elements.
        initMenuComponents();
    }

    private void initMenuComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST; // Add this line

        gbc.insets = new Insets(5, 5, 5, 5); // Add this line for padding

        gbc.gridx = 0;
        gbc.gridy = 0;

        // Start Cell Fields
        JPanel startCellPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        startCellXField = new JTextField(5);
        startCellXField.setText(String.valueOf(Config.getStartCell().x));
        startCellYField = new JTextField(5);
        startCellYField.setText(String.valueOf(Config.getStartCell().y));

        startCellPanel.add(new JLabel("X:"));
        startCellPanel.add(startCellXField);
        startCellPanel.add(new JLabel("Y:"));
        startCellPanel.add(startCellYField);

        gbc.gridy++;
        menuPanel.add(new JLabel("Cell Start:"), gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 4; // Span it across multiple columns if necessary
        menuPanel.add(startCellPanel, gbc);

        // End Cell Fields
        JPanel endCellPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        endCellXField = new JTextField(5);
        endCellXField.setText(String.valueOf(Config.getEndCell().x));
        endCellYField = new JTextField(5);
        endCellYField.setText(String.valueOf(Config.getEndCell().y));

        endCellPanel.add(new JLabel("X:"));
        endCellPanel.add(endCellXField);
        endCellPanel.add(new JLabel("Y:"));
        endCellPanel.add(endCellYField);

        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(new JLabel("Cell End:"), gbc);
        gbc.gridx = 2;
        gbc.gridwidth = 4; // Span it across multiple columns if necessary
        menuPanel.add(endCellPanel, gbc);

        // Algorithm ComboBox
        // Assuming you have a way to get all available algorithms, else you might have
        // to add them manually
        algorithmComboBox = new JComboBox<Algorithm>(Config.ALGORITHMS);
        algorithmComboBox.setSelectedItem(Config.getAlgorithm());

        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(new JLabel("Algorithm:"), gbc);
        gbc.gridx = 2;
        menuPanel.add(algorithmComboBox, gbc);

        // Action Time Field
        actionTimeField = new JTextField(5);
        actionTimeField.setText(String.valueOf(Config.getActionTime()));

        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(new JLabel("Action Time:"), gbc);
        gbc.gridx = 2;
        menuPanel.add(actionTimeField, gbc);

        // Maze ComboBox
        // Assuming you have a way to get all available mazes, else you might have to
        // add them manually
        mazeComboBox = new JComboBox<Maze>(Config.MAZES);
        mazeComboBox.setSelectedItem(Config.getMaze());

        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(new JLabel("Maze Type:"), gbc);
        gbc.gridx = 2;
        menuPanel.add(mazeComboBox, gbc);

        // Number of Cell Rows and Columns Fields
        numCellRowsField = new JTextField(5);
        numCellRowsField.setText(String.valueOf(Config.getNumCellRows()));

        numCellColsField = new JTextField(5);
        numCellColsField.setText(String.valueOf(Config.getNumCellCols()));

        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(new JLabel("Number of Cell Rows:"), gbc);
        gbc.gridx = 2;
        menuPanel.add(numCellRowsField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(new JLabel("Number of Cell Columns:"), gbc);
        gbc.gridx = 2;
        menuPanel.add(numCellColsField, gbc);

        // Number of Colors Field
        numColorGradientField = new JTextField(5);
        numColorGradientField.setText(String.valueOf(Config.getNumColorGradient()));

        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(new JLabel("Number of Colors:"), gbc);
        gbc.gridx = 2;
        menuPanel.add(numColorGradientField, gbc);

        // Add the label for the color palette dropdown
        gbc.gridx = 0;
        gbc.gridy++;
        menuPanel.add(new JLabel("Color Palette:"), gbc);
        gbc.gridx = 2;

        // Create a ComboBox with palette names
        colorPaletteDropdown = new JComboBox<>();
        Hashtable<String, Color[]> palettes = ColorInterpolator.getPalettes();

        // Populate the combo box with palette names
        for (String paletteName : palettes.keySet()) {
            colorPaletteDropdown.addItem(paletteName);
        }

        // Set the "Standard" palette as the default
        colorPaletteDropdown.setSelectedItem("Standard");

        // Add the combo box to the menu panel
        gbc.gridx = 2; // This is not strictly needed as it's set before, but for clarity it can remain
        menuPanel.add(colorPaletteDropdown, gbc);

        // Developer Mode Checkbox
        gbc.gridy++;
        gbc.gridx = 0;
        developerModeCheckbox = new JCheckBox("Developer Mode", Config.isDeveloperMode());
        gbc.gridx = 0;
        menuPanel.add(developerModeCheckbox, gbc);

        // Before adding the saveSettingsButton, modify the anchor property:
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Save Settings Button
        saveSettingsButton = new JButton("Save Settings");
        saveSettingsButton.addActionListener(new SaveSettingsActionListener());

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        menuPanel.add(saveSettingsButton, gbc);

        window.add(menuPanel);
    }

    public void drawMenu(Graphics g) {
        drawTitle("Pathfinding Playground", new Vector2(100, 100), new Vector2(100, 100), g);
        // You can add more drawing components here, such as buttons or other graphics.
    }

    // Utility to draw any given title on the screen.
    public void drawTitle(String title, Vector2 pos, Vector2 size, Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(pos.x, pos.y, size.x, size.y);

        g.setFont(new Font("Serif", Font.PLAIN,
                calcFontSize(title, pos, size, Vector2.one.mult(5), g)));
        g.setColor(Color.GRAY);
        Vector2 stringMiddle = calcMidText(title, pos, size, g);
        g.drawString(title, stringMiddle.x, stringMiddle.y);
    }

    private int calcFontSize(String text, Vector2 pos, Vector2 size, Vector2 padding, Graphics g) {
        int maxWidth = size.x - (2 * padding.x);
        int maxHeight = size.y - (2 * padding.y);

        int minFontSize = 1;
        int maxFontSize = 100;

        while (minFontSize <= maxFontSize) {
            int midFontSize = (minFontSize + maxFontSize) / 2;
            Font font = new Font("Serif", Font.PLAIN, midFontSize);
            FontMetrics fm = g.getFontMetrics(font);

            if (fm.stringWidth(text) <= maxWidth && fm.getHeight() <= maxHeight) {
                minFontSize = midFontSize + 1;
            } else {
                maxFontSize = midFontSize - 1;
            }
        }

        return maxFontSize;
    }

    private Vector2 calcMidText(String text, Vector2 pos, Vector2 size, Graphics g) {
        FontMetrics fm = g.getFontMetrics();

        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int x = pos.x + (size.x - textWidth) / 2;
        int y = pos.y + ((size.y - textHeight) / 2) + fm.getAscent();

        return new Vector2(x, y);
    }

    private class SaveSettingsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (isEmptyField(startCellXField) || isEmptyField(startCellYField) || isEmptyField(endCellXField)
                        || isEmptyField(endCellYField) || isEmptyField(actionTimeField)
                        || isEmptyField(numCellRowsField)
                        || isEmptyField(numCellColsField)
                        || algorithmComboBox.getSelectedItem() == null || mazeComboBox.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(menuPanel, "Please fill in all fields.", "Incomplete Input",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // All settings save operations go here
                Config.setDeveloperMode(developerModeCheckbox.isSelected());
                Config.setStartCell(new Vector2(Integer.parseInt(startCellXField.getText()),
                        Integer.parseInt(startCellYField.getText())));
                Config.setEndCell(new Vector2(Integer.parseInt(endCellXField.getText()),
                        Integer.parseInt(endCellYField.getText())));
                Config.setAlgorithm((Algorithm) algorithmComboBox.getSelectedItem());
                Config.setMaze((Maze) mazeComboBox.getSelectedItem());
                Config.setActionTime(Double.parseDouble(actionTimeField.getText()));
                Config.setNumCellRows(Integer.parseInt(numCellRowsField.getText()));
                Config.setNumCellCols(Integer.parseInt(numCellColsField.getText()));
                String chosenPalette = (String) colorPaletteDropdown.getSelectedItem();
                Config.setColorPalette(ColorInterpolator.getPalette(chosenPalette));
                Config.setNumColorGradient((int) Double.parseDouble(numColorGradientField.getText()));

                // Directly execute the post-save actions, no success popup
                hide();
                System.out.println(Config.getConfigState());
                window.startGame();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(menuPanel,
                        "Invalid input in one or more fields. Please enter correct values.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuPanel, "Error saving settings: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean isEmptyField(JTextField field) {
            return field.getText().trim().isEmpty();
        }

    }

    public void show() {
        menuPanel.setVisible(true);
    }

    public void hide() {
        menuPanel.setVisible(false);
    }

}
