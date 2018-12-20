package cn.harry12800.j2se.component.utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GBC extends GridBagConstraints {

	/**
	 * @author harry12800
	 */
	private static final long serialVersionUID = 5920165338046425175L;

	/**
	 * 
	 * @param x  第几列
	 * @param y  第几行
	 * @param anchor 依靠方向
	 * @param insets 间隙
	 * @param rows  占几列
	 * @param column 占几行
	 */
	public GBC(int x, int y, int anchor, Insets insets, int column, int rows) {
		this.gridx = x;
		this.gridy = y;
		this.insets = insets;
		this.gridwidth = column;
		this.gridheight = rows;
		this.anchor = anchor;
		this.fill = BOTH;
	}

	public GBC(int x, int y, int anchor, Insets insets, int rows, int h, int weigth) {
		this.gridx = x;
		this.gridy = y;
		this.insets = insets;
		this.gridwidth = rows;
		this.gridheight = h;
		this.anchor = anchor;
		this.ipadx = 0;
		this.ipady = 0;
		this.fill = BOTH;
		this.weighty = weigth;
	}

	/*
     * constructs a GBC with a given gridx and gridy position and all other grid
     * bag constraint values set to the default
     * @param gridx the gridx position
     * @param gridy the gridy position
     */
    public GBC(int gridx, int gridy){
        this.gridx = gridx;
        this.gridy = gridy;
    }

    public GBC(int gridx, int gridy, int gridWidth, int gridHeight){
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridWidth;
        this.gridheight = gridHeight;
    }

    /*
     * sets the anchor
     * @param anchor the anchor style
     * @return this object for further modification
     */

    public GBC setAnchor(int anchor){
        this.anchor = anchor;
        return this;
    }

    /*
     * sets the fill direction
     * @param fill the fill direction
     * @return this object for further modification
     */

    public GBC setFill(int fill){
        this.fill = fill;
        return this;
    }

    /*
     * sets the cell weights
     * @param weightx the cell weight in x direction
     * @param weighty the cell weight in y direction
     * @return this object for further modification
     */

    public GBC setWeight(int weightx, int weighty){
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    /*
     * sets the insets of this cell
     * @param insets distance ths spacing to use in all directions
     * @return this object for further modification
     */

    public GBC setInsets(int distance){
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    /*
     * sets the insets of this cell
     * @param top distance ths spacing to use on top
     * @param bottom distance ths spacing to use on bottom
     * @param left distance ths spacing to use to the left
     * @param right distance ths spacing to use to the  right
     * @return this object for further modification
     */

    public GBC setInsets(int top, int left,int bottom,int right){
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    /*
     * sets the Ipad of this cell
     * @param Ipad distance ths spacing to use in all directions
     * @return this object for further modification
     */

    public GBC setIpad(int ipadx, int ipady){
        this.ipadx = ipadx;
        this.ipadx = ipadx;
        return this;
    }
}
