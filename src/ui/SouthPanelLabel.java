package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class SouthPanelLabel extends JLabel implements Comparable<SouthPanelLabel> {
    private int value;

    public SouthPanelLabel (int value) {
        this.value = value;
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 20));
        setText(String.valueOf(value));
    }

    public void setActive () {
        setForeground(Color.YELLOW);
    }
    public void setNormal () {
        setForeground(Color.WHITE);
    }
    public void setInactive () {
        setForeground(Color.GRAY);
    }

    public int getValue () { return value; }

    @Override
    public int compareTo(SouthPanelLabel other) {
        return Integer.compare(this.value, other.value);
    }

    @Override
    public String toString () {
        return String.valueOf(value);
    }
}
