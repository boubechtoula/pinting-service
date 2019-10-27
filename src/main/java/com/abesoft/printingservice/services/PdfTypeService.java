package com.abesoft.printingservice.services;

import com.abesoft.printingservice.model.entity.PdfType;
import com.abesoft.printingservice.repository.PdfTypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author b.walid
 */
@Service
public class PdfTypeService {

    private final Logger logger = LoggerFactory.getLogger(PdfTypeService.class);
    @Autowired
    PdfTypeRepository pdfTypeRepository;

    public List<PdfType> listPdfTypes() {
        Iterable<PdfType> toReturn = pdfTypeRepository.findAll();
        return StreamSupport.stream(toReturn.spliterator(), false)
                .collect(Collectors.toList());
    }

    public long getMaxId() {
        return pdfTypeRepository.getMaxId();
    }

    public long savePdfType(PdfType pdfType) {
        PdfType pt = pdfTypeRepository.save(pdfType);
        return pt.getId();
    }

    public PdfType getPdfTypeByName(String name) {
        return pdfTypeRepository.findByName(name);
    }
}
