package com.example.book.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.book.Model.Book;
import com.example.book.Services.BookService;
import com.example.book.Services.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("title", "Book List");
        return "book/list";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("title", "Add Book");
        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("book", book);
            model.addAttribute("title", "Add Book");
            return "book/add";
        }

        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("title", "Edit Book");
        model.addAttribute("categories", categoryService.getAllCategories());
        Book editBook = bookService.getBookById(id);
        if (editBook != null) {
            model.addAttribute("book", editBook);
            return "book/edit";

        } else {
            return "not-found";
        }
    }

    @PostMapping("/edit/{id}")
    public String editBook( @PathVariable("id") Long id,@Valid @ModelAttribute("book") Book book,BindingResult bindingResult,Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("book", book);
            model.addAttribute("title", "Edit Book");
            return "book/edit";
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
