package Sphere;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Einfuehrung in die Computergrafik
 * 
 * Beispielprogramm f�r die das Aufsetzen einer Transformationsmatrix
 * Es besteht keine Garantie fuer die Richtigkeit
 * 
 * Das Programm wird nach dem Observermuster angedockt.
 * Mittels der Variablen data k�nnen verschiedene Controller unterschieden
 * werden.
 * 
 *@author F. N. Rudolph (c) 2015
 *26.07.2015
 */
public class MatrixController extends Controller implements ChangeListener, ActionListener{
	//static int lastX = 1200;
	// Datenobjekt fuer den Observer
	Integer data =  new Integer(1);
	// Einflussparameter fuer die Matrix
	double rotX = 0, rotY = 0, rotZ = 0, scale = 1d, dx = 0, dy = 0, dz = 0;
	// die Einzelmatrizen
	double [][] mGesamt = {
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
	};
	double [][] mRotX = {
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
	};
	double [][] mRotY = {
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
	};
	double [][] mRotZ = {
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
	};
	double [][] mScale = {
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
	};
	double [][] mTrans = {
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{0, 0, 0, 1}
	};
	int wX=0, wY=0, wZ=0;
	JFrame jf;
	JPanel jp;
	JSlider jsX, jsY, jsZ, jsS, jsTx, jsTy, jsTz;
	JButton jlX, jlY, jlZ, jlS, jlTx, jlTy, jlTz ;
	/**
	 * @param title �berschrift
	 * @param data 1: Model, 2: View
	 */
	public MatrixController(String title, Integer data){
		this.data = data;
		jf = new JFrame(title);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(180,800);
		jf.setLocation(lastX, 100);
		jp = new JPanel();
		jp.setLayout(new GridLayout(15,1));
		
		JLabel lTitle = new JLabel(title);
		lTitle.setHorizontalAlignment(SwingConstants.CENTER);
		jp.add(lTitle);
		jlX = new JButton();
		jlY= new JButton();
		jlZ = new JButton();
		jlS = new JButton();
		jlTx = new JButton();
		jlTy = new JButton();
		jlTz = new JButton();
		jsX=winkelSlider("X-Achse: ", rotX, 1, jlX, jp, 0, 360, 90);
		jsY=winkelSlider("Y-Achse: ", rotY, 1, jlY, jp, 0, 360, 90);
		jsZ=winkelSlider("Z-Achse: ", rotZ, 1, jlZ, jp, 0, 360, 90);
		jsS=winkelSlider("Skalierung: ", scale, 10, jlS, jp, 0, 50, 10);
		jsTx=winkelSlider("Versch. in X: ", dx, 10, jlTx, jp, -100, 100, 50);
		jsTy=winkelSlider("Versch. in Y: ", dy, 10, jlTy, jp, -100, 100,50);
		jsTz=winkelSlider("Versch. in Z: ", dz, 10, jlTz, jp, -100, 100,50);

		jf.add(jp);
		//jf.pack();
		lastX += jf.getWidth();
		jf.setVisible(true);
		
	}

	public static void main(String[] args) {
		MatrixController mc = new MatrixController("Test", new Integer(0));
		mc.setScale(0.4);
		mc.setRotY(36);
		mc.setDx(0.2);
	}

	/* 
	 * Abfrage der ge�nderten Slider und 
	 * Aufruf der notify Methode
	 * 
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = ((JSlider)e.getSource());
		//System.out.println(source.getName() + ": " + source.getValue());
		if (e.getSource() == jsX){
			rotX =  ((double)source.getValue());
			jlX.setText("Drehung um X: " + rotX + "�");
		} else
			if (e.getSource() == jsY){
				rotY =  ((double)source.getValue());
				jlY.setText("Drehung um Y: " + rotY + "�");
			} else
				if (e.getSource() == jsZ){
					rotZ =  ((double)source.getValue());
					jlZ.setText("Drehung um Z: " + rotZ + "�");
				} else
					if (e.getSource() == jsS){
						scale =  ((double)source.getValue())/10.0;
						jlS.setText("Skalierung: " + scale);
					} else
						if (e.getSource() == jsTx){
							dx =  ((double)source.getValue())/10.0;
							jlTx.setText("Versch. in X: " + dx);
						} else
							if (e.getSource() == jsTy){
								dy =  ((double)source.getValue())/10.0;
								jlTy.setText("Versch. in Y: " + dy);
							} else
								if (e.getSource() == jsTz){
									dz =  ((double)source.getValue())/10.0;
									jlTz.setText("Versch. in Z: " + dz);
								} else {}
		berechnung(); // Ausrechnen der Gesamtmatrix
		super.setChanged();  // Modell ist ge�ndert
		notifyObservers(data); // informiere Beobachter mit Typ des Observable
	}
	/**
	 * Berechne die Gesamttransformation
	 */
	private void berechnung(){
		mRotX[1][1] = Math.cos(radian(rotX));
		mRotX[1][2] = -Math.sin(radian(rotX));
		mRotX[2][1] = Math.sin(radian(rotX));
		mRotX[2][2] = Math.cos(radian(rotX));

		// Rotation um Y
		mRotY[0][0] = Math.cos(radian(rotY));
		mRotY[0][2] = Math.sin(radian(rotY));
		mRotY[2][2] = Math.cos(radian(rotY));
		mRotY[2][0] = -Math.sin(radian(rotY));

		// Rotation um Z
		mRotZ[0][0] = Math.cos(radian(rotZ));
		mRotZ[0][1] = -Math.sin(radian(rotZ));
		mRotZ[1][0] = Math.sin(radian(rotZ));
		mRotZ[1][1] = Math.cos(radian(rotZ));
		
		// Skalierung
		mScale[0][0] = scale;
		mScale[1][1] = scale;
		mScale[2][2] = scale;
		
		// Translation
		mTrans[0][3] = dx;
		mTrans[1][3] = dy;
		mTrans[2][3] = dz;
		
		//Gesamtoperatin
		// EVTL RICHTIG SO
//		mGesamt = Matrix.matMult(mRotX, mRotY);
//		mGesamt = Matrix.matMult(mGesamt, mRotZ);
//		mGesamt = Matrix.matMult(mGesamt, mScale);
//		mGesamt = Matrix.matMult(mGesamt, mTrans);
		mGesamt = Matrix.matMult(mRotX, mRotY);
		mGesamt = Matrix.matMult(mRotZ, mGesamt);
		mGesamt = Matrix.matMult(mScale, mGesamt);
		mGesamt = Matrix.matMult(mTrans, mGesamt);
		
	}
	/**
	 * Rechne den Winkel von Grad in Radian um
	 * @param winkel Winkel in Grad
	 * @return Winkel in Radian
	 */
	private double radian(double winkel){
		return winkel / 180d * Math.PI;
	}

	/**
	 * @return the dx
	 */
	public synchronized double getDx() {
		return dx;
	}

	/**
	 * @return the dy
	 */
	public synchronized double getDy() {
		return dy;
	}

	/**
	 * @return the dz
	 */
	public synchronized double getDz() {
		return dz;
	}

	/**
	 * @return the mGesamt
	 */
	public synchronized double[][] getmGesamt() {
		return mGesamt;
	}

	/* 
	 * Setzt den Action Event der Push Buttons um
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton source = ((JButton) arg0.getSource());
		if (source == jlX){
			jsX.setValue(0);
		} else
			if (source == jlY){
				jsY.setValue(0);
			} else
				if (source == jlZ){
					jsZ.setValue(0);
				} else
					if (source == jlS){
						jsS.setValue(10);
					} else
						if (source == jlTx){
							jsTx.setValue(0);
						} else
							if (source == jlTy){
								jsTy.setValue(0);
							} else
								if (source == jlTz){
									jsTz.setValue(0);
								} else{};
		
	}

	/**
	 * @return the rotX
	 */
	public synchronized double getRotX() {
		return rotX;
	}

	/**
	 * @param rotX the rotX to set
	 */
	public synchronized void setRotX(double rotX) {
		this.rotX = rotX%360d;
		jsX.setValue((int)this.rotX);
	}

	/**
	 * @return the rotY
	 */
	public synchronized double getRotY() {
		return rotY;
	}

	/**
	 * @param rotY the rotY to set
	 */
	public synchronized void setRotY(double rotY) {
		this.rotY = rotY%360d;
		jsY.setValue((int)this.rotY);
	}

	/**
	 * @return the rotZ
	 */
	public synchronized double getRotZ() {
		return rotZ;
	}

	/**
	 * @param rotZ the rotZ to set
	 */
	public synchronized void setRotZ(double rotZ) {
		this.rotZ = rotZ%360d;
		jsZ.setValue((int)this.rotZ);
	}

	/**
	 * @return the scale
	 */
	public synchronized double getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public synchronized void setScale(double scale) {
		this.scale = scale;
		jsS.setValue((int)(scale*10d));
	}

	/**
	 * @param dx the dx to set
	 */
	public synchronized void setDx(double dx) {
		jsTx.setValue((int)(dx*10d));
	}

	/**
	 * @param dy the dy to set
	 */
	public synchronized void setDy(double dy) {
		jsTy.setValue((int)(dy*10d));
	}

	/**
	 * @param dz the dz to set
	 */
	public synchronized void setDz(double dz) {
		jsTz.setValue((int)(dz*10d));
	}
	
	

}
