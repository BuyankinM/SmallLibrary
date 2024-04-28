package ru.buyankin.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.buyankin.spring.dao.BookDao;
import ru.buyankin.spring.dao.ReaderDao;
import ru.buyankin.spring.models.Book;
import ru.buyankin.spring.models.Reader;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDao bookDao;
    private final ReaderDao readerDao;

    @Autowired
    public BooksController(BookDao bookDao, ReaderDao readerDao) {
        this.bookDao = bookDao;
        this.readerDao = readerDao;
    }

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id,
                           @ModelAttribute("reader") Reader reader,
                           Model model) {
        model.addAttribute("book", bookDao.getBook(id));
        model.addAttribute("readerFio", bookDao.getReaderFIO(id));
        model.addAttribute("readers", readerDao.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDao.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String patch(@ModelAttribute("book") @Valid Book book,
                        BindingResult bindingResult,
                        @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "books/edit";

        bookDao.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/free/{id}")
    public String free(@PathVariable("id") int id) {
        bookDao.freeBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/assign/{id}")
    public String assign(@PathVariable("id") int id,
                       @ModelAttribute("reader") Reader reader) {
        bookDao.assignBook(id, reader.getId());
        return "redirect:/books/" + id;
    }
}