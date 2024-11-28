package com.curso.service;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.curso.modelo.Inscricao;
import com.curso.util.DateUtils;
import com.curso.util.NegocioException;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

public class InscricaoPDFService implements Serializable  {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(InscricaoPDFService.class);
	
	
	
	public ByteArrayOutputStream generateStream(List<Inscricao> inscricoes) throws NegocioException {
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			PdfWriter writer = new PdfWriter(baos);
	        // Creating a PdfDocument  
	        PdfDocument pdf = new PdfDocument(writer);	    
	        pdf.setDefaultPageSize(PageSize.A4);
	        // Creating a Document   
	        Document document = new Document(pdf); 		        
	        
			document.setMargins(72, 50, 50, 50);

	        
	        // gera impressão da Denúncia
	        generateContent(pdf,document,inscricoes);
	        log.info("documento feito");
	        
	        return baos;
	        
		}catch (Exception ex) {
	    	log.error("error: " + ex.getMessage());	    	
	    	throw new NegocioException("Erro na montagem do PDF: " + ex.getMessage());
	    }
	}
	
	private void generateContent(PdfDocument pdfDocument, Document document, List<Inscricao> inscricoes) throws Exception, MalformedURLException {

		log.info("header feito");
		
		String logo = ("logoSalto.png");
		String logoSvsa = ("SVSA_Transparente.png");
		
		try {
			//logo Prefeitura
		    String logoSaltoPath = getClass().getClassLoader().getResource("imagens/"+ logo).toString();
		    //logo SVSA
		    String logosvsaPath = getClass().getClassLoader().getResource("imagens/"+ logoSvsa).toString();
		    pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, new HeaderEventHandler(logoSaltoPath,logosvsaPath));
		    
		    
		    
		} catch (NullPointerException e) {
		    System.err.println("Imagem não encontrada no caminho especificado.");
		    e.printStackTrace();
		} catch (Exception e) {
		    System.err.println("Erro ao carregar a imagem: " + e.getMessage());
		    e.printStackTrace();
		}
        
		Paragraph line = new Paragraph("Ocorrências\n");
        line.setFontSize(24);
        line.setTextAlignment(TextAlignment.CENTER);
        document.add(line);
      
        onTableRender(document, inscricoes);      
		document.close();
	}
	
	
	private static class HeaderEventHandler implements IEventHandler {

        private Image imageLogoSalto;
        private Image imageLogosvsa;

        public HeaderEventHandler(String logoSaltoPath, String logosvsaPath) throws MalformedURLException {

            ImageData imageLogoSalto = ImageDataFactory.create(logoSaltoPath);
            this.imageLogoSalto = new Image(imageLogoSalto);
            ImageData imageLogosvsa = ImageDataFactory.create(logosvsaPath);
            this.imageLogosvsa = new Image(imageLogosvsa);
        }

        @Override
        public void handleEvent(Event event) {
            // Acessando o evento como um PdfDocumentEvent
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            PdfDocument pdfDoc = docEvent.getDocument();
            Document document = new Document(pdfDoc);


            // Caminho para o logotipo
            try {
                this.imageLogoSalto.scaleToFit(100f,150f);
                this.imageLogoSalto.setFixedPosition(40, page.getPageSize().getTop() - 60);
                document.add(imageLogoSalto);

                this.imageLogosvsa.scaleToFit(100f,150f);
                this.imageLogosvsa.setFixedPosition(150, page.getPageSize().getTop() - 70);
                document.add(imageLogosvsa);

               Canvas canvas = new Canvas(new PdfCanvas(page), page.getPageSize()).add(imageLogoSalto);
               canvas.add(imageLogosvsa);

                PdfCanvas pdfCanvas = new PdfCanvas(page);
                pdfCanvas.moveTo(30, 774) 
                        .lineTo(565, 774)
                        .setLineWidth(1.5f) 
                        .stroke();
                canvas.close();
        
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
	
	public void onTableRender(Document document, List<Inscricao> inscricoes){
		
        log.info("criando tabela");
       for(Inscricao inscricao : inscricoes) {
    	   
    	   float[] columnWidths = new float[] {0.2f,0.2f,0.2f,0.2f};
           Table pTable = new Table(UnitValue.createPercentArray(columnWidths));
           pTable.setWidth(UnitValue.createPercentValue(100));
           
    	   Cell cellAtleta = new Cell(1, 4) 
                   .add(new Paragraph("Nome: " + inscricao.getAtleta().getNome()))
                   .setBackgroundColor(ColorConstants.LIGHT_GRAY) 
                   .setPadding(5f); 
           pTable.addCell(cellAtleta);
           
           Cell cellContato = new Cell(2, 2) 
                   .add(new Paragraph("Contato: " + inscricao.getContato()))
                   .setPadding(5f);
           pTable.addCell(cellContato);
           
           Cell cellTelefone = new Cell(2, 2) 
                   .add(new Paragraph("Telefone: " + inscricao.getTelefoneContato()))
                   .setPadding(5f);
           pTable.addCell(cellTelefone);
           
           pTable.addCell(new Cell(3,1).add(new Paragraph("Evento: " + inscricao.getEvento().getOrganizador())));
           pTable.addCell(new Cell(3,1).add(new Paragraph("Categoria: " + inscricao.getCategoria().getNome())));
           pTable.addCell(new Cell(3,1).add(new Paragraph("Status: " + inscricao.getStatus().name())));
           pTable.addCell(new Cell(3,1).add(new Paragraph("Data: " + inscricao.getDataInscricao().toString())));
           /*try {
               String dataFormatada = DateUtils.parseDateToString(inscricao.getDataInscricao());
               pTable.addCell(new Cell().add(new Paragraph("Data: " + dataFormatada)));
           } catch (ParseException e) {
               e.printStackTrace();
               pTable.addCell(new Cell().add(new Paragraph("Data: inválida")));
           }*/
       
           pTable.setMarginBottom(10f);
           pTable.setKeepTogether(true);
           document.add(pTable);
           
       }    
	}
}
