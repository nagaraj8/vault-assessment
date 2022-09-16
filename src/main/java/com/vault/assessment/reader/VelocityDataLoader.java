package com.vault.assessment.reader;

import com.vault.assessment.exception.VelocityException;
import com.vault.assessment.model.VelocityRequest;
import com.vault.assessment.service.VelocityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.vault.assessment.util.Constants.*;

@Component
public class VelocityDataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityDataLoader.class);

    @Autowired
    VelocityService service;

    Map<String, Set<String>> customerLoadedData = new HashMap<>();
    Map<String, VelocityRequest> clientData = new HashMap<>();

    /**
     * Method takes the filename attribute and reads the json data line by line,
     * processes the velocity data and writes the output to a file line by line.
     *
     * @param fileName
     * @throws VelocityException
     */
    public void readAndProcessVelocityData(String fileName) throws VelocityException {
        LOGGER.info("Started processing the velocity data for the input file: "+fileName);
        try(Scanner sc = new Scanner(getResourceAsStream(fileName))) {
            while (sc.hasNextLine()) {
                JSONObject object = (JSONObject) new JSONParser().parse(sc.nextLine());
                String id = getJsonString(object, ID);
                String customerId = getJsonString(object, CUSTOMER_ID);
                String loadAmount = getJsonString(object, LOAD_AMOUNT);
                String time = getJsonString(object, TIME);
                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);
                LocalDateTime date = LocalDateTime.parse(time, formatter);

                LOGGER.debug("Processing velocity data for the customer Id: "+ customerId +" and Id: "+id);
                Map<String, Object> outputData = new LinkedHashMap<>();
                if(!customerLoadedData.keySet().contains(customerId)) {
                    customerLoadedData.put(customerId, new HashSet<>());
                }

                if(!customerLoadedData.get(customerId).contains(id)) {
                    customerLoadedData.get(customerId).add(id);

                    VelocityRequest data = getVelocityRequest(customerId);
                    service.updateTxnAndWriteToFile(id, customerId, loadAmount, date, outputData, data);
                }
            }
            LOGGER.info("Successfully processed all the velocity data for the given input file: "+INPUT_FILE);
        } catch (VelocityException | ParseException e) {
            LOGGER.error("Failed to process the velocity data: "+e.getCause());
            throw new VelocityException("Failed process the velocity Data: ", e.getCause());
        }
    }

    private static InputStream getResourceAsStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    private VelocityRequest getVelocityRequest(String customerId) {
        VelocityRequest data;
        if(!clientData.keySet().contains(customerId)) {
            data = new VelocityRequest(customerId);
            clientData.put(customerId, data);
        } else {
            data = clientData.get(customerId);
        }
        return data;
    }

    private String getJsonString(JSONObject obj, String input) {
        return obj.get(input).toString();
    }
}
