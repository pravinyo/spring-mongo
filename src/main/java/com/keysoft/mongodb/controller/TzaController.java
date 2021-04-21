package com.keysoft.mongodb.controller;

import com.keysoft.mongodb.model.Application;
import com.keysoft.mongodb.model.Release;
import com.keysoft.mongodb.model.Ticket;
import com.keysoft.mongodb.repositories.ApplicationRepository;
import com.keysoft.mongodb.repositories.DBService;
import com.keysoft.mongodb.repositories.ReleaseRepository;
import com.keysoft.mongodb.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/tza")
public class TzaController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private DBService dbService;

    // ************** Methods for Applications *************************
    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public List<Application> getAllApplications() {
        return dbService.getApplicationRepository().findAll();
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.GET)
    public Optional<Application> getApplicationById(@PathVariable("id") String id) {
        return dbService.getApplicationRepository().findById(id);
    }

    @RequestMapping(value = "/applications", method = RequestMethod.POST)
    public Application addNewApplication(@RequestBody Application application){
        return dbService.getApplicationRepository().save(application);
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.PUT)
    public Application updateApplication(@PathVariable("id") String id, @RequestBody Application application){
        application.setId(id);
        return dbService.getApplicationRepository().save(application);
    }

    @RequestMapping(value = "/applications/{id}", method = RequestMethod.DELETE)
    public void deleteApplication(@PathVariable("id") String id) {
        dbService.getApplicationRepository().deleteById(id);
    }

    @RequestMapping(value = "/applications/name/{name}", method = RequestMethod.GET)
    public List<Application> findByName(@PathVariable("name") String name) {
        return dbService.getApplicationRepository().findByName(name);
    }


    @RequestMapping(value = "/applications/template", method = RequestMethod.PUT)
    public void updateApplicationWithCriteria(@RequestBody Application application) {
        dbService.updateApplicationWithCriteria(application);
    }

    // ************** Methods for Tickets *************************
    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public List<Ticket> getAllTickets() {
        return dbService.getTicketRepository().findAll();
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.GET)
    public Optional<Ticket> getTicketById(@PathVariable("id") String id) {
        return dbService.getTicketRepository().findById(id);
    }

    @RequestMapping(value = "/tickets", method = RequestMethod.POST)
    public Ticket addNewApplication(@RequestBody Ticket ticket){
        return dbService.getTicketRepository().save(ticket);
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.PUT)
    public Ticket updateApplication(@PathVariable("id") String id, @RequestBody Ticket ticket){
        ticket.setId(id);
        return dbService.getTicketRepository().save(ticket);
    }

    @RequestMapping(value = "/tickets/{id}", method = RequestMethod.DELETE)
    public void deleteTicket(@PathVariable("id") String id) {
        dbService.getTicketRepository().deleteById(id);
    }

    @GetMapping(value = "/tickets/status/{status}")
    public List<Ticket> findByStatus(@PathVariable("status") String status){
        return dbService.getTicketRepository().findByStatus(status);
    }

    @GetMapping(value = "/tickets/count")
    public Long countAllTickets(){
        Stream<Ticket> stream =  dbService.getTicketRepository().findAllCustomerQueryAndStream("Open");
        Long count = stream.count();
        stream.close();
        return count;
    }

    @GetMapping(value = "/tickets/appId/{appId}")
    public List<Ticket> findTicketsByApplicationId(@PathVariable String appId){
        return dbService.getTicketRepository().findByAppId(appId);
    }

    @GetMapping(value = "/tickets/search/{text}/page/{page}")
    public List<Ticket> searchTicketsFor(@PathVariable String text, @PathVariable Integer page){
        return dbService.searchInTickets(text, page);
    }

    // ************** Methods for Releases *************************
    @RequestMapping(value = "/releases", method = RequestMethod.GET)
    public List<Release> getAllReleases() {
        return dbService.getReleaseRepository().findAll();
    }

    @RequestMapping(value = "/releases/{orderBy}/{pageSize}/{pageNb}", method = RequestMethod.GET)
    public List<Release> getAllReleases(@PathVariable String orderBy,
                                        @PathVariable int pageSize,
                                        @PathVariable int pageNb) {
        return dbService.findAllReleases(orderBy, pageNb, pageSize);
    }

    @RequestMapping(value = "/releases/{id}", method = RequestMethod.GET)
    public Optional<Release> getReleaseId(@PathVariable("id") String id) {
        return dbService.getReleaseRepository().findById(id);
    }

    @RequestMapping(value = "/releases", method = RequestMethod.POST)
    public Release addNewRelease(@RequestBody Release release){
        return dbService.getReleaseRepository().save(release);
    }

    @PutMapping(value = "/releases/tickets")
    public Release addNewReleaseWithTicket(@RequestBody Release release){
        return dbService.insertReleaseWithTicket(release);
    }

    @RequestMapping(value = "/releases/{id}", method = RequestMethod.PUT)
    public Release updateRelease(@PathVariable("id") String id, @RequestBody Release release){
        release.setId(id);
        return dbService.getReleaseRepository().save(release);
    }

    @RequestMapping(value = "/releases/{id}", method = RequestMethod.DELETE)
    public void deleteRelease(@PathVariable("id") String id) {
        dbService.getReleaseRepository().deleteById(id);
    }

    @RequestMapping(value = "/releases/status/{status}", method = RequestMethod.GET)
    public List<Release> getReleaseByTicketStatus(@PathVariable String status) {
        return dbService.getReleaseByTicketStatus(status);
    }

    @RequestMapping(value = "/releases/costs/{id}", method = RequestMethod.GET)
    public Double getReleaseCost(@PathVariable String id) {
        return dbService.getReleaseCost(id);
    }

    @GetMapping(value = "/releases/tickets/releaseDateGt/{date}/page/{pageNum}")
    public List<Ticket> findTicketsForReleaseHavingDateGt(
            @PathVariable String date,
            @PathVariable Integer pageNum){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneOffset.UTC);
        LocalDate dt = LocalDate.parse(date,formatter);
        ZonedDateTime zdt = dt.atStartOfDay(ZoneOffset.UTC);
        logger.debug("Time: {}",zdt);

        return dbService.getTicketsWithReleaseDateGt(zdt, pageNum);
    }

}
