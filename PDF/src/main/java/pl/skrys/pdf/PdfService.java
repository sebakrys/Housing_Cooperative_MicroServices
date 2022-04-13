package pl.skrys.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class PdfService{

    public void generateRachunekPdf(int year, int month, boolean ryczalt,
                                    long flatid, long buildingid,
                                    String street, String bNr, String fNr, String postalcode, String city,
                                    double fr, double fr_rate,
                                    double g, double g_rate,
                                    double og, double og_rate,
                                    double pr, double pr_rate,
                                    double sc, double sc_rate,
                                    double cw, double cw_rate,
                                    double zw, double zw_rate,
                                    String usr_names, List<String> otherLocators,
                                    HttpServletResponse response) {
        try {


            OutputStream o = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/pdf");//todo \/ nazwa pliku: idBud_idFlat_Rok_Miesiac.pdf
            response.setHeader("Content-Disposition", "inline; filename=" +buildingid+"_"+flatid+"_"+year+"_"+month + ".pdf");
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, o);
            pdf.open();
            if(ryczalt){
                pdf.add(new Paragraph("Bill with a lump sum"));
            }else{
                pdf.add(new Paragraph("Bill"));
            }
            pdf.add(new Paragraph(Chunk.NEWLINE));
            PdfPTable table1 = new PdfPTable(2);

            table1.addCell("Address");
            table1.addCell("Ul. "+street+" nr. "+bNr+"/"+fNr+"\r\n"
                    +postalcode+", "+city);

            table1.addCell("Period");
            table1.addCell(year+" "+month);

            pdf.add(table1);



            PdfPTable table2 = new PdfPTable(4);

            double suma = 0;
            DecimalFormat df = new DecimalFormat("#.00");

            table2.addCell("Fundusz Remontowy");
            table2.addCell(String.valueOf(fr));
            table2.addCell(String.valueOf(fr_rate));
            table2.addCell(String.valueOf(df.format(fr_rate
                    *fr)));
            suma+=fr_rate
                    *fr;

            table2.addCell("Gaz");
            table2.addCell(String.valueOf(g));
            table2.addCell(String.valueOf(g_rate));
            table2.addCell(String.valueOf(df.format(g_rate
                    *g)));
            suma+=g_rate
                    *g;

            table2.addCell("Ogrzewanie");
            table2.addCell(String.valueOf(og));
            table2.addCell(String.valueOf(og_rate));
            table2.addCell(String.valueOf(df.format(og_rate
                    *og)));
            suma+=og_rate
                    *og;

            table2.addCell("Prad");
            table2.addCell(String.valueOf(pr));
            table2.addCell(String.valueOf(pr_rate));
            table2.addCell(String.valueOf(df.format(pr_rate
                    *pr)));
            suma+=pr_rate
                    *pr;

            table2.addCell("Scieki");
            table2.addCell(String.valueOf(sc));
            table2.addCell(String.valueOf(sc_rate));
            table2.addCell(String.valueOf(df.format(sc_rate
                    *sc)));
            suma+=sc_rate
                    *sc;

            table2.addCell("Ciepla woda");
            table2.addCell(String.valueOf(cw));
            table2.addCell(String.valueOf(cw_rate));
            table2.addCell(String.valueOf(df.format(cw_rate
                    *cw)));
            suma+=cw_rate
                    *cw;

            table2.addCell("Zimna zimna");
            table2.addCell(String.valueOf(zw));
            table2.addCell(String.valueOf(zw_rate));
            table2.addCell(String.valueOf(df.format(zw_rate
                    *zw)));
            suma+=zw_rate
                    *zw;

            table2.addCell("Suma");
            table2.addCell("");
            table2.addCell("");
            table2.addCell(String.valueOf(df.format(suma)));

            table2.addCell(" ");
            table2.addCell(" ");
            table2.addCell(" ");
            table2.addCell(" ");

            table2.addCell(" ");
            table2.addCell(" ");
            table2.addCell(" ");
            table2.addCell("Locators");


            table2.addCell("Copy for");
            table2.addCell("");
            table2.addCell("");
            table2.addCell(usr_names);


            for (String tempUser : otherLocators) {

                table2.addCell("");

                table2.addCell("");
                table2.addCell("");
                table2.addCell(tempUser);
            }



            pdf.add(table2);






            pdf.close();
            o.close();

        }catch (IOException | com.itextpdf.text.DocumentException e){
            e.printStackTrace();
        }
    }

}