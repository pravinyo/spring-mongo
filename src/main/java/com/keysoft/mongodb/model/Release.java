package com.keysoft.mongodb.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Release {

    @Id
    private String id;
    private String name;
    private String description;

    @Field("release_tickets")
    private List<Ticket> tickets;
    private LocalDate releaseDate;

    @Transient //don't save in db
    private Double estimatedCosts;

    public Release() {

    }

    public Release(String name, String description, List<Ticket> tickets, LocalDate releaseDate) {
        this.name = name;
        this.description = description;
        this.tickets = tickets;
        this.releaseDate = releaseDate;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Double getEstimatedCosts() {
        return tickets.size() * 15.2; //dollars
    }

    public void setEstimatedCosts(Double estimatedCosts) {
        this.estimatedCosts = estimatedCosts;
    }
}
