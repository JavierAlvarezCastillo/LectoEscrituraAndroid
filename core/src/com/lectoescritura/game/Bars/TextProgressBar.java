package com.lectoescritura.game.Bars;

import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TextProgressBar extends ProgressBar{
    private Label labelName;
    private Label labelValue;
    private LabelStyle labelStyle;
    private boolean valueVisible = true;

    public TextProgressBar(String text, float min, float max, float stepSize,
                           boolean vertical, ProgressBarStyle style, LabelStyle labelStyle) {
        super(min, max, stepSize, vertical, style);

        this.labelStyle = new LabelStyle(labelStyle);
        this.labelStyle.fontColor.a = 0.7f;

        labelName = new Label(text, this.labelStyle);
        add(labelName).expandX().left();

        labelValue = new Label((int) min + "/" + (int) max, this.labelStyle);
        add(labelValue).right();
    }

    public Label getLabelName() {
        return labelName;
    }

    public void setLabelName(Label labelName) {
        this.labelName = labelName;
    }

    public Label getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(Label labelValue) {
        this.labelValue = labelValue;
    }

    public boolean isValueVisible() {
        return valueVisible;
    }

    public void setValueVisible(boolean valueVisible) {
        this.valueVisible = valueVisible;
        labelValue.setVisible(valueVisible);
    }

    @Override
    public boolean setValue(float value){
        if(!super.setValue(value))
            return false;

        refreshValue();
        return true;
    }

    @Override
    public void setRange(float min, float max){
        super.setRange(min, max);
        refreshValue();
    }

    public void refreshValue(){
        labelValue.setText((int) value+"/"+ (int) max);
    }

    public LabelStyle getLabelStyle() {
        return labelStyle;
    }

    public void setLabelStyle(LabelStyle labelStyle) {
        this.labelStyle = labelStyle;
    }

    public float getLabelAlpha() {
        return this.labelStyle.fontColor.a;
    }

    public void setLabelAlpha(float labelAlpha) {
        this.labelStyle.fontColor.a = labelAlpha;
    }

}