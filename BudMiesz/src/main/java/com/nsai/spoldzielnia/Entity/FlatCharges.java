package com.nsai.spoldzielnia.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flat_charges")
public class FlatCharges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    @JoinColumn(name="flat_id", nullable=false)
    private Flat flat;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date data;

    private boolean accepted;

    private boolean zaplacone;



    private double prad;
    private double gaz;
    private double woda_ciepla;
    private double woda_zimna;
    private double scieki;
    private double ogrzewanie;
    private double funduszRemontowy;

    private double prad_stawka;
    private double gaz_stawka;
    private double woda_ciepla_stawka;
    private double woda_zimna_stawka;
    private double scieki_stawka;
    private double ogrzewanie_stawka;
    private double funduszRemontowy_stawka;


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }


    public boolean isZaplacone() {
        return zaplacone;
    }

    public void setZaplacone(boolean zaplacone) {
        this.zaplacone = zaplacone;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrad() {
        return prad;
    }

    public void setPrad(double prad) {
        this.prad = prad;
    }

    public double getGaz() {
        return gaz;
    }

    public void setGaz(double gaz) {
        this.gaz = gaz;
    }

    public double getWoda_ciepla() {
        return woda_ciepla;
    }

    public void setWoda_ciepla(double woda_ciepla) {
        this.woda_ciepla = woda_ciepla;
    }

    public double getWoda_zimna() {
        return woda_zimna;
    }

    public void setWoda_zimna(double woda_zimna) {
        this.woda_zimna = woda_zimna;
    }

    public double getScieki() {
        return scieki;
    }

    public void setScieki(double scieki) {
        this.scieki = scieki;
    }

    public double getOgrzewanie() {
        return ogrzewanie;
    }

    public void setOgrzewanie(double ogrzewanie) {
        this.ogrzewanie = ogrzewanie;
    }

    public double getFunduszRemontowy() {
        return funduszRemontowy;
    }

    public void setFunduszRemontowy(double funduszRemontowy) {
        this.funduszRemontowy = funduszRemontowy;
    }

    public double getPrad_stawka() {
        return prad_stawka;
    }

    public void setPrad_stawka(double prad_stawka) {
        this.prad_stawka = prad_stawka;
    }

    public double getGaz_stawka() {
        return gaz_stawka;
    }

    public void setGaz_stawka(double gaz_stawka) {
        this.gaz_stawka = gaz_stawka;
    }

    public double getWoda_ciepla_stawka() {
        return woda_ciepla_stawka;
    }

    public void setWoda_ciepla_stawka(double woda_ciepla_stawka) {
        this.woda_ciepla_stawka = woda_ciepla_stawka;
    }

    public double getWoda_zimna_stawka() {
        return woda_zimna_stawka;
    }

    public void setWoda_zimna_stawka(double woda_zimna_stawka) {
        this.woda_zimna_stawka = woda_zimna_stawka;
    }

    public double getScieki_stawka() {
        return scieki_stawka;
    }

    public void setScieki_stawka(double scieki_stawka) {
        this.scieki_stawka = scieki_stawka;
    }

    public double getOgrzewanie_stawka() {
        return ogrzewanie_stawka;
    }

    public void setOgrzewanie_stawka(double ogrzewanie_stawka) {
        this.ogrzewanie_stawka = ogrzewanie_stawka;
    }

    public double getFunduszRemontowy_stawka() {
        return funduszRemontowy_stawka;
    }

    public void setFunduszRemontowy_stawka(double funduszRemontowy_stawka) {
        this.funduszRemontowy_stawka = funduszRemontowy_stawka;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }
}
