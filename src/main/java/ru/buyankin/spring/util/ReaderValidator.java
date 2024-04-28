package ru.buyankin.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.buyankin.spring.dao.ReaderDao;
import ru.buyankin.spring.models.Reader;

@Component
public class ReaderValidator implements Validator {

    private final ReaderDao readerDao;

    @Autowired
    public ReaderValidator(ReaderDao readerDao) {
        this.readerDao = readerDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Reader.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Reader reader = (Reader) target;

        // Проверка уникальности ФИО
        if (readerDao.checkUniqueName(reader).isPresent())
            errors.rejectValue("name", "duplicate", "Человек с таким ФИО уже занесен в базу");
    }
}
