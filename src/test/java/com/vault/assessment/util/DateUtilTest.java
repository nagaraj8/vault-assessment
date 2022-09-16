package com.vault.assessment.util;


import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateUtilTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void compareDates() {
        LocalDateTime date1 = LocalDateTime.of(2017, Month.JANUARY, 1, 6, 29, 45);
        LocalDateTime date2 = LocalDateTime.of(2017, Month.JANUARY, 1, 16, 29, 45);
        boolean isSameDay = DateUtil.compareDates(date1, date2);
        assertTrue(isSameDay);
    }

    @Test
    public void compareDatesOnDifferentDay() {
        LocalDateTime date1 = LocalDateTime.of(2017, Month.JANUARY, 2, 6, 29, 45);
        LocalDateTime date2 = LocalDateTime.of(2017, Month.JANUARY, 1, 16, 29, 45);
        boolean isSameDay = DateUtil.compareDates(date1, date2);
        assertTrue(!isSameDay);
    }

    @Test
    public void compareWeeksOnDates() {
        LocalDateTime date1 = LocalDateTime.of(2017, Month.JANUARY, 2, 6, 29, 45);
        LocalDateTime date2 = LocalDateTime.of(2017, Month.JANUARY, 1, 16, 29, 45);
        boolean isSameWeek = DateUtil.compareWeeksOnDates(date1, date2);
        assertTrue(isSameWeek);
    }

    @Test
    public void compareWeeksOnDatesOnDifferentWeeks() {
        LocalDateTime date1 = LocalDateTime.of(2017, Month.JANUARY, 2, 6, 29, 45);
        LocalDateTime date2 = LocalDateTime.of(2017, Month.JANUARY, 10, 16, 29, 45);
        boolean isSameWeek = DateUtil.compareWeeksOnDates(date1, date2);
        assertTrue(!isSameWeek);
    }
}