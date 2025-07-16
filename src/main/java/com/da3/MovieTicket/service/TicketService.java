package com.da3.MovieTicket.service;

import com.da3.MovieTicket.entity.TicketEntity;
import com.da3.MovieTicket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    public List<TicketEntity> getAllTickets (){
        return ticketRepository.findAll();
    }

    public void createTicket(TicketEntity ticket){
        ticketRepository.save(ticket);
    }

    public TicketEntity getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id: " + id));
    }
}
