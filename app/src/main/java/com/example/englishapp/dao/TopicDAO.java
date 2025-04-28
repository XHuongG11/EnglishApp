package com.example.englishapp.dao;

import android.util.Log;

import com.example.englishapp.R;
import com.example.englishapp.model.EPracticeType;
import com.example.englishapp.model.Practice;
import com.example.englishapp.model.Question;
import com.example.englishapp.model.Topic;
import com.example.englishapp.model.Word;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopicDAO {
//    // Danh sách tĩnh để lưu chủ đề
//    private static List<Topic> topics = new ArrayList<>();
//    private static long nextTopicId = 1; // Tự tăng ID cho numberTopic
//    private static long nextPracticeId = 1; // Tự tăng ID cho Practice
//
//    // Khối static để khởi tạo dữ liệu mẫu
//    static {
//        // Lấy câu hỏi từ QuestionDAO
//        QuestionDAO questionDAO = new QuestionDAO();
////        List<Question> allQuestions = questionDAO.getAll();
//
//        // Chia câu hỏi theo chủ đề
//        List<Question> topic1Questions = new ArrayList<>();
//        List<Question> topic2Questions = new ArrayList<>();
//        List<Question> topic3Questions = new ArrayList<>();
//        List<Question> topic4Questions = new ArrayList<>();
//
//
//        // Chủ đề 1: Từ vựng cơ bản
//        List<Practice> practices1 = new ArrayList<>();
//        practices1.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic1Questions));
//        practices1.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic1Questions));
//        Topic topic1 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu khẳng định", practices1, com.example.englishapp.R.drawable.lesson_card_background);
//        practices1.forEach(p -> p.setTopic(topic1)); // Gán topic cho Practice
//        topics.add(topic1);
//
//        // Chủ đề 2: Ngữ pháp cơ bản
//        List<Practice> practices2 = new ArrayList<>();
//        practices2.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic2Questions));
//        practices2.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic2Questions));
//        Topic topic2 = new Topic("Topic " + nextTopicId++, "Danh từ số nhiều", practices2, com.example.englishapp.R.drawable.lesson_card_background2);
//        practices2.forEach(p -> p.setTopic(topic2));
//        topics.add(topic2);
//
//        // Chủ đề 3: Từ vựng nâng cao
//        List<Practice> practices3 = new ArrayList<>();
//        practices3.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic3Questions));
//        Topic topic3 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu nghi vấn", practices3, com.example.englishapp.R.drawable.lesson_card_background3);
//        practices3.forEach(p -> p.setTopic(topic3));
//        topics.add(topic3);
//
//        // Chủ đề 4: Giao tiếp hàng ngày
//        List<Practice> practices4 = new ArrayList<>();
//        practices4.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic4Questions));
//        practices4.add(new Practice(nextPracticeId++, EPracticeType.GHEPTU, null, topic4Questions));
//        Topic topic4 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu phủ định", practices4, com.example.englishapp.R.drawable.lesson_card_background4);
//        practices4.forEach(p -> p.setTopic(topic4));
//        topics.add(topic4);
//    }
//
//    // Thêm chủ đề mới
//    public boolean create(Topic topic) {
//        if (topic == null || topic.getNumberTopic() != null) return false;
//
//        topic.setNumberTopic("Topic" + nextTopicId++); // Gán numberTopic tự động
//        topics.add(topic);
//        // Gán topic cho các Practice
//        if (topic.getPractices() != null) {
//            topic.getPractices().forEach(p -> {
//                p.setTopic(topic);
//                if (p.getId() == null) {
//                    p.setId(nextPracticeId++);
//                }
//            });
//        }
//        return true;
//    }
//
//    // Lấy chủ đề theo numberTopic
//    public Topic getByID(Topic topic) {
//        if (topic == null || topic.getNumberTopic() == null) return null;
//
//        for (Topic t : topics) {
//            if (t.getNumberTopic().equals(topic.getNumberTopic())) {
//                return t;
//            }
//        }
//        return null;
//    }
//
//    // Lấy tất cả chủ đề
//    public List<Topic> getAll() {
//        return new ArrayList<>(topics); // Trả về bản sao
//    }
//
//    // Cập nhật thông tin chủ đề
//    public boolean update(Topic topic) {
//        if (topic == null || topic.getNumberTopic() == null) return false;
//
//        for (int i = 0; i < topics.size(); i++) {
//            if (topics.get(i).getNumberTopic().equals(topic.getNumberTopic())) {
//                topics.set(i, topic);
//                // Cập nhật topic cho các Practice
//                if (topic.getPractices() != null) {
//                    topic.getPractices().forEach(p -> p.setTopic(topic));
//                }
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // Xóa chủ đề
//    public boolean delete(Topic topic) {
//        if (topic == null || topic.getNumberTopic() == null) return false;
//
//        return topics.removeIf(t -> t.getNumberTopic().equals(topic.getNumberTopic()));
//    }

private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void create(Topic topic, OnSaveUpdateListener listener) {
        try {
            db.collection("topics")
                    .document(topic.getNumberTopic())
                    .set(topic)
                    .addOnSuccessListener(documentReference -> {
                        listener.onComplete(true);
                        Log.d("Firebase", "Success saving topic");
                    })
                    .addOnFailureListener(e -> {
                        listener.onComplete(false);
                        Log.w("Firebase", "Error saving topic", e);
                    });
        } catch (Exception ex) {
            Log.e("CREATE_TOPIC", ex.getMessage());
        }
    }

    public void getByID(String topicID, OnGetByIdListener<Topic> listener) {
        db.collection("topics")
                .document(topicID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Topic topic = documentSnapshot.toObject(Topic.class);
                        listener.onGetByID(topic);
                    } else {
                        listener.onGetByID(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error getting topic by ID", e);
                    listener.onGetByID(null);
                });
    }

    public void getByNumberTopic(String numberTopic, OnGetByIdListener<Topic> listener) {
        db.collection("topics")
                .whereEqualTo("numberTopic", numberTopic)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        Topic topic = querySnapshot.getDocuments().get(0).toObject(Topic.class);
                        listener.onGetByID(topic);
                    } else {
                        listener.onGetByID(null);
                    }
                })
                .addOnFailureListener(listener::onGetFailed);
    }
    public void getAll(OnGetAllListener<Topic> listener) {
        db.collection("topics")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<Topic> topicList = queryDocumentSnapshots.toObjects(Topic.class);
                        listener.onGetAll(topicList);
                    } else {
                        listener.onGetAll(Collections.emptyList());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error getting all topics", e);
                    listener.onGetAll(Collections.emptyList());
                });
    }

    public void update(String id, Topic topic, OnSaveUpdateListener listener) {
        db.collection("topics")
                .document(id)
                .set(topic)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Topic updated successfully");
                    listener.onComplete(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error updating topic", e);
                    listener.onComplete(false);
                });
    }

    public void delete(String id, OnSaveUpdateListener listener) {
        db.collection("topics")
                .document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Topic deleted successfully");
                    listener.onComplete(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error deleting topic", e);
                    listener.onComplete(false);
                });
    }

}