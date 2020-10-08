package com.example.connect4;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

public class Cell implements Parcelable {
    public CellState cellState;

    public Cell(Button button, CellState cellState) {
        this.cellState = cellState;
    }

    protected Cell(Parcel in) {
    }

    public static final Creator<Cell> CREATOR = new Creator<Cell>() {
        @Override
        public Cell createFromParcel(Parcel in) {
            return new Cell(in);
        }

        @Override
        public Cell[] newArray(int size) {
            return new Cell[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
