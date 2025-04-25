package com.example.englishapp.dao;

import com.example.englishapp.model.Question;
import com.example.englishapp.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionDAO {
    // Danh sách tĩnh để lưu câu hỏi
    private static List<Question> questions = new ArrayList<>();
    private static long nextId = 1; // Tự tăng ID cho câu hỏi

    // Khối static để khởi tạo dữ liệu mẫu
    static {
        // Câu hỏi 1: 6 từ
        Word correctAnswer1 = new Word(1L, "apple", "quả táo", "ˈæp.əl", "I eat an apple every day", "https://example.com/apple.jpg", new ArrayList<>());
        List<Word> answers1 = new ArrayList<>();
        answers1.add(correctAnswer1);
        questions.add(new Question(nextId++, "I eat an apple every day", "Tôi ăn một quả táo mỗi ngày", correctAnswer1, answers1));

        // Câu hỏi 2: 5 từ
        Word correctAnswer2 = new Word(2L, "book", "quyển sách", "bʊk", "This book is very interesting", "https://example.com/book.jpg", new ArrayList<>());
        List<Word> answers2 = new ArrayList<>();
        answers2.add(correctAnswer2);
        questions.add(new Question(nextId++, "This book is very interesting", "Quyển sách này rất thú vị", correctAnswer2, answers2));

        // Câu hỏi 3: 5 từ
        Word correctAnswer3 = new Word(3L, "dog", "con chó", "dɒɡ", "My dog is very cute", "https://example.com/dog.jpg", new ArrayList<>());
        List<Word> answers3 = new ArrayList<>();
        answers3.add(correctAnswer3);
        questions.add(new Question(nextId++, "My dog is very cute", "Con chó của tôi rất dễ thương", correctAnswer3, answers3));

        // Câu hỏi 4: 6 từ
        Word correctAnswer4 = new Word(4L, "study", "học", "ˈstʌd.i", "I study English every single day", "https://example.com/study.jpg", new ArrayList<>());
        List<Word> answers4 = new ArrayList<>();
        answers4.add(correctAnswer4);
        questions.add(new Question(nextId++, "I study English every single day", "Tôi học tiếng Anh mỗi ngày", correctAnswer4, answers4));
    }

    // Thêm câu hỏi mới
    public boolean create(Question question) {
        if (question == null) return false;

        question.setId(nextId++); // Gán ID tự động
        questions.add(question);
        return true;
    }

    // Lấy câu hỏi theo ID
    public Question getByID(Question question) {
        if (question == null || question.getId() == null) return null;

        for (Question q : questions) {
            if (q.getId().equals(question.getId())) {
                return q;
            }
        }
        return null;
    }

    // Lấy tất cả câu hỏi
    public List<Question> getAll() {
        return new ArrayList<>(questions); // Trả về bản sao để tránh sửa đổi trực tiếp
    }

    // Cập nhật thông tin câu hỏi
    public boolean update(Question question) {
        if (question == null || question.getId() == null) return false;

        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(question.getId())) {
                questions.set(i, question);
                return true;
            }
        }
        return false;
    }

    // Xóa câu hỏi
    public boolean delete(Question question) {
        if (question == null || question.getId() == null) return false;

        return questions.removeIf(q -> q.getId().equals(question.getId()));
    }
}