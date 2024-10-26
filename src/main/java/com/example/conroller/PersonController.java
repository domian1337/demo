package com.example.conroller;

import com.example.model.Person;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String getPersons(Model model) {
        model.addAttribute("persons", personService.getPersons());
        return "users";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("person", new Person());
        return "add";
    }

    @PostMapping("/add")
    public String addPerson(@ModelAttribute("person") Person person) {
        personService.addNewPerson(person);
        return "redirect:/people";
    }

    @GetMapping( "/delete")
    public String deletePerson(@RequestParam("id") Long id) {
        personService.deletePerson(id);
        return "redirect:/people";
    }

    @GetMapping("/edit")
    public String editPage(Model model, @RequestParam Long id) {
        model.addAttribute("person", personService.getPerson(id));
        return "edit";
    }

    @PostMapping( "/edit")
    public String updatePerson(@ModelAttribute("person") Person person) {
        personService.updatePerson(
                person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getAge());
        return "redirect:/people";
    }
}
