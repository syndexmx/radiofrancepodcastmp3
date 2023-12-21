package com.github.syndexmx;

import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import static com.github.syndexmx.ExternalPageService.Fetcher.scanUrl;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        String input = "";
        do {
            input = JOptionPane.showInputDialog("Enter any podcast list page from www.radiofrance.fr :");
            if (input!=null && input.length()>0) {
                String htmlReport = scanUrl(input).toString();
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("HTML page File","html");
                    fileChooser.setFileFilter(filter);
                    fileChooser.setCurrentDirectory(new File("."));
                    fileChooser.setSelectedFile(new File("outputreport.html"));
                    int result = fileChooser.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        FileWriter output = new FileWriter(file);
                        BufferedWriter writer = new BufferedWriter(output);
                        writer.write(htmlReport);
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        } while (input!=null && input.length()>0);

    }
}
