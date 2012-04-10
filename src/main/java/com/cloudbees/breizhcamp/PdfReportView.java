package com.cloudbees.breizhcamp;

import com.cloudbees.breizhcamp.controllers.Data;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class PdfReportView extends AbstractPdfView {
    
    private PdfPCell createHeaderCell(String content) throws BadElementException {
        Paragraph paragraph = new Paragraph();
        Font font = new Font();
        font.setColor(Color.WHITE);
        paragraph.setFont(font);
        paragraph.add(new Phrase(content));
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setPaddingBottom(10);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBackgroundColor(Color.GRAY);
        return cell;
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        Data data = (Data) model.get("data");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String sansRoom = "sansRoom";

        document.setPageSize(PageSize.A4.rotate());

        for (Date date : data.getDatesOrdonnees()) {
            Paragraph titre = new Paragraph();
            Font font = new Font();
            font.setStyle(Font.BOLD);
            font.setSize(14);
            titre.setFont(font);
            titre.setAlignment(Paragraph.ALIGN_CENTER);
            titre.add(new Phrase("Programme du " + sdf.format(date)));
            document.add(titre);

            PdfPTable table = new PdfPTable(data.getRooms().size() + 1);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(20);

            table.addCell(createHeaderCell("Heure"));
            for (Room room : data.getRooms()) {
                table.addCell(createHeaderCell(room.getName()));
            }
            for (String creneau : data.getCreneaux().get(date)) {
                table.addCell(creneau);
                if (data.getTalks().get(date).get(creneau).containsKey(sansRoom)) {
                    PdfPCell cell = new PdfPCell();
                    cell.setColspan(data.getRooms().size());
                    Talk talk = data.getTalks().get(date).get(creneau).get(sansRoom);
                    cell.addElement(new Paragraph(talk.getTitle()));
                    cell.addElement(new Paragraph(talk.getTheme().getHtmlValue()));
                    for (Speaker speaker : talk.getSpeakers()) {
                        cell.addElement(new Paragraph(speaker.getFirstName() + " " + speaker.getLastName()));
                    }
                    table.addCell(cell);
                } else {
                    for (Room room : data.getRooms()) {
                        PdfPCell cell = new PdfPCell();

                        Talk talk = data.getTalks().get(date).get(creneau).get(room.getName());
                        if (talk != null) {
                            cell.addElement(new Paragraph(talk.getTitle()));
                            cell.addElement(new Paragraph(talk.getTheme().getHtmlValue()));
                            for (Speaker speaker : talk.getSpeakers()) {
                                if (speaker.getPicture() != null) {
                                    cell.addElement(new Paragraph(speaker.getFirstName() + " " + speaker.getLastName
                                            ()));
                                }
                            }
                        }

                        table.addCell(cell);
                    }
                }
            }
            document.add(table);
        }
    }
}
