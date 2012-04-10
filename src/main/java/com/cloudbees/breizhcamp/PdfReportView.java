package com.cloudbees.breizhcamp;

import com.cloudbees.breizhcamp.controllers.Data;
import com.cloudbees.breizhcamp.domain.Room;
import com.cloudbees.breizhcamp.domain.Speaker;
import com.cloudbees.breizhcamp.domain.Talk;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class PdfReportView extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        Data data = (Data) model.get("data");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String sansRoom = "sansRoom";


        for (Date date : data.getDatesOrdonnees()) {
            document.add(new Paragraph("Programme du " + sdf.format(date)));

            Table table = new Table(data.getRooms().size() + 1);
            table.addCell("Heure");
            for (Room room : data.getRooms()) {
                table.addCell(room.getName());
            }
            for (String creneau : data.getCreneaux().get(date)) {
                table.addCell(creneau);
                if (data.getTalks().get(date).get(creneau).containsKey(sansRoom)) {
                    Cell cell = new Cell();
                    cell.setColspan(data.getRooms().size());
                    Talk talk = data.getTalks().get(date).get(creneau).get(sansRoom);
                    cell.add(new Paragraph(talk.getTitle()));
                    cell.add(new Paragraph(talk.getTheme().getHtmlValue()));
                    for (Speaker speaker : talk.getSpeakers()) {
                        if (speaker.getPicture() != null) {
                            Image image = Image.getInstance(speaker.getPicture());
                            image.scaleAbsolute(30, 30);
                            cell.add(image);
                        }
                    }
                    table.addCell(cell);
                } else {
                    for (Room room : data.getRooms()) {
                        Cell cell = new Cell();

                        Talk talk = data.getTalks().get(date).get(creneau).get(room.getName());
                        if (talk != null) {
                            cell.add(new Paragraph(talk.getTitle()));
                            cell.add(new Paragraph(talk.getTheme().getHtmlValue()));
                            for (Speaker speaker : talk.getSpeakers()) {
                                if (speaker.getPicture() != null) {
                                    Image image = Image.getInstance(speaker.getPicture());
                                    image.scaleAbsolute(30, 30);
                                    cell.add(image);
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
