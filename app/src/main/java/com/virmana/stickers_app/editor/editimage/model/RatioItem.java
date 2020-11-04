package com.virmana.stickers_app.editor.editimage.model;

/**
 * Created by Vimal-CVS on 04/11/2020.
 */
public class RatioItem {
	private String text;
	private Float ratio;
	private int index;

	public RatioItem(String text, Float ratio) {
		super();
		this.text = text;
		this.ratio = ratio;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Float getRatio() {
		return ratio;
	}

	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}// end class
