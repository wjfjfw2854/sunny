package cn.wjf.myaidlserver.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String name;
    private int number;

    public Book(String name){
        this.name = name;
    }

    protected Book(Parcel in) {
        name = in.readString();
        number = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(number);
    }

    public void readFromParcel(Parcel desc)
    {
        name = desc.readString();
    }
}
