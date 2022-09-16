package com.vault.assessment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vault.assessment.exception.VelocityException;
import com.vault.assessment.model.VelocityRequest;
import com.vault.assessment.reader.VelocityDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Map;

import static com.vault.assessment.util.Constants.*;

@Service
public class VelocityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityService.class);


    /**
     * Method takes velocity request data and checks all the criteria and conditions before updating the transaction.
     * If all the velocity conditions are satisfied for a customer
     * then the velocity request will be updated as a transaction.
     *
     * @param id
     * @param customerId
     * @param loadAmount
     * @param date
     * @param outputData
     * @param data
     * @throws VelocityException
     */
    public void updateTxnAndWriteToFile(String id, String customerId, String loadAmount, LocalDateTime date, Map<String, Object> outputData, VelocityRequest data) throws VelocityException {
        LOGGER.info("Updating the transaction for the customer Id: "+ customerId +" and Id: "+id);
        try(FileWriter file = new FileWriter(OUTPUT_FILE, true)) {
            outputData.put(ID, id);
            outputData.put(CUSTOMER_ID, customerId);
            if(updateTransaction(data, Double.parseDouble(loadAmount.substring(1)), date)) {
                outputData.put(ACCEPTED_STRING, true);
            } else {
                outputData.put(ACCEPTED_STRING, false);
            }
            LOGGER.debug("Writing the output response to file for id: "+id);
            String json = new ObjectMapper().writeValueAsString(outputData);
            file.write(json+LINE_SEPARATOR);
        } catch (Exception e) {
            LOGGER.error("Exception while processing the velocity data in service: "+e.getCause());
            throw new VelocityException("Failed to write the Velocity json data to file: ", e.getCause());
        }
    }

    private boolean updateTransaction(VelocityRequest requestData, double loadAmount, LocalDateTime loadDate) {
        requestData.evaluateLoadDate(loadDate);
        boolean isDailyLoadExceeded = requestData.isDailyLoadLimitExceeded(loadAmount);
        boolean isWeeklyLoadExceeded = requestData.isWeeklyLoadLimitExceeded(loadAmount);

        if(!isDailyLoadExceeded && !isWeeklyLoadExceeded) {
            requestData.updateDailyLoad(loadAmount);
            requestData.updateWeeklyLoad(loadAmount);
            return true;
        }

        return false;
    }
}
