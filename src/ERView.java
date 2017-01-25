// From the code examples provided in class

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;	
import java.util.Observable;
import java.util.Observer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


class ERView extends JPanel implements Observer {

	// the view's main user interface
	private ERModel model_;
	private JButton button;
	private JButton button2;
	private JList entityList;
	private JList entityList2;
	private JList entityList3;
	private JList arrowList;
	private static String[] entityNames = {"black", "blue", "red"};
	private static String[] arrowNames = {"black", "blue", "red"};
	private DefaultListModel entityModel;
	private DefaultListModel arrowModel;


	private JScrollPane entityPane;
	private JScrollPane arrowPane;
	
	// the model that this view is showing
	private ERModel model;
	
	ERView(ERModel model_) {
		this.model_ = model_;
		JLabel label1 = new JLabel("<html><center>Entities</center></html>", JLabel.CENTER);
		label1.setFont(new Font("Helvetica", Font.PLAIN, 34));

		JLabel label2 = new JLabel("<html><center>Arrows</center></html>", JLabel.CENTER);
		label2.setFont(new Font("Helvetica", Font.PLAIN, 34));
		// create the view UI
		button = new JButton("Add Entity");
		button.setMaximumSize(new Dimension(100, 50));
		button.setPreferredSize(new Dimension(100, 50));

		// create the view UI
		button2 = new JButton("Add Arrow");
		button2.setMaximumSize(new Dimension(100, 50));
		button2.setPreferredSize(new Dimension(100, 50));
		// a GridBagLayout with default constraints centres
		// the widget in the window
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(30, 30, 30, 30));

		//JList
		entityModel = new DefaultListModel();
		arrowModel = new DefaultListModel();
		updateEntities();
		updateArrows();
		entityList = new JList(entityModel);
		entityList.setVisibleRowCount(4);
		entityList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		//JList2
		arrowList = new JList(arrowModel);
		arrowList.setVisibleRowCount(4);
		arrowList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		this.add(label1);
		entityPane = new JScrollPane(entityList);
		add(entityPane);
		this.add(button);
		add(Box.createRigidArea(new Dimension(0,60)));
		this.add(label2);
		arrowPane = new JScrollPane(arrowList);
		add(arrowPane);
		this.add(button2);



		setBackground(Color.white);
		
		// set the model 
		model = model_;

		entityList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (entityList.getSelectedIndex() != -1){
		            	for (int i=0; i<model.getEntityList().size();i++){
			            	model.getEntityList().get(i).disselect();
			            }

			            model.getEntity(entityList.getSelectedIndex()).selectVIP();
			            model.select();
		            }
            }
        });

        arrowList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (arrowList.getSelectedIndex() != -1){
                		for (int i=0; i<model.getEntityList().size();i++){
			            	model.getEntityList().get(i).disselect();
			            }
		            	Entity entz1 = model.getArrowList().get(arrowList.getSelectedIndex()).getSource();
		            	Entity entz2 = model.getArrowList().get(arrowList.getSelectedIndex()).getTarget();
		            	entz1.selectVIP();
		            	entz2.selectVIP();
		            	model.select();
		            }
            }
        });

		
		// setup the event to go to the "controller"
		// (this anonymous class is essentially the controller)
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField name = new JTextField(10);
				JTextField xField = new JTextField(5);
      			JTextField yField = new JTextField(5);
      			JPanel myPanel = new JPanel();
      			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				myPanel.add(new JLabel("Name:"));
				myPanel.add(name);
				myPanel.add(Box.createRigidArea(new Dimension(0,20)));
				myPanel.add(new JLabel("X Coordinate:"));
				myPanel.add(xField);
				myPanel.add(Box.createRigidArea(new Dimension(0,20)));
				myPanel.add(new JLabel("Y Coordinate:"));
				myPanel.add(yField);

				int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Please type the Entity name and place:", JOptionPane.OK_CANCEL_OPTION);

				 if (result == JOptionPane.OK_OPTION) {
         			int xValue = Integer.parseInt(xField.getText());
         			int yValue = Integer.parseInt(yField.getText());
         			model.addEntity(name.getText(), xValue, yValue);
      			}
			}
		});

		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entityList2 = new JList(entityModel);
				entityList2.setVisibleRowCount(4);
				entityList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				entityList3 = new JList(entityModel);
				entityList3.setVisibleRowCount(4);
				entityList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      			JPanel myPanel = new JPanel();
      			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
				myPanel.add(new JLabel("Source Entity:"));
				//myPanel.add(name);
				JScrollPane entityPane1 = new JScrollPane(entityList2);
				myPanel.add(entityPane1);
				myPanel.add(Box.createRigidArea(new Dimension(0,20)));
				myPanel.add(new JLabel("Target Entity:"));
				JScrollPane entityPane2 = new JScrollPane(entityList3);
				myPanel.add(entityPane2);
				//myPanel.add(xField);

				int result = JOptionPane.showConfirmDialog(null, myPanel, 
               "Please type the Entity name and place:", JOptionPane.OK_CANCEL_OPTION);

				 if (result == JOptionPane.OK_OPTION) {
				 	// System.out.println(entityList2.getSelectedValue());
				 	// System.out.println(entityList3.getSelectedValue());
				 	int firstid = model.getEntityIdByName((String)entityList2.getSelectedValue());
				 	int secondid = model.getEntityIdByName((String)entityList3.getSelectedValue());
				 	model.addArrow(firstid,secondid);
         			// int xValue = Integer.parseInt(xField.getText());
         			// int yValue = Integer.parseInt(yField.getText());
         			// model.addEntity(name.getText(), xValue, yValue);
      			}
			}
		});
	}

	private int updateEntities() {
		java.util.List<Entity> entities = model_.getEntityList();
		int count = model_.getEntityCount();
		this.entityNames = new String[count];
		entityModel.removeAllElements();
		for (int i=0; i<count; i++){
			entityModel.addElement(entities.get(i).getName());
		}
		return entityNames.length;
	}

	private int updateArrows() {
		java.util.List<Arrow> arrows = model_.getArrowList();
		int count = model_.getArrowCount();
		this.arrowNames = new String[count];
		arrowModel.removeAllElements();
		for (int i=0; i<count; i++){
			String out = arrows.get(i).getSourceName() + " -> " + arrows.get(i).getTargetName();
			arrowModel.addElement(out);
		}
		return arrowNames.length;
	}

	// Observer interface 
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("ERView: update");
		System.out.println(updateEntities());
		System.out.println(updateArrows());
		if (model.getChosenEntity() != null){
			entityList.setSelectedIndices(model.getChosenEntity());
		}
		if (model.getChosenEntity().length != 0){
			int goodId = model.getChosenEntity()[0];
			int countergood = 0;
			for (int i=0; i<model.getArrowList().size(); i++){
				if (model.getArrowList().get(i).getSource().getId() == goodId || model.getArrowList().get(i).getTarget().getId() == goodId){
					countergood++;
				}
			}
			int[] goodArrows = new int[countergood];
			int countergood2 = 0;
			for (int i=0; i<model.getArrowList().size(); i++){
				if (model.getArrowList().get(i).getSource().getId() == goodId || model.getArrowList().get(i).getTarget().getId() == goodId){
					goodArrows[countergood2] = i;
					countergood2++;
				}
			}
			arrowList.setSelectedIndices(goodArrows);
		}
		//button.setText(Integer.toString(model.getCounterValue()));		
	}
} 
