package com.vault.assessment.reader;

import com.vault.assessment.exception.VelocityException;
import com.vault.assessment.service.VelocityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static com.vault.assessment.util.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VelocityDataLoaderTest {

    @InjectMocks
    VelocityDataLoader underTest;

    @Mock
    VelocityService service;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void readAndProcessVelocityData() throws VelocityException {
        doNothing().when(service).updateTxnAndWriteToFile(any(), any(), any(), any(), any(), any());
        underTest.readAndProcessVelocityData(INPUT_FILE);
        verify(service);
    }

    @Test(expected = VelocityException.class)
    public void readAndProcessVelocityDataOnException() throws VelocityException {
        doThrow(new VelocityException()).when(service).updateTxnAndWriteToFile(any(), any(), any(), any(), any(), any());
        underTest.readAndProcessVelocityData(INPUT_FILE);
        verify(service);
    }
}