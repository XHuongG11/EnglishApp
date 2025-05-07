package com.example.englishapp.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.englishapp.model.FlyingWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread thread;
    private List<FlyingWord> words;
    private Paint paint;
    private Random random = new Random();

    public GameSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        words = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String[] allWords = {"cat", "dog", "apple", "car", "book"};
            float y = 100 + i * 150;
            words.add(new FlyingWord(allWords[random.nextInt(allWords.length)], 0, y, 5 + random.nextInt(5)));
        }

        thread = new GameThread(getHolder());
        thread.setRunning(true);
        thread.start();
    }

    private class GameThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private boolean running;

        public GameThread(SurfaceHolder holder) {
            surfaceHolder = holder;
        }

        public void setRunning(boolean run) {
            running = run;
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    if (canvas == null) continue;
                    synchronized (surfaceHolder) {
                        canvas.drawColor(Color.WHITE);
                        for (FlyingWord word : words) {
                            word.update();
                            canvas.drawText(word.word, word.x, word.y, paint);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

                try {
                    Thread.sleep(30); // khoảng 30 FPS
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX(), y = event.getY();
            for (FlyingWord word : words) {
                float textWidth = paint.measureText(word.word);
                if (x >= word.x && x <= word.x + textWidth &&
                        y >= word.y - 50 && y <= word.y + 20) {
                    Toast.makeText(getContext(), "Bạn chọn: " + word.word, Toast.LENGTH_SHORT).show();
                    // TODO: ghi câu trả lời vào Firebase
                    break;
                }
            }
        }
        return true;
    }
}
