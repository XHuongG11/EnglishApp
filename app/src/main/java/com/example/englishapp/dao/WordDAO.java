package com.example.englishapp.dao;

import com.example.englishapp.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordDAO {
    // Danh sách tĩnh để lưu từ vựng
    static List<Word> words = new ArrayList<>();
    private static long nextId = 1; // Dùng để tự tăng ID
    static {
        words.add(new Word(nextId++, "apple", "quả táo", "ˈæp.əl", "I eat an apple every day.", "https://example.com/apple.jpg", new ArrayList<>()));
        words.add(new Word(nextId++, "book", "quyển sách", "bʊk", "This book is very interesting.", "https://example.com/book.jpg", new ArrayList<>()));
        words.add(new Word(nextId++, "computer", "máy tính", "kəmˈpjuː.tər", "My computer is new.", "https://example.com/computer.jpg", new ArrayList<>()));
    }

    // Thêm từ mới
    public boolean create(Word word) {
        if (word == null) return false;

        word.setId(nextId++); // Gán ID tự động
        words.add(word);
        return true;
    }

    // Lấy từ theo ID
    public Word getByID(Word word) {
        if (word == null || word.getId() == null) return null;

        for (Word w : words) {
            if (w.getId().equals(word.getId())) {
                return w;
            }
        }
        return null;
    }

    // Lấy tất cả từ vựng
    public List<Word> getAll() {
        return new ArrayList<>(words);
    }

    // Cập nhật thông tin từ
    public boolean update(Word word) {
        if (word == null || word.getId() == null) return false;

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getId().equals(word.getId())) {
                words.set(i, word);
                return true;
            }
        }
        return false;
    }

    // Xóa từ
    public boolean delete(Word word) {
        if (word == null || word.getId() == null) return false;

        return words.removeIf(w -> w.getId().equals(word.getId()));
    }
}
