package com.kwame.tpay.listeners;

import java.util.Calendar;

public interface CalenderListener {

    void onFirstDateSelected(Calendar startDate);

    void onDateRangeSelected(Calendar startDate, Calendar endDate);
}
