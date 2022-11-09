package webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import webapp.entity.Stationery;
import webapp.entity.User;
import webapp.spring.StationeryDAO;
import webapp.spring.UserDAO;

@Controller
public class BasicController {
    private final StationeryDAO stationeryDAO;
    private final UserDAO userDAO;

    @Autowired
    public BasicController(StationeryDAO stationeryDAO, UserDAO userDAO){
        this.stationeryDAO = stationeryDAO;
//        stationeryDAO.checkTableCreated();
//        stationeryDAO.insertExampleData();
        this.userDAO = userDAO;
//        userDAO.checkTableCreated();
//        userDAO.insertBasicUsers();
    }

    @RequestMapping("/")
    public String showHomePage(HttpServletRequest httpServletRequest, Model model) {
        String username = httpServletRequest.getUserPrincipal().getName();
        model.addAttribute("is_admin", httpServletRequest.isUserInRole(User.UserRole.ADMIN.toString()));
        model.addAttribute("username", username);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "users/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "users/registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/registration";
        } else if (userDAO.exist(user.getUsername())) {
            String message = "This username has taken. Try another username";
            model.addAttribute("message", message);
            return "users/registration";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setRole(User.UserRole.USER.toRole());
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.insert(user);
        return "redirect:/login";
    }

    @GetMapping("/stationery/all")
    public String allStationers(Model model){
        model.addAttribute("stationers", stationeryDAO.findAll());
        return "allStationers";
    }

    @GetMapping("/stationery/find")
    public String find(Model model){
        model.addAttribute("stationery", new Stationery());
        return "findById";
    }

    @PostMapping("/stationery/find")
    public String find(@ModelAttribute("stationery") Stationery stationery, Model model){
        Stationery foundStationery = stationeryDAO.findById(stationery.getId());
        if(foundStationery == null) {
            model.addAttribute("message", "This id does not exist in the database");
            return "findById";
        }
        model.addAttribute("stationery", foundStationery);
        model.addAttribute("title", "Found stationery object by %d id.".formatted(stationery.getId()));
        return "displaySingleStationery";
    }
}
