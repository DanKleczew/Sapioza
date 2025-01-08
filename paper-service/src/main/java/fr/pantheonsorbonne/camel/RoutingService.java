package fr.pantheonsorbonne.camel;

import fr.pantheonsorbonne.global.GlobalRoutes;
import jakarta.inject.Singleton;

@Singleton
public class RoutingService {

    public String getLocalRoute(Routes r){
        return r.getRoute();
    }

    public String getGlobalRoute(GlobalRoutes gr){
        return gr.getRoute();
    }
}