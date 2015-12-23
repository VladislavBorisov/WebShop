package by.bsuir.store.validator;

public interface Validator<T> {
    String isValid(T t);
}
