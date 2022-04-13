package pl.skrys.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PdfControl {

    private PdfService pdfService;

    @Autowired
    public PdfControl(PdfService pdfService) {
        this.pdfService = pdfService;
    }


    @GetMapping(value = "/rachunek")
    public void generateRachunekPdf(@RequestParam int year,@RequestParam int month, @RequestParam boolean ryczalt,
                                    @RequestParam long flatid,@RequestParam long buildingid,
                                    @RequestParam String street, @RequestParam String bNr, @RequestParam String fNr, @RequestParam String postalcode, @RequestParam String city,
                                    @RequestParam double fr, @RequestParam double fr_rate,
                                    @RequestParam double g, @RequestParam double g_rate,
                                    @RequestParam double og, @RequestParam double og_rate,
                                    @RequestParam double pr, @RequestParam double pr_rate,
                                    @RequestParam double sc, @RequestParam double sc_rate,
                                    @RequestParam double cw, @RequestParam double cw_rate,
                                    @RequestParam double zw, @RequestParam double zw_rate,
                                    @RequestParam String usr_names, @RequestParam List<String> otherLocators,
                                    HttpServletResponse response){
        /*
        boolean ryczalt = false;
        if(r=='r'){
            System.out.println("RAchunek z ryczaltem");
            ryczalt = true;
        }*/
        //pdfService.generateRachunekPdf(flatCharge, user, ryczalt,  response);
        pdfService.generateRachunekPdf(year, month, ryczalt,
                flatid,  buildingid,
                street,  bNr,  fNr,  postalcode,  city,
                fr,  fr_rate,
                g,  g_rate,
                og,  og_rate,
                pr,  pr_rate,
                sc,  sc_rate,
                cw,  cw_rate,
                zw,  zw_rate,
                usr_names, otherLocators,
                response);


    }


}
