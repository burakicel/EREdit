/**
 * Created by bwbecker on 2016-10-10.
 */

import javax.swing.*;
import java.awt.*;

public class EREdit {

    public static void main(String[] args) {
        System.out.println("Compiled");
        JFrame frame = new JFrame("ERedit by Burak Icel");

        //Create Model
        ERModel model = new ERModel();

        //Create View
        ERFrameView frameView =  new ERFrameView(model);
        frameView.setLayout(new BorderLayout());
        ERView view = new ERView(model);
        model.addObserver(view);
        model.addObserver(frameView);

        // let all the views know that they're connected to the model
		model.notifyObservers();

		// create the window
		JPanel p = new JPanel(new BorderLayout());
		frame.getContentPane().add(p);
		p.add(view, BorderLayout.EAST);
        JScrollPane panelPane = new JScrollPane();
        frameView.setPreferredSize(new Dimension(2000, 2000));
        panelPane.setViewportView(frameView);
        panelPane.setFocusable(true);
        panelPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        p.add(panelPane, BorderLayout.CENTER);

        frame.setPreferredSize(new Dimension(1000,800));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
    }
}
