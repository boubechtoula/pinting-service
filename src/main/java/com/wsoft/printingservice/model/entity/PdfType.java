/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wsoft.printingservice.model.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author b.walid
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "pdftype")
public class PdfType extends BaseEntity {

    /**
     * Nom du PdfType
     */
    @Column(name = "name", nullable = true)
    private String name;
    /**
     * nom de l'imprimente
     */
    @Column(name = "print_name", nullable = true)
    private String printName;

    /**
     * nom/path du fichier jrxml principale (le cas ou y aura plusieurs
     */
    @Column(name = "jrxml_name", nullable = true)
    private String jrxmlName;

    /**
     * nom/path du dossier qui va contenir les ressources (jrxml, images, autres
     * ...)
     */
    @Column(name = "ressources_folder_name", nullable = true)
    private String ressourcesFolderName;

    @OneToMany(mappedBy = "pdfType", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Job> jobs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getJrxmlName() {
        return jrxmlName;
    }

    public void setJrxmlName(String jrxmlName) {
        this.jrxmlName = jrxmlName;
    }

    public String getRessourcesFolderName() {
        return ressourcesFolderName;
    }

    public void setRessourcesFolderName(String ressourcesFolderName) {
        this.ressourcesFolderName = ressourcesFolderName;
    }

    @Override
    public String toString() {
        return "PdfType{" + "name=" + name + ", printName=" + printName + ", jrxmlName=" + jrxmlName + ", ressourcesFolderName=" + ressourcesFolderName + '}';
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

}
