/**
 * 
 */
package Sphere;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

/**
 * Einfuehrung in die Computergrafik
 *
 * @author F. N. Rudolph (c) 2015
 *         29.07.2015
 */
public abstract class Controller extends Observable implements ActionListener,
		ChangeListener {
	static int lastX = 950;

	/**
	 * Fuegt einen Slider in das Grid und erstellt die Wertanzeige
	 * vefreinfachend wird der Slider mit int Werten betrieben,
	 * welch im stateChanges umgerechnet werden.
	 * 
	 * @param hint
	 *            Hinweis
	 * @param def
	 *            Default Wert der Anzeige
	 * @param faktor
	 *            Faktor zur Umrechnung auf int
	 * @param jl
	 *            Button fuer Wertanzeige und zum R�cksetzen
	 * @param jp
	 *            Panel, in dem Button und Slider angeordnet werden
	 * @param min
	 *            Mindestwert
	 * @param max
	 *            H�chstwert
	 * @param spacing
	 *            Abstand der angezeigten Werte
	 * @return fertiger Slider
	 */
	protected JSlider winkelSlider(String hint, double def, double faktor,
			JButton jl, JPanel jp, int min, int max, int spacing) {
		JSlider slider = new JSlider(min, max, (int) (def * faktor));
		slider.addChangeListener(this);
		slider.setMinorTickSpacing(spacing / 2);
		slider.setMajorTickSpacing(spacing);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setToolTipText(hint);
		slider.setSnapToTicks(false);
		jl.setText(hint + (def));
		jp.add(jl);
		jp.add(slider);
		jl.setSize((int) slider.getPreferredSize()
				.getWidth(), 15);
		jl.setPreferredSize(new Dimension((int) slider.getPreferredSize()
				.getWidth(), 15));
		jl.addActionListener(this);
		slider.addChangeListener(this);

		return slider;
	}
}
