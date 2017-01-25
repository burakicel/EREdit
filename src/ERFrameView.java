// From the code examples provided in class

import java.awt.geom.AffineTransform;
import static java.awt.geom.AffineTransform.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;	
import java.util.Observable;
import java.util.Observer;
import javax.swing.border.EmptyBorder;

class ERFrameView extends JPanel implements Observer {

	// the view's main user interface
	private JButton button;

	int boxWidth = 200;
	int boxHeight = 100;
	double boxWidthD = (double)boxWidth;
	double boxHeightD = (double)boxHeight;
	// the model that this view is showing
	private ERModel model;
	
	ERFrameView(ERModel model_) {
		// create the view UI
		
		// set the model 
		model = model_;

		setFocusable(true);
		requestFocus();
		requestFocusInWindow();

		
		addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent me) {
				requestFocus();
				requestFocusInWindow();
				if(me.getButton() == MouseEvent.BUTTON3){
					model.deleteSelected();
				}
				java.util.List<Entity> entityList = model.getEntityList();
				for (int i=0; i<entityList.size(); i++){
					entityList.get(i).select(me.getX(), me.getY(),boxWidth, boxHeight);
					model.select();
				}
				repaint();
			}
		});

		addMouseMotionListener(new MouseAdapter() { 
			public void mouseDragged(MouseEvent me) {
				Entity myEnt = model.getEntity(model.getChosenEntity()[0]);
				myEnt.move(me.getX(),me.getY());
				repaint();
			}
		});

		addMouseWheelListener(new MouseWheelListener(){
		    @Override
		    public void mouseWheelMoved(MouseWheelEvent e) {
		    	java.util.List<Entity> entityList = model.getEntityList();
		        if (true) {
		            int notches = e.getWheelRotation();
		            e.consume();
		            if (notches < 0) {
		            	double change1 = (boxWidthD*1.10)-boxWidthD;
		            	double change2 = (boxHeightD*1.10)-boxHeightD;
		                boxWidthD=(boxWidthD*1.10);
		                boxHeightD=(boxHeightD*1.10);
		                boxWidth = (int)boxWidthD;
		                boxHeight = (int)boxHeightD;
		                model.specificZoom(boxWidth,boxHeight);
		                for (int i=0; i<entityList.size(); i++){
							entityList.get(i).zoomInSpecific(e.getX(),e.getY(),change1,change2);
						}
		                repaint();
		            } else {
		            	double change1 = (boxWidthD*0.90)-boxWidthD;
		            	double change2 = (boxHeightD*0.90)-boxHeightD;
		                boxWidthD=(boxWidthD*0.90);
		                boxHeightD=(boxHeightD*0.90);
		                boxWidth = (int)boxWidthD;
		                boxHeight = (int)boxHeightD;
		                model.specificZoom(boxWidth,boxHeight);
		                for (int i=0; i<entityList.size(); i++){
							entityList.get(i).zoomOutSpecific(e.getX(),e.getY(),change1,change2);
						}
		                repaint();
		            }
		        }
		    }
		});

		addKeyListener(new KeyListener() {
		    public void keyPressed(KeyEvent e) {
		    	java.util.List<Entity> entityList = model.getEntityList();
		    	if(e.getKeyCode()==KeyEvent.VK_OPEN_BRACKET && e.isControlDown()){
		    		double change1 = (boxWidthD*0.90)-boxWidthD;
	            	double change2 = (boxHeightD*0.90)-boxHeightD;
	                boxWidthD=(boxWidthD*0.90);
	                boxHeightD=(boxHeightD*0.90);
	                boxWidth = (int)boxWidthD;
	                boxHeight = (int)boxHeightD;
	                model.specificZoom(boxWidth,boxHeight);
	                for (int i=0; i<entityList.size(); i++){
						entityList.get(i).zoomOutSpecific(500,500,change1,change2);
					}
	                repaint();
         		}
         		else if(e.getKeyCode()==KeyEvent.VK_CLOSE_BRACKET && e.isControlDown()){
		    		double change1 = (boxWidthD*1.10)-boxWidthD;
	            	double change2 = (boxHeightD*1.10)-boxHeightD;
	                boxWidthD=(boxWidthD*1.10);
	                boxHeightD=(boxHeightD*1.10);
	                boxWidth = (int)boxWidthD;
	                boxHeight = (int)boxHeightD;
	                model.specificZoom(boxWidth,boxHeight);
	                for (int i=0; i<entityList.size(); i++){
						entityList.get(i).zoomInSpecific(500,500,change1,change2);
					}
	                repaint();
         		}
		    }
		    public void keyReleased(KeyEvent e){};
		    public void keyTyped(KeyEvent e){};
		});

		
		// setup the event to go to the "controller"
		// (this anonymous class is essentially the controller)
		// button.addActionListener(new ActionListener() {
		// 	public void actionPerformed(ActionEvent e) {
		// 		//model.incrementCounter();
		// 	}
		// });	
	}

	public void paintComponent(Graphics g)
    {	
    	super.paintComponent(g);
    	java.util.List<Entity> entityList = model.getEntityList();
    	java.util.List<Arrow> arrowList = model.getArrowList();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int i=0; i<entityList.size(); i++){
        	g.setColor(Color.WHITE);
        	if (entityList.get(i).isSelected()){
        		g.setColor(Color.CYAN);
        	}
        	g.fillRect(entityList.get(i).getX(), entityList.get(i).getY(), boxWidth, boxHeight);
        	g.setColor(Color.BLACK);
        	g.setFont(new Font("Helvetica", Font.PLAIN, 18)); 
        	g.drawString(entityList.get(i).getName(), entityList.get(i).getX()+30, entityList.get(i).getY()+30);
        }

        for (int i=0; i<arrowList.size(); i++){
        	g.setColor(Color.BLACK);
        	int x1 = arrowList.get(i).getSource().getX();
        	int y1 = arrowList.get(i).getSource().getY();
        	int x2 = arrowList.get(i).getTarget().getX();
        	int y2 = arrowList.get(i).getTarget().getY();
        	arrowDraw(g, x1, y1, x2, y2);
        }

    }

    //This function was inspired from stack overflow (Formulas)
    void arrowDraw(Graphics g1, int x1, int y1, int x2, int y2) {
    	Graphics2D g = (Graphics2D) g1.create();
    	if( (x2-x1) <= boxWidth && (x2-x1+boxWidth)>=0 ){
    		if (y2<y1){
    			x1 = x1 + Math.abs(x1-x2-boxWidth)/2;
    			x2 = x1;
    			y2 = y2 + boxHeight;
    		}//
    		else {
    			x1 = x1 + Math.abs(x1-x2-boxWidth)/2;
    			y1 = y1 + boxHeight;
    			x2 = x1;
    		}
    	}
    	else if ( (y2-y1+boxHeight)>=0 && (y2-y1) <= boxHeight ){
    		if(x2<x1){
    			y1 = y1 + Math.abs(y1-y2-boxHeight)/2;
    			y2 = y1;
    			x2 = x2+boxWidth;
    		}
    		else{
    			y1 = y1 + Math.abs(y1-y2-boxHeight)/2;
    			x1 = x1 + boxWidth;
    			y2 = y1;
    		}
    	}
    	else if (y1 < y2){
    		if (x2<x1){
    			x2 = x2+boxWidth;
    		}
    		int x3 = x1;
    		int y3 = y1;
    		x1 = x1 + boxWidth/2;
    		y1 = y2 + boxHeight/2;
    		y2 = y2 + boxHeight/2;
    		g.drawLine(x3+boxWidth/2, y3+boxHeight, x1, y1);
    	}
    	else if(y1 > y2){
    		int x3 = x1;
    		int y3 = y1;
    		if(x1>x2){
    			x2 = x2 + boxWidth;
    		}
    		x1 = x1 + boxWidth/2;
    		y1 = y2 + boxHeight/2;
    		y2 = y2 + boxHeight/2;
    		g.drawLine(x3+boxWidth/2,y3,x1,y1);
    	}
    	double dx = x2 - x1, dy = y2 - y1;
    	double angle = Math.atan2(dy, dx);
    	int len = (int) Math.sqrt(dx*dx + dy*dy);
    	AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
    	at.concatenate(AffineTransform.getRotateInstance(angle));
    	g.transform(at);
    	g.drawLine(0, 0, len, 0);
    	g.fillPolygon(new int[] {len, len-16, len-16, len},
    		new int[] {0, -16, 16, 0}, 4);
    }

	// Observer interface 
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("View: update");
		removeAll();
		repaint();
		//button.setText(Integer.toString(model.getCounterValue()));		
	}
} 
