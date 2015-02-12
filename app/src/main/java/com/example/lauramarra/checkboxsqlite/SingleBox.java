package com.example.lauramarra.checkboxsqlite;

/**
 * Created by lauramarra on 09/02/15.
 */
public class SingleBox {

    boolean selected = false;

    public SingleBox(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
