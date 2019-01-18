package com.android.esprit.smartreminders.Enums;


import android.icu.util.Calendar;

import java.time.LocalDate;

public enum DayOfTheWeek {
    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday;

    public static DayOfTheWeek DayOfWeekForID(int day) {

        switch (day) {
            case 1: {
                return Sunday;
            }

            case 2: {
                return Monday;
            }

            case 3: {
                return Tuesday;
            }

            case 4: {
                return Wednesday;
            }

            case 5: {
                return Thursday ;
            }

            case 6: {
                return Friday;
            }

            case 7: {
                return Saturday;
            }
            default:
                return Sunday;
        }

    }
    }
