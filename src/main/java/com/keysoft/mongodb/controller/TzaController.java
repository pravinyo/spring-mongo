package com.keysoft.mongodb.controller;

import com.keysoft.mongodb.model.Application;
import com.keysoft.mongodb.model.Release;
import com.keysoft.mongodb.model.Ticket;
import com.keysoft.mongodb.repositories.ApplicationRepository;
import com.keysoft.mongodb.repositories.DBService;
import com.keysoft.mongodb.repositories.ReleaseRepository;
import com.keysoft.mongodb.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/tza")
public class TzaController {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private DBService dbService;

    // ************** Methods for Applications *************************
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.GET)
    public Optional<Application> getApplicationById(@PathVariable("id") String id) {
        return applicationRepository.findById(id);
    }

    @RequestMapping(value = "/applications", method = RequestMethod.POST)
    public Application addNewApplication(@RequestBody Application application){
        return applicationRepository.save(application);
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.PUT)
    public Application updateApplication(@PathVariable("id") String id, @RequestBody Application application){
        application.setId(id);
        return applicationRepository.save(application);
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.DELETE)
    public void deleteApplication(@PathVariable("id") String id) {
        applicationRepository.deleteById(id);
    }

    @RequestMapping(value = "/applications/name/{name}", method = RequestMethod.GET)
    public List<Application> findByName(@PathVariable("name") String name) {
        return applicationRepository.findByName(name);
    }


    @RequestMapping(value = "/applications/template", method = RequestMethod.PUT)
    public void updateApplicationWithCriteria(@RequestBody Application application) {
        dbService.updateApplicationWithCriteria(application);
    }

    // ************** Methods for Tickets *************************
    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.GET)
    public Optional<Ticket> getTicketById(@PathVariable("id") String id) {
        return ticketRepository.findById(id);
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.POST)
    public Ticket addNewApplication(@RequestBody Ticket ticket){
        return ticketRepository.save(ticket);
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.PUT)
    public Ticket updateApplication(@PathVariable("id") String id, @RequestBody Ticket ticket){
        ticket.setId(id);
        return ticketRepository.save(ticket);
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.DELETE)
    public void deleteTicket(@PathVariable("id") String id) {
        ticketRepository.deleteById(id);
    }

    @GetMapping(value = "/tickets/status/{status}")
    public List<Ticket> findByStatus(@PathVariable("status") String status){
        return ticketRepository.findByStatus(status);
    }

    @GetMapping(value = "/tickets/count")
    public Long countAllTickets(){
        Stream<Ticket> stream =  ticketRepository.findAllCustomerQueryAndStream("Open");
        Long count = stream.count();
        stream.close();
        return count;
    }

    @GetMapping(value = "/tickets/appId/{appId}")
    public List<Ticket> findTicketsByApplicationId(@PathVariable String appId){
        return ticketRepository.findByAppId(appId);
    }

    // ************** Methods for Releases *************************
    @RequestMapping(value = "/releases", method = RequestMethod.GET)
    public List<Release> getAllReleases() {
        return releaseRepository.findAll();
    }

    @RequestMapping(value = "/releases/{id}", method = RequestMethod.GET)
    public Optional<Release> getReleaseId(@PathVariable("id") String id) {
        return releaseRepository.findById(id);
    }

    @RequestMapping(value = "/releases", method = RequestMethod.POST)
    public Release addNewRelease(@RequestBody Release release){
        return releaseRepository.save(release);
    }

    @PutMapping(value = "/releases/tickets")
    public Release addNewReleaseWithTicket(@RequestBody Release release){
        return dbService.insertReleaseWithTicket(release);
    }

    @RequestMapping(value = "/releases/{id}", method = RequestMethod.PUT)
    public Release updateRelease(@PathVariable("id") String id, @RequestBody Release release){
        release.setId(id);
        return releaseRepository.save(release);
    }

    @RequestMapping(value = "/releases/{id}", method = RequestMethod.DELETE)
    public void deleteRelease(@PathVariable("id") String id) {
        releaseRepository.deleteById(id);
    }

    @RequestMapping(value = "/releases/status/{status}", method = RequestMethod.GET)
    public List<Release> getReleaseByTicketStatus(@PathVariable String status) {
        return dbService.getReleaseByTicketStatus(status);
    }

    @RequestMapping(value = "/releases/costs/{id}", method = RequestMethod.GET)
    public Double getReleaseCost(@PathVariable String id) {
        return dbService.getReleaseCost(id);
    }

}
