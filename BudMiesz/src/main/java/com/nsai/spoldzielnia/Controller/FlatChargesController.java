package com.nsai.spoldzielnia.Controller;

import org.springframework.stereotype.Controller;

@Controller
public class FlatChargesController {

    public static double cprad_stawka = 0.65;
    public static double cgaz_stawka = 1.3;
    public static double cwoda_ciepla_stawka = 35.0;
    public static double cwoda_zimna_stawka = 6.0;
    public static double cscieki_stawka = 9.1;
    public static double cogrzewanie_stawka = 0.3;
    public static double cfunduszRemontowy_stawka = 2.00;

    public static double prad_ryczalt = 65.0;//kWh na osobe
    public static double gaz_ryczalt = 16;//m3 na osobe
    public static double woda_ciepla_ryczalt = 0.500; //m3 na osobe
    public static double woda_zimna_ryczalt = 1.000; // m3 na osobe
    public static double scieki_ryczalt = 1.500; // m3 na osobe
    public static double ogrzewanie_ryczalt = 50;// kwh na osobe
    public static double funduszRemontowy_ryczalt = 60;// średnio na mieszkanie



}
