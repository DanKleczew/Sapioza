package fr.pantheonsorbonne.testpdfs;

import fr.pantheonsorbonne.dto.PaperDTOs.CompletePaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.PaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.SubmittedPaperDTO;
import fr.pantheonsorbonne.enums.ResearchField;
import fr.pantheonsorbonne.global.UserInfoDTO;
import fr.pantheonsorbonne.pdf.PdfGenerator;
import fr.pantheonsorbonne.service.PdfGenerationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.sql.Date;
import java.util.List;

@Path("/pdf")
public class PdfResourceExample {

    @Inject
    PdfGenerator pdfGenerator;

    @GET
    @Path("/generate")
    public Response getPdf() {
        // Generate PDF
        CompletePaperDTO completePaperDTO = new CompletePaperDTO(
                new PaperDTO("Optimizing Inventory Management with Seasonal Demand Forecasting in a Fuzzy Environment",
                        12345678L, ResearchField.COMPUTER_SCIENCE,
                        "Journal Européen des Systèmes Automatisés",
                        new Date(2024-8-27),
                        "supply model, shortages, forecasting demand, artificial intelligence, machine learning, deterioration, carbon pollution policy, finite planning horizon",
                        "This study explores an inventory management model in today's business landscape, where " +
                        "organizations increasingly rely on Machine Learning for demand-driven stock control. The " +
                        "proposed model accounts for imperfect and deteriorating products within a fuzzy " +
                        "environment, allowing for shortages and partial backlogging. Degradation rates and faulty " +
                        "percentages are classified as fuzzy variables since they are unpredictable and impacted by " +
                        "undefined conditions. The goal is to calculate the appropriate replenishment cycle and " +
                        "ordering quantity while reducing the optimal overall cost, including carbon pollution costs, " +
                        "within a constrained planning horizon. The defuzzification technique uses the sign distance " +
                        "approximation technique. Leveraging Machine Learning, the study utilizes a seasonal " +
                        "demand forecasting methodology. A numerical illustration supports the mathematical " +
                        "approach by demonstrating its capacity to estimate demand for deteriorating products. This " +
                        "facilitates optimized inventory management aligned with forecasted demand. A " +
                        "comparative examination emphasizes the positive aspects of AI learning-based forecasting " +
                        "systems over determined demand circumstances. Sensitivity analysis provides insights " +
                        "into the impact of various parameters on optimal solutions, contributing valuable managerial perspectives.",
                        "https://doi.org/10.18280/jesa.570416",
                        36,
                        2,
                        List.of()),
                new UserInfoDTO(12345678L, "Renuka", "Namwad", "renukanamwad@gmail.com"),
                "In the ever-evolving global market landscape, the intricate " +
                        "dance between seasonal and weather conditions exerts a " +
                        "profound influence on consumer demand, a cornerstone " +
                        "variable that presents multifaceted challenges to efficient " +
                        "inventory management across diverse industries [1]. The ebb " +
                        "and flow of seasonal demand, shaped by events such as " +
                        "festivals and climatic factors, introduces uncertainties and " +
                        "complexities into consumer purchasing behaviours, " +
                        "necessitating a sophisticated approach to inventory control. " +
                        "While conventional inventory models often hinge on " +
                        "deterministic demand assumptions, the real-world scenario " +
                        "unfolds with variations in product demand that adhere to " +
                        "distinct seasonal patterns. So, in our study, we have applied " +
                        "the time series algorithm to forecast seasonal demand. " +
                        "The strategic imperative of effective demand prediction " +
                        "emerges as a key solution, offering the potential to refine " +
                        "inventory management strategies, curtail superfluous costs, " +
                        "and elevate overall customer service [3]. Leveraging machine " +
                        "learning (ML): with its advanced predictive capabilities, " +
                        "particularly through Decision Tree-based Algorithms, stands " +
                        "out as a transformative tool in achieving precise and accurate " +
                        "seasonal demand forecasts [4]. This paper delves into the " +
                        "convergence of seasonal demand dynamics, imperfect " +
                        "deteriorating products, and the contemporary imperative of " +
                        "considering carbon emissions in inventory systems. The " +
                        "intrinsic deterioration of physical products over time, be it " +
                        "during transit or storage, is a ubiquitous challenge across " +
                        "various industries [5]. Items such as fruits, medicines, flowers, " +
                        "foodstuffs, and vegetables are susceptible to decay during their " +
                        "holding and in-transit periods. This study acknowledges the " +
                        "deterministic approach traditionally applied to deterioration " +
                        "rates in inventory models but contends that real-world " +
                        "uncertainty demands a more sophisticated treatment [6]. To " +
                        "address this, the model introduces a fuzzy variable for " +
                        "deterioration rates, acknowledging the uncertainty in their " +
                        "precise estimation.");
        byte[] pdf = this.pdfGenerator.generatePdf(completePaperDTO);

        return Response.ok(pdf).header("Content-Disposition", "inline; filename=\"generated.pdf\"").build();
    }
}
