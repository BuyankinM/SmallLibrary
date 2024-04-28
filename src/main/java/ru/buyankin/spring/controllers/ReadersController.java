package ru.buyankin.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.buyankin.spring.dao.ReaderDao;
import ru.buyankin.spring.models.Reader;
import ru.buyankin.spring.util.ReaderValidator;

@Controller
@RequestMapping("/readers")
public class ReadersController {
    private final ReaderDao readerDao;
    private final ReaderValidator readerValidator;

    @Autowired
    public ReadersController(ReaderDao readerDao, ReaderValidator readerValidator) {
        this.readerDao = readerDao;
        this.readerValidator = readerValidator;
    }

    @GetMapping()
    public String readers(Model model) {
        model.addAttribute("readers", readerDao.index());
        return "readers/index";
    }

    @GetMapping("/{id}")
    public String reader(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerDao.getReader(id));
        model.addAttribute("books", readerDao.getBooksOfReader(id));
        return "readers/show";
    }

    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader") Reader reader) {
        return "readers/new";
    }

    @PostMapping()
    public String createReader(@ModelAttribute("reader") @Valid Reader reader,
                               BindingResult bindingResult) {

        readerValidator.validate(reader, bindingResult);
        if (bindingResult.hasErrors())
            return "readers/new";

        readerDao.save(reader);
        return "redirect:/readers";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerDao.getReader(id));
        return "readers/edit";
    }

    @PatchMapping("/{id}")
    public String patch(@ModelAttribute("reader") @Valid Reader reader,
                        BindingResult bindingResult,
                        @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "readers/edit";

        readerDao.update(id, reader);
        return "redirect:/readers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        readerDao.delete(id);
        return "redirect:/readers";
    }
}
