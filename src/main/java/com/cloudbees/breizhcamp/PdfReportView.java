package com.cloudbees.breizhcamp;

import com.lowagie.text.Document;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringReader;
import java.util.Map;


public class PdfReportView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter, HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) throws Exception {
        String data = (String) model.get("data");

        HTMLWorker htmlWorker = new HTMLWorker(document);

        htmlWorker.parse(new StringReader(data));
    }
}
