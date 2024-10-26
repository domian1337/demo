package com.example.service;

import com.example.dao.UserDao;
import com.example.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class PersonService {

    private final UserDao userDao;

    @Autowired
    public PersonService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<Person> getPersons() {
        return userDao.findAll();
    }

    public Person getPerson(Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("пользователя с id" + id + "не существует"));
    }

    public void addNewPerson(Person person) {
        Optional<Person> personOptional = userDao.findByEmail(person.getEmail());
        if (personOptional.isPresent()) {
            throw new IllegalArgumentException("Пользователь с этой почтой уже существует");
        }
        userDao.save(person);
    }

    @Transactional
    public void updatePerson(Long id, String firstName, String lastName, String email, int age) {
        Person person = userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("пользователя с id" + id + "не существует"));
        if (firstName != null && !firstName.isEmpty() && !Objects.equals(firstName, person.getFirstName())) {
            person.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isEmpty() && !Objects.equals(lastName, person.getLastName())) {
            person.setLastName(lastName);
        }
        if (email != null && !email.isEmpty() && !Objects.equals(email, person.getEmail())) {
            Optional<Person> personOptional = userDao.findByEmail(email);
            if (personOptional.isPresent()) {
                throw new IllegalArgumentException("Пользователь с этой почтой уже существует");
            }
            person.setEmail(email);
        }
        if (age != person.getAge() && age != 0) {
            person.setAge(age);
        }
    }

    public void deletePerson(Long personId) {
        boolean exists = userDao.existsById(personId);
        if (!exists) {
            throw new IllegalArgumentException("Пользователь с id" + personId + "не существует");
        }
        userDao.deleteById(personId);
    }
}