package com.vault.assessment.model;

import com.vault.assessment.util.DateUtil;

import java.time.LocalDateTime;
import java.time.Month;

import static com.vault.assessment.util.Constants.*;

public class VelocityRequest {

    private String customerId;

    private int dailyLoads;

    private double currDayAmount;

    private double currWeekAmount;

    private LocalDateTime lastLoadDate;

    public VelocityRequest(String customerId) {
        this.customerId = customerId;
        this.dailyLoads = 0;
        this.currDayAmount = 0;
        this.currWeekAmount = 0;
        this.lastLoadDate = LocalDateTime.of(1960, Month.JANUARY, 1, 16, 29, 45);
    }

    /**
     * Method to check if the daily load amount limit is exceeded for a customer
     *
     * @param loadAmount
     * @return
     */
    public boolean isDailyLoadLimitExceeded(double loadAmount) {
        if(loadAmount > MAX_DAILY_LOAD || (MAX_DAILY_LOAD - currDayAmount < loadAmount)
        || (dailyLoads >= DAILY_TRANSACTIONS_LIMIT)) {
            return true;
        }
        return false;
    }

    /**
     * Method to check if the weekly load amount limit is exceeded for a customer
     *
     * @param loadAmount
     * @return
     */
    public boolean isWeeklyLoadLimitExceeded(double loadAmount) {
        if(loadAmount > MAX_WEEKLY_LOAD || (MAX_WEEKLY_LOAD - currWeekAmount < loadAmount)) {
            return true;
        }
        return false;
    }

    /**
     * Method will update the daily load count and load amount for a customer to keep track on the velocity.
     *
     * @param loadAmount
     */
    public void updateDailyLoad(double loadAmount) {
        currDayAmount += loadAmount;
        dailyLoads += 1;
    }

    /**
     * Method keeps track on the weekly amount for a customer.
     * Amount will be accumulated on the weekly basis.
     *
     * @param loadAmount
     */
    public void updateWeeklyLoad(double loadAmount) {
        currWeekAmount += loadAmount;
    }

    /**
     * Method is used to reset the current amount,
     * daily load amount and current weekly amount
     * if the date/week appears to be the new date and also keeps track of last load date
     *
     * @param loadDate
     */
    public void evaluateLoadDate(LocalDateTime loadDate) {

        if(!DateUtil.compareDates(lastLoadDate, loadDate)) {
            currDayAmount = 0;
            dailyLoads = 0;

            if(!DateUtil.compareWeeksOnDates(lastLoadDate, loadDate)) {
                currWeekAmount = 0;
            }
            lastLoadDate = loadDate;
        }
    }
}
