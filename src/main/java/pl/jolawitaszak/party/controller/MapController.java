package pl.jolawitaszak.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jolawitaszak.party.external.api.tomtom.TomTomConfig;

@Controller
@RequestMapping("/v1")
@RequiredArgsConstructor
public class MapController {

    private final TomTomConfig tomTomConfig;

    @GetMapping("/map")
    public String homePage(Model model) {
        model.addAttribute("apiKey", tomTomConfig.getTomtomAppKey());
        return "map";
    }
}
