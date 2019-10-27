package com.abesoft.printingservice.services;

import com.abesoft.printingservice.model.entity.Job;
import com.abesoft.printingservice.model.entity.PdfType;
import com.abesoft.printingservice.repository.JobRepository;
import com.abesoft.printingservice.utils.PSConst;
import com.abesoft.printingservice.utils.Util;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author b.walid
 */
@Service
public class JobsService {

    private final Logger logger = LoggerFactory.getLogger(JobsService.class);
    @Autowired
    JobRepository jobRepository;
    @Autowired
    PdfTypeService pdfTypeService;

    public List<Job> listJobs() {
        Iterable<Job> toReturn = jobRepository.findAll();
        return StreamSupport.stream(toReturn.spliterator(), false)
                .collect(Collectors.toList());
    }

    public long getMaxId() {
        return jobRepository.getMaxId();
    }

    public long saveJob(Job job) {
        Job j = jobRepository.save(job);
        return j.getId();
    }

    public Job getJobName(String name) {
        return jobRepository.findByName(name);
    }

    public ResponseEntity<String> generate(String json) {
        //parser le json
        Gson gson = new Gson();
        JsonObject jsonObj = null;
        try {
            JsonElement element = gson.fromJson(json, JsonElement.class);
            jsonObj = element.getAsJsonObject();
        } catch (JsonSyntaxException ex) {
            return ResponseEntity.badRequest()
                    .body("Format Json incorrect !");
        }
        if (!jsonObj.has("pdfType")) {
            return ResponseEntity.badRequest()
                    .body("Format Json incorrect! <pdfType> introuvable");
        }

        String pdfType = jsonObj.get("pdfType").getAsString();
        PdfType pt = pdfTypeService.getPdfTypeByName(pdfType);
        if (pt == null) {
            logger.error("La configuration du type de PDF " + pdfType + "n'existe pas ! ");
            return ResponseEntity.badRequest()
                    .body("La configuration du type de PDF " + pdfType + "n'existe pas ! ");
        }
        if (!jsonObj.has("printType")) {
            logger.error("job génération pour : " + pdfType + "Format Json incorrect! <printType> introuvable");
            return ResponseEntity.badRequest()
                    .body("Format Json incorrect! <printType> introuvable");
        }

        Job job = new Job();
        job.setName(pt.getName() + " job");
        job.setJson(json);
        job.setPdfType(pt);
        job.setStatus(Job.Status.IN_QUEUE);
        saveJob(job);

        //creer un fichier log pour ce job.
        Util.setLoggingFilePath(pdfType);
        //lancement de la génération du rapport. rejoindre la file d'attante
        logger.info("lancement job de génération pour le type pdf : " + pdfType);
        try {
            lancerGeneration(pt, json);
        } catch (IOException ex) {
            logger.error("IOException est survenue lors de la génération du pdf " + ex.getMessage());
            return ResponseEntity.badRequest()
                    .body("IOException est survenue lors de la génération du pdf ");
        } catch (JRException ex) {
            logger.error("JRException est survenue lors de la génération du pdf " + ex.getMessage());
            return ResponseEntity.badRequest()
                    .body("JRException est survenue lors de la génération du pdf");
        }
        //envoyer la réponse
        logger.info("job de génération pour le type pdf : " + pdfType + " terminé avec succès.");
        return ResponseEntity.status(HttpStatus.OK)
                .body("job de génération pour le type pdf : " + pdfType + " terminé avec succès.");
    }

    public void lancerGeneration(PdfType pdfType, String json) throws IOException, JRException {
        System.setProperty("java.awt.headless", "true");
        JasperDesign design = JRXmlLoader.load(getJrxml(pdfType));
        JasperReport jasperReport = JasperCompileManager.compileReport(design);
        JsonDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(json.getBytes()));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), jsonDataSource);

        String pdfName = "" + new Date().getTime() + pdfType.getName() + ".pdf";
        String pdfsFolder = pdfType.getRessourcesFolderName() + File.separator + PSConst.PDFS_FOLDER_NAME + File.separator;
        Files.createDirectories(Paths.get(pdfsFolder));
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfsFolder + pdfName);
    }

    private File getJrxml(PdfType pdfType) {
        String path = pdfType.getRessourcesFolderName() + File.separator + PSConst.JRXML_FOLDER_NAME + File.separator + pdfType.getJrxmlName();
        return new File(path);
    }
}
