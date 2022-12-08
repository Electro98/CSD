package webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import webapp.JMS.Producer;
import webapp.entity.Stationery;
import webapp.spring.StationeryDAO;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v0.1")
public class APIController {
    private final StationeryDAO stationeryDAO;
    private final Producer producer;

    @Autowired
    public APIController(StationeryDAO stationeryDAO, Producer producer) {
        this.stationeryDAO = stationeryDAO;
        this.producer = producer;
    }

    // GET STATIONERY/STATIONERS
    @GetMapping("/stationers")
    public List<Stationery> allStationers() {
        return stationeryDAO.findAll();
    }

    @GetMapping("/stationers/{id}")
    public ResponseEntity<Stationery> getStationeryById(@PathVariable int id) {
        Stationery stationery = stationeryDAO.findById(id);
        if (stationery == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(stationery);
    }

    @GetMapping("/stationers/new/{id}")
    public Stationery getStationeryByIdNew(@PathVariable int id) {
        return stationeryDAO.findById(id);
    }

    // ADD STATIONERY
    @PostMapping("stationers")
    public ResponseEntity<Stationery> addNewStationery(@Valid @RequestBody Stationery stationery, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().build();
        if (stationeryDAO.insert(stationery) == 0)
            return ResponseEntity.badRequest().build();
        producer.send("Stationery was created!");
        return ResponseEntity.ok(stationeryDAO.findLastInserted());
    }

    // DELETE STATIONERY
    @DeleteMapping("/stationers/{id}")
    public ResponseEntity<Stationery> removeStationery(@PathVariable int id) {
        Stationery stationery = stationeryDAO.findById(id);
        if (stationery == null)
            return ResponseEntity.notFound().build();
        if (stationeryDAO.delete(id) == 0)
            return ResponseEntity.internalServerError().build();
        producer.send("Stationery was removed!");
        return ResponseEntity.ok(stationery);
    }

    // UPDATE STATIONERY
    @PutMapping("stationers/{id}")
    public ResponseEntity<Stationery> updateStationery(@PathVariable int id, @Valid @RequestBody Stationery stationery, BindingResult result) {
        Stationery oldStationery = stationeryDAO.findById(id);
        if (oldStationery == null)
            return ResponseEntity.notFound().build();
        if (stationeryDAO.update(stationery) == 0)
            return ResponseEntity.internalServerError().build();
        producer.send("Stationery was updated!");
        return ResponseEntity.ok(stationery);
    }

    @PutMapping("stationers")
    public ResponseEntity<Stationery> updateStationery(@Valid @RequestBody Stationery stationery, BindingResult result) {
        Stationery oldStationery = stationeryDAO.findById(stationery.getId());
        if (oldStationery == null)
            return ResponseEntity.notFound().build();
        if (stationeryDAO.update(stationery) == 0)
            return ResponseEntity.internalServerError().build();
        producer.send("Stationery was updated!");
        return ResponseEntity.ok(stationery);
    }
}
