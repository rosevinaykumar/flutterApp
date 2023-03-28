package com.duke.todo.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public List<TicketItem> getTickets() {
        return ticketRepository.findAll();
    }

    @PostMapping("/add")
    public TicketItem addTicket(@RequestBody TicketItem ticketItem) {
        return ticketRepository.save(ticketItem);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateTicket(@PathVariable Long id) {
        boolean taskExist =  ticketRepository.existsById(id);
        if(taskExist) {
            TicketItem ticket = ticketRepository.getById(id);
            boolean done = ticket.isDone();
            ticket.setDone(!done);
            ticketRepository.save(ticket);
            return new ResponseEntity<>("Ticket is updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Ticket is not exist", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTicket(@PathVariable Long id) {
        boolean taskExist =  ticketRepository.existsById(id);
        if(taskExist) {
            ticketRepository.deleteById(id);
            return new ResponseEntity("Ticket is deleted", HttpStatus.OK);
        }
        return new ResponseEntity("Ticket is not exist", HttpStatus.BAD_REQUEST);
    }

}
