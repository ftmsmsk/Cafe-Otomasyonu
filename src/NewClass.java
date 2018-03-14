
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class NewClass {
  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
        { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
    Object columnNames[] = { "Column One", "Column Two", "Column Three" };
    JTable table = new JTable(rowData, columnNames);

    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane, BorderLayout.CENTER);
    frame.setSize(300, 150);
    frame.setVisible(true);

  }
}