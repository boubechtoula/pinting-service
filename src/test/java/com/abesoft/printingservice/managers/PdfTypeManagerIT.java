package com.abesoft.printingservice.managers;

import com.abesoft.printingservice.model.entity.PdfType;
import java.nio.file.Path;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author b.walid
 */
public class PdfTypeManagerIT {

    public PdfTypeManagerIT() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createPdfType method, of class PdfTypeManager.
     */
    /**
     * Test of uploadJrxmlFiles method, of class PdfTypeManager.
     */
    @Test
    public void testUploadJrxmlFiles() {
        System.out.println("uploadJrxmlFiles");
        FileUploadEvent event = null;
        PdfTypeManager instance = new PdfTypeManager();
        instance.uploadJrxmlFiles(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of uploadRessourceFiles method, of class PdfTypeManager.
     */
    @Test
    public void testUploadRessourceFiles() {
        System.out.println("uploadRessourceFiles");
        FileUploadEvent event = null;
        PdfTypeManager instance = new PdfTypeManager();
        instance.uploadRessourceFiles(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveUploadedFile method, of class PdfTypeManager.
     */
    @Test
    public void testSaveUploadedFile() {
        System.out.println("saveUploadedFile");
        String path = "";
        UploadedFile uploadedFile = null;
        PdfTypeManager instance = new PdfTypeManager();
        Path expResult = null;
        Path result = instance.saveUploadedFile(path, uploadedFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getListPdfTypes method, of class PdfTypeManager.
     */
    @Test
    public void testGetListPdfTypes() {
        System.out.println("getListPdfTypes");
        PdfTypeManager instance = new PdfTypeManager();
        List<PdfType> expResult = null;
        List<PdfType> result = instance.getListPdfTypes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
