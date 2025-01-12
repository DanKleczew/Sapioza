package fr.pantheonsorbonne.camel;

import org.apache.camel.builder.RouteBuilder;

public class PdfGenerator extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from(Routes.GENERATE_PDF.getRoute())
                .to("pdf:create?pageSize=A4&font=Courier")
                .to("file:output?fileName=pdf");
    }
}
