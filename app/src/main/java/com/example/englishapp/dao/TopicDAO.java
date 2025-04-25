package com.example.englishapp.dao;

import com.example.englishapp.model.EPracticeType;
import com.example.englishapp.model.Practice;
import com.example.englishapp.model.Question;
import com.example.englishapp.model.Topic;
import com.example.englishapp.model.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopicDAO {
    // Danh sách tĩnh để lưu chủ đề
    private static List<Topic> topics = new ArrayList<>();
    private static long nextTopicId = 1; // Tự tăng ID cho numberTopic
    private static long nextPracticeId = 1; // Tự tăng ID cho Practice

    // Khối static để khởi tạo dữ liệu mẫu
    static {
        // Lấy câu hỏi từ QuestionDAO
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> allQuestions = questionDAO.getAll();

        // Chia câu hỏi theo chủ đề
        List<Question> topic1Questions = new ArrayList<>();
        List<Question> topic2Questions = new ArrayList<>();
        List<Question> topic3Questions = new ArrayList<>();
        List<Question> topic4Questions = new ArrayList<>();


        // Chủ đề 1: Từ vựng cơ bản
        List<Practice> practices1 = new ArrayList<>();
        practices1.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic1Questions));
        practices1.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic1Questions));
        Topic topic1 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu khẳng định", practices1, com.example.englishapp.R.drawable.lesson_card_background);
        practices1.forEach(p -> p.setTopic(topic1)); // Gán topic cho Practice
        topics.add(topic1);

        // Chủ đề 2: Ngữ pháp cơ bản
        List<Practice> practices2 = new ArrayList<>();
        practices2.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic2Questions));
        practices2.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic2Questions));
        Topic topic2 = new Topic("Topic " + nextTopicId++, "Danh từ số nhiều", practices2, com.example.englishapp.R.drawable.lesson_card_background2);
        practices2.forEach(p -> p.setTopic(topic2));
        topics.add(topic2);

        // Chủ đề 3: Từ vựng nâng cao
        List<Practice> practices3 = new ArrayList<>();
        practices3.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic3Questions));
        Topic topic3 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu nghi vấn", practices3, com.example.englishapp.R.drawable.lesson_card_background3);
        practices3.forEach(p -> p.setTopic(topic3));
        topics.add(topic3);

        // Chủ đề 4: Giao tiếp hàng ngày
        List<Practice> practices4 = new ArrayList<>();
        practices4.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic4Questions));
        practices4.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic4Questions));
        Topic topic4 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu phủ định", practices4, com.example.englishapp.R.drawable.lesson_card_background4);
        practices4.forEach(p -> p.setTopic(topic4));
        topics.add(topic4);
    }

    // Thêm chủ đề mới
    public boolean create(Topic topic) {
        if (topic == null || topic.getNumberTopic() != null) return false;

        topic.setNumberTopic("Topic" + nextTopicId++); // Gán numberTopic tự động
        topics.add(topic);
        // Gán topic cho các Practice
        if (topic.getPractices() != null) {
            topic.getPractices().forEach(p -> {
                p.setTopic(topic);
                if (p.getId() == null) {
                    p.setId(nextPracticeId++);
                }
            });
        }
        return true;
    }

    // Lấy chủ đề theo numberTopic
    public Topic getByID(Topic topic) {
        if (topic == null || topic.getNumberTopic() == null) return null;

        for (Topic t : topics) {
            if (t.getNumberTopic().equals(topic.getNumberTopic())) {
                return t;
            }
        }
        return null;
    }

    // Lấy tất cả chủ đề
    public List<Topic> getAll() {
        return new ArrayList<>(topics); // Trả về bản sao
    }

    // Cập nhật thông tin chủ đề
    public boolean update(Topic topic) {
        if (topic == null || topic.getNumberTopic() == null) return false;

        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).getNumberTopic().equals(topic.getNumberTopic())) {
                topics.set(i, topic);
                // Cập nhật topic cho các Practice
                if (topic.getPractices() != null) {
                    topic.getPractices().forEach(p -> p.setTopic(topic));
                }
                return true;
            }
        }
        return false;
    }

    // Xóa chủ đề
    public boolean delete(Topic topic) {
        if (topic == null || topic.getNumberTopic() == null) return false;

        return topics.removeIf(t -> t.getNumberTopic().equals(topic.getNumberTopic()));
    }
}