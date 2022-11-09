package webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webapp.entity.Stationery;
import webapp.spring.StationeryDAO;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final StationeryDAO stationeryDAO;

    @Autowired
    public AdminController(StationeryDAO stationeryDAO){
        this.stationeryDAO = stationeryDAO;
    }

    @GetMapping("/stationery/new")
    public String add(Model model){
        model.addAttribute("stationery", new Stationery());
        return "addStationery";
    }

    @PostMapping("/stationery/new")
    public String add(@Valid @ModelAttribute("stationery") Stationery stationery, BindingResult result){
        if(result.hasErrors()){
            return "addStationery";
        }
        stationeryDAO.insert(stationery);
        return "redirect:/";
    }

    @GetMapping("/stationery/edit")
    public String update(Model model){
        model.addAttribute("stationery", new Stationery());
        model.addAttribute("search", true);
        return "editById";
    }

    @PostMapping("/stationery/edit")
    public String update_search(@ModelAttribute("stationery") Stationery stationery, Model model){
        Stationery foundStationery = stationeryDAO.findById(stationery.getId());
        if (foundStationery == null) {
            model.addAttribute("stationery", new Stationery());
            model.addAttribute("message", "This id does not exist in the database");
            model.addAttribute("search", true);
            return "editById";
        }
        model.addAttribute("stationery", foundStationery);
        model.addAttribute("search", false);
        return "editById";
    }

    @PostMapping("/stationery/edit_final")
    @Validated
    public String update(@Valid @ModelAttribute("stationery") Stationery stationery, BindingResult result, Model model){
        Stationery foundStationery = stationeryDAO.findById(stationery.getId());

        if (foundStationery == null) {
            // Should be impossible for normies
            model.addAttribute("message", "This id does not exist in the database");
            model.addAttribute("search", true);
            return "editById";
        }
        if (result.hasErrors()) {
            model.addAttribute("stationery", stationery);
            model.addAttribute("search", false);
            return "editById";
        }
        stationeryDAO.update(stationery);
        return "redirect:/";
    }

    @GetMapping("/stationery/delete")
    public String delete(Model model){
        model.addAttribute("stationery", new Stationery());
        return "deleteById";
    }

    @PostMapping("/stationery/delete")
    public String delete(@ModelAttribute("stationery") Stationery stationery, Model model){
        Stationery foundStationery = stationeryDAO.findById(stationery.getId());
        if(foundStationery == null) {
            model.addAttribute("message", "This id does not exist in the database");
            return "deleteById";
        }
        stationeryDAO.delete(stationery.getId());
        return "redirect:/";
    }
}
