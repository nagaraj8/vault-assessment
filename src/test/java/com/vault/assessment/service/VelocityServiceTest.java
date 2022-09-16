package com.vault.assessment.service;

import com.vault.assessment.exception.VelocityException;
import com.vault.assessment.model.VelocityRequest;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.vault.assessment.util.Constants.*;
import static com.vault.assessment.util.Constants.LOAD_AMOUNT;
import static org.junit.jupiter.api.Assertions.*;

public class VelocityServiceTest {

    @InjectMocks
    VelocityService underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateTxnAndWriteToFile() throws VelocityException {
        VelocityRequest req = new VelocityRequest("123");
        Map<String, Object> map = new LinkedHashMap<>();
        underTest.updateTxnAndWriteToFile("1", "123", "$1234", LocalDateTime.now(), map, req);
        String lastLine = readLastLineOfFile();
        JSONObject object = (JSONObject) JSONValue.parse(lastLine);
        String id = object.get("id").toString();
        String customerId = object.get("customer_id").toString();
        String accepted = object.get("accepted").toString();
        assertNotNull(lastLine);
        assertEquals("1", id);
        assertEquals("123", customerId);
        assertEquals("true", accepted);
    }

    @Test
    public void updateTxnAndWriteToFileNotAccepted() throws VelocityException {
        VelocityRequest req = new VelocityRequest("123");
        Map<String, Object> map = new LinkedHashMap<>();
        underTest.updateTxnAndWriteToFile("1", "123", "$6000", LocalDateTime.now(), map, req);
        String lastLine = readLastLineOfFile();
        JSONObject object = (JSONObject) JSONValue.parse(lastLine);
        String id = object.get("id").toString();
        String customerId = object.get("customer_id").toString();
        String accepted = object.get("accepted").toString();
        assertNotNull(lastLine);
        assertEquals("1", id);
        assertEquals("123", customerId);
        assertEquals("false", accepted);
    }

    private static String readLastLineOfFile() {
        String lastLine = "";
        try(BufferedReader br = new BufferedReader(new FileReader("src/main/resources/test_result.txt"));) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                lastLine = sCurrentLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastLine;
    }

    @Test(expected = VelocityException.class)
    public void updateTxnAndWriteToFileOnException() throws VelocityException {
        VelocityRequest req = new VelocityRequest("123");
        Map<String, Object> map = null;
        underTest.updateTxnAndWriteToFile("1", "123", "$1234", LocalDateTime.now(), map, req);
    }

    private static InputStream getResourceAsStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
}