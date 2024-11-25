/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;


public class Time implements Comparable<Time>, Serializable{
    private int _hour;
    private int _minute;

    public Time() {
        this._hour = 0;
        this._minute = 0;
    }

    public Time(int hour, int minute) {
        this._hour = hour;
        this._minute = minute;
    }

    public int hour() {
        return _hour;
    }

    public void setHour(int hour) {
        this._hour = hour;
    }

    public int minute() {
        return _minute;
    }

    public void setMinute(int minute) {
        this._minute = minute;
    }

    @Override
    public String toString() {
        return String.format("%d:%02d", _hour, _minute);
    }
    
    @Override
    public int compareTo(Time other) {
        if (this._hour != other._hour) {
            return this._hour - other._hour;
        }
        return this._minute - other._minute;
    }

    public boolean equals(Time other) {
        return this._hour == other._hour && this._minute == other._minute;
    }

    public boolean before(Time other) {
        return this.compareTo(other) < 0;
    }

    public boolean after(Time other) {
        return this.compareTo(other) > 0;
    }
    
    public Time timeDifference(Time other) {
        int differenceInMinutes;
        
        if (this.equals(other)) {
            return new Time(0, 0);
        } else if (this.before(other)) {
            differenceInMinutes = (other._hour - this._hour) * 60 + (other._minute - this._minute);
        } else {
            differenceInMinutes = (this._hour - other._hour) * 60 + (this._minute - other._minute);
        }
        
        int hours = differenceInMinutes / 60;
        int minutes = differenceInMinutes % 60;

        return new Time(hours, minutes);
    }
    
}
