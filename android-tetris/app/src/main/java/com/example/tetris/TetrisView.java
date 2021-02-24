package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class TetrisView extends SurfaceView implements SurfaceHolder.Callback {
    private int mWidth;
    private int mHeight;
    private TetrisThread mThread = null;
    private Rect brick1;
    private Rect brick2;
    private Rect brick3;
    private Rect brick4;
    private int form;
    private int state = 0;
    private int type = 0;
    private boolean hit = true;
    private boolean rotation = false;
    private boolean moving = false;
    private boolean moveLeftorRightAllowed = true;
    private boolean rotateAllowed = true;
    private Random formRand = new Random();
    private Random typeRand = new Random();
    private ArrayList<Rect> bricks = new ArrayList<Rect>();
    private ArrayList<Paint> colors = new ArrayList<Paint>();

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        mThread = new TetrisThread(holder, this);
        setFocusable(true); // make sure we get key events
    }

    public TetrisThread getThread() {
        return mThread;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN) {
            Posision touch = new Posision((int)event.getX(0), (int)event.getY(0)) ;
            if (touch.yPos > mHeight/2) {
                move(touch.xPos);
            } else {
                rotate(touch.xPos);
            }
        }
        return true;
    }

    public void move(int x) {
        moving = true;
        if (x < mWidth/2) {
            if (brick1.left - mWidth/10 < 0 || brick2.left - mWidth/10 < 0 || brick3.left - mWidth/10 < 0 || brick4.left - mWidth/10 < 0) {
                moveLeftorRightAllowed = false;
            }
            for (int i = 0; i < bricks.size() - 4; i++) {
                if ((brick1.bottom == bricks.get(i).bottom && brick1.left - mWidth/10 == bricks.get(i).left) ||
                        (brick2.bottom == bricks.get(i).bottom && brick2.left - mWidth/10 == bricks.get(i).left) ||
                        (brick3.bottom == bricks.get(i).bottom && brick3.left - mWidth/10 == bricks.get(i).left) ||
                        (brick4.bottom == bricks.get(i).bottom && brick4.left - mWidth/10 == bricks.get(i).left)) {
                    moveLeftorRightAllowed = false;
                }
            }
            if (moveLeftorRightAllowed) {
                brick1.offset(- mWidth/10, 0);
                brick2.offset(- mWidth/10, 0);
                brick3.offset(- mWidth/10, 0);
                brick4.offset(- mWidth/10, 0);
            } else {
                moveLeftorRightAllowed = true;
            }

        } else {
            if (brick1.right + mWidth/10 > mWidth || brick2.right + mWidth/10 > mWidth || brick3.right + mWidth/10 > mWidth || brick4.right + mWidth/10 > mWidth) {
                moveLeftorRightAllowed = false;
            }

            for (int i = 0; i < bricks.size() - 4; i++) {
                if ((brick1.bottom == bricks.get(i).bottom && brick1.left + mWidth/10 == bricks.get(i).left) ||
                        (brick2.bottom == bricks.get(i).bottom && brick2.left + mWidth/10 == bricks.get(i).left) ||
                        (brick3.bottom == bricks.get(i).bottom && brick3.left + mWidth/10 == bricks.get(i).left) ||
                        (brick4.bottom == bricks.get(i).bottom && brick4.left + mWidth/10 == bricks.get(i).left)) {
                    moveLeftorRightAllowed = false;
                }
            }

            if (moveLeftorRightAllowed) {
                brick1.offset(mWidth/10, 0);
                brick2.offset(mWidth/10, 0);
                brick3.offset(mWidth/10, 0);
                brick4.offset(mWidth/10, 0);
            } else {
                moveLeftorRightAllowed = true;
            }
        }
    }

    public void rotate(int x) {
        rotation = true;
        switch (form) {
            case 0:
                if (state == 0) {
                    for (int i = 0; i < bricks.size() - 4; i++) {
                        if ((brick1.bottom - mHeight/20 * 3 == bricks.get(i).bottom && brick1.left + mWidth/10 * 2 == bricks.get(i).left) ||
                                (brick2.bottom - mHeight/20 * 2 == bricks.get(i).bottom && brick2.left + mWidth/10 == bricks.get(i).left) ||
                                (brick3.bottom - mHeight/20 == bricks.get(i).bottom && brick3.left == bricks.get(i).left)) {
                            rotateAllowed = false;
                        }
                    }
                    if (rotateAllowed) {
                        brick1.offset(mWidth/10 * 2, - mHeight/20 * 3);
                        brick2.offset(mWidth/10, - mHeight/20 * 2);
                        brick3.offset(0, - mHeight/20);
                        brick4.offset(- mWidth/10, 0);
                        state = 1;
                    } else {
                        rotateAllowed = true;
                    }
                } else {
                    if (brick1.left - mWidth/10 * 2 < 0 || brick4.right + mWidth/10 > mWidth) {
                        rotateAllowed = false;
                    }
                    for (int i = 0; i < bricks.size() - 4; i++) {
                        if ((brick1.bottom + mHeight/20 * 3 == bricks.get(i).bottom && brick1.left - mWidth/10 * 2 == bricks.get(i).left) ||
                                (brick2.bottom + mHeight/20 * 2 == bricks.get(i).bottom && brick2.left - mWidth/10 == bricks.get(i).left) ||
                                (brick4.bottom == bricks.get(i).bottom && brick4.left + mWidth/10 == bricks.get(i).left)) {
                            rotateAllowed = false;
                        }
                    }
                    if (rotateAllowed) {
                        brick1.offset(- mWidth/10 * 2, mHeight/20 * 3);
                        brick2.offset(- mWidth/10, mHeight/20 * 2);
                        brick3.offset(0, mHeight/20);
                        brick4.offset(mWidth/10, 0);
                        state = 0;
                    } else {
                        rotateAllowed = true;
                    }
                }
                break;
            case 1:
                if (x < mWidth/2) {
                    if (type == 0) {
                        if (state == 0) {
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom + mHeight/20 == bricks.get(i).bottom && brick1.left + mWidth/10 == bricks.get(i).left) ||
                                        (brick3.bottom - mHeight/20 == bricks.get(i).bottom && brick3.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick4.bottom - mHeight/20 * 2 == bricks.get(i).bottom && brick4.left == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(mWidth/10, mHeight/20);
                                brick3.offset(- mWidth/10, - mHeight/20);
                                brick4.offset(0,- mHeight/20 * 2);
                                state = 3;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 1) {
                            if (brick1.left - mWidth/10 * 2 < 0) {
                                rotateAllowed = false;
                            }
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom + mHeight/20 == bricks.get(i).bottom && brick1.left - mWidth/10 * 2 == bricks.get(i).left) ||
                                        (brick2.bottom == bricks.get(i).bottom && brick2.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick3.bottom - mHeight/20 == bricks.get(i).bottom && brick3.left == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(- mWidth/10 * 2, mHeight/20);
                                brick2.offset(- mWidth/10, 0);
                                brick3.offset(0, - mHeight/20);
                                brick4.offset(mWidth/10, 0);
                                state = 0;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 2) {
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom - mHeight/20 * 2 == bricks.get(i).bottom && brick1.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick2.bottom - mHeight/20 == bricks.get(i).bottom && brick2.left == bricks.get(i).left) ) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(- mWidth/10, - mHeight/20 * 2);
                                brick2.offset(0, - mHeight/20);
                                brick3.offset(mWidth/10, 0);
                                brick4.offset(0, mHeight/20);
                                state = 1;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 3) {
                            if (brick4.left - mWidth/10 * 2 < 0) {
                                rotateAllowed = false;
                            }
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom == bricks.get(i).bottom && brick1.left + mWidth/10 == bricks.get(i).left) ||
                                        (brick3.bottom + mHeight/20 * 2 == bricks.get(i).bottom && brick3.left  - mWidth/10 == bricks.get(i).left) ||
                                        (brick4.bottom + mHeight/20 == bricks.get(i).bottom && brick4.left - mWidth/10 * 2 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(mWidth/10, 0);
                                brick2.offset(0, mHeight/20);
                                brick3.offset(- mWidth/10, mHeight/20 * 2);
                                brick4.offset(- mWidth/10 * 2, mHeight/20);
                                state = 2;
                            } else {
                                rotateAllowed = true;
                            }
                        }
                    } else {
                        if (state == 0) {
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom + mHeight/20 == bricks.get(i).bottom && brick1.left + mWidth/10 == bricks.get(i).left) ||
                                        (brick3.bottom - mHeight/20 == bricks.get(i).bottom && brick3.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick4.bottom == bricks.get(i).bottom && brick4.left + mWidth/10 * 2 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(mWidth/10, mHeight/20);
                                brick3.offset(- mWidth/10, - mHeight/20);
                                brick4.offset(mWidth/10 * 2, 0);
                                state = 3;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 1) {
                            if (brick1.left - mWidth/10 * 2 < 0) {
                                rotateAllowed = false;
                            }
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom + mHeight/20 == bricks.get(i).bottom && brick1.left - mWidth/10 * 2 == bricks.get(i).left) ||
                                        (brick2.bottom == bricks.get(i).bottom && brick2.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick4.bottom + mHeight/20 * 2 == bricks.get(i).bottom && brick4.left - mWidth/10 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(- mWidth/10 * 2, mHeight/20);
                                brick2.offset(- mWidth/10, 0);
                                brick3.offset(0, - mHeight/20);
                                brick4.offset(- mWidth/10, mHeight/20 * 2);
                                state = 0;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 2) {
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom - mHeight/20 * 2 == bricks.get(i).bottom && brick1.left == bricks.get(i).left) ||
                                        (brick4.bottom - mHeight/20 == bricks.get(i).bottom && brick4.left - mWidth/10 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(0, - mHeight/20 * 2);
                                brick2.offset(mWidth/10, - mHeight/20);
                                brick3.offset(mWidth/10 * 2, 0);
                                brick4.offset(- mWidth/10, - mHeight/20);
                                state = 1;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 3) {
                            if (brick3.left - mWidth/10 < 0) {
                                rotateAllowed = false;
                            }
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick3.bottom + mHeight/20 * 2 == bricks.get(i).bottom && brick3.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick4.bottom - mHeight/20 == bricks.get(i).bottom && brick4.left == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(mWidth/10, 0);
                                brick2.offset(0, mHeight/20);
                                brick3.offset(- mWidth/10, mHeight/20 * 2);
                                brick4.offset(0, - mHeight/20);
                                state = 2;
                            } else {
                                rotateAllowed = true;
                            }
                        }
                    }
                } else {
                    if (type == 0) {
                        if (state == 0) {
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom - mHeight/20 == bricks.get(i).bottom && brick1.left + mWidth/10 * 2 == bricks.get(i).left) ||
                                        (brick4.bottom == bricks.get(i).bottom && brick4.left - mWidth/10 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(mWidth/10 * 2, - mHeight/20);
                                brick2.offset(mWidth/10, 0);
                                brick3.offset(0, mHeight/20);
                                brick4.offset(- mWidth/10, 0);
                                state = 1;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 1) {
                            if (brick4.left - mWidth/10 < 0) {
                                rotateAllowed = false;
                            }
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick3.bottom == bricks.get(i).bottom && brick3.left - mWidth/10 * 2 == bricks.get(i).left) ||
                                        (brick4.bottom - mHeight/20 == bricks.get(i).bottom && brick4.left - mWidth/10 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(0, mHeight/20 * 2);
                                brick2.offset(- mWidth/10, mHeight/20);
                                brick3.offset(- mWidth/10 * 2, 0);
                                brick4.offset(- mWidth/10, - mHeight/20);
                                state = 2;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 2) {
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick2.bottom - mHeight/20 == bricks.get(i).bottom && brick2.left == bricks.get(i).left) ||
                                        (brick3.bottom - mHeight/20 * 2 == bricks.get(i).bottom && brick3.left + mWidth/10 == bricks.get(i).left) ||
                                        (brick4.bottom - mHeight/20 == bricks.get(i).bottom && brick4.left + mWidth/10 * 2 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(- mWidth/10, 0);
                                brick2.offset(0, - mHeight/20);
                                brick3.offset(mWidth/10, - mHeight/20 * 2);
                                brick4.offset(mWidth/10 * 2, - mHeight/20);
                                state = 3;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 3) {
                            if (brick1.left - mWidth/10 < 0) {
                                rotateAllowed = false;
                            }
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom - mHeight/20 == bricks.get(i).bottom && brick1.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick3.bottom + mHeight/20 == bricks.get(i).bottom && brick3.left + mWidth/10 == bricks.get(i).left) ||
                                        (brick4.bottom + mHeight/20 * 2 == bricks.get(i).bottom && brick4.left == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(- mWidth/10, - mHeight/20);
                                brick3.offset(mWidth/10, mHeight/20);
                                brick4.offset(0, mHeight/20 * 2);
                                state = 0;
                            } else {
                                rotateAllowed = true;
                            }
                        }
                    } else {
                        if (state == 0) {
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom - mHeight/20 == bricks.get(i).bottom && brick1.left + mWidth/10 * 2 == bricks.get(i).left) ||
                                        (brick3.bottom + mHeight/20 == bricks.get(i).bottom && brick3.left == bricks.get(i).left) ||
                                        (brick4.bottom - mHeight/20 * 2 == bricks.get(i).bottom && brick4.left + mWidth/10 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(mWidth/10 * 2, - mHeight/20);
                                brick2.offset(mWidth/10, 0);
                                brick3.offset(0, mHeight/20);
                                brick4.offset(mWidth/10, - mHeight/20 * 2);
                                state = 1;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 1) {
                            if (brick3.left - mWidth/10 * 2 < 0) {
                                rotateAllowed = false;
                            }
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick2.bottom + mHeight/20 == bricks.get(i).bottom && brick2.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick3.bottom == bricks.get(i).bottom && brick3.left - mWidth/10 * 2 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(0, mHeight/20 * 2);
                                brick2.offset(- mWidth/10, mHeight/20);
                                brick3.offset(- mWidth/10 * 2, 0);
                                brick4.offset(mWidth/10, mHeight/20);
                                state = 2;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 2) {
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick2.bottom - mHeight/20 == bricks.get(i).bottom && brick2.left == bricks.get(i).left) ||
                                        (brick3.bottom - mHeight/20 * 2 == bricks.get(i).bottom && brick3.left + mWidth/10 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(- mWidth/10, 0);
                                brick2.offset(0, - mHeight/20);
                                brick3.offset(mWidth/10, - mHeight/20 * 2);
                                brick4.offset(0, mHeight/20);
                                state = 3;
                            } else {
                                rotateAllowed = true;
                            }
                        } else if (state == 3) {
                            if (brick1.left - mWidth/10 < 0) {
                                rotateAllowed = false;
                            }
                            for (int i = 0; i < bricks.size() - 4; i++) {
                                if ((brick1.bottom - mHeight/20 == bricks.get(i).bottom && brick1.left - mWidth/10 == bricks.get(i).left) ||
                                        (brick3.bottom + mHeight/20 == bricks.get(i).bottom && brick3.left + mWidth/10 == bricks.get(i).left) ||
                                        (brick4.bottom == bricks.get(i).bottom && brick4.left - mWidth/10 * 2 == bricks.get(i).left)) {
                                    rotateAllowed = false;
                                }
                            }
                            if (rotateAllowed) {
                                brick1.offset(- mWidth/10, - mHeight/20);
                                brick3.offset(mWidth/10, mHeight/20);
                                brick4.offset(- mWidth/10 * 2, 0);
                                state = 0;
                            } else {
                                rotateAllowed = true;
                            }
                        }
                    }
                }
                break;
            case 3:
                if (type == 0) {
                    if (state == 0) {
                        for (int i = 0; i < bricks.size() - 4; i++) {
                            if ((brick1.bottom - mHeight/20 == bricks.get(i).bottom && brick1.left + mWidth/10 * 2 == bricks.get(i).left) ||
                                    (brick2.bottom == bricks.get(i).bottom && brick2.left + mWidth/10 == bricks.get(i).left)) {
                                rotateAllowed = false;
                            }
                        }
                        if (rotateAllowed) {
                            brick1.offset(mWidth/10 * 2, - mHeight/20);
                            brick2.offset(mWidth/10, 0);
                            brick3.offset(0, - mHeight/20);
                            brick4.offset(- mWidth/10, 0);
                            state = 1;
                        } else {
                                rotateAllowed = true;
                            }
                    } else {
                        if (brick1.left - mWidth/10 * 2 < 0) {
                            rotateAllowed = false;
                        }
                        for (int i = 0; i < bricks.size() - 4; i++) {
                            if ((brick1.bottom + mHeight/20 == bricks.get(i).bottom && brick1.left - mWidth/10 * 2 == bricks.get(i).left) ||
                                    (brick4.bottom == bricks.get(i).bottom && brick4.left + mWidth/10 == bricks.get(i).left)) {
                                rotateAllowed = false;
                            }
                        }
                        if (rotateAllowed) {
                            brick1.offset(- mWidth/10 * 2, mHeight/20);
                            brick2.offset(- mWidth/10, 0);
                            brick3.offset(0, mHeight/20);
                            brick4.offset(mWidth/10, 0);
                            state = 0;
                        } else {
                            rotateAllowed = true;
                        }
                    }
                } else {
                    if (state == 0) {
                        for (int i = 0; i < bricks.size() - 4; i++) {
                            if ((brick3.bottom == bricks.get(i).bottom && brick3.left - mWidth/10 == bricks.get(i).left) ||
                                    (brick4.bottom - mHeight/20 == bricks.get(i).bottom && brick4.left - mWidth/10 * 2 == bricks.get(i).left)) {
                                rotateAllowed = false;
                            }
                        }
                        if (rotateAllowed) {
                            brick1.offset(mWidth/10, 0);
                            brick2.offset(0, - mHeight/20);
                            brick3.offset(- mWidth/10, 0);
                            brick4.offset(- mWidth/10 * 2,- mHeight/20);
                            state = 1;
                        } else {
                            rotateAllowed = true;
                        }
                    } else {
                        if (brick4.right + mWidth/10 * 2 > mWidth) {
                            rotateAllowed = false;
                        }
                        for (int i = 0; i < bricks.size() - 4; i++) {
                            if ((brick1.bottom == bricks.get(i).bottom && brick1.left - mWidth/10 == bricks.get(i).left) ||
                                    (brick4.bottom + mHeight/20 == bricks.get(i).bottom && brick4.left + mWidth/10 * 2 == bricks.get(i).left)) {
                                rotateAllowed = false;
                            }
                        }
                        if (rotateAllowed) {
                            brick1.offset(- mWidth/10, 0);
                            brick2.offset(0, mHeight/20);
                            brick3.offset(mWidth/10, 0);
                            brick4.offset(mWidth/10 * 2,mHeight/20);
                            state = 0;
                        } else {
                            rotateAllowed = true;
                        }

                    }
                }
                break;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!mThread.isPaused()) {
            updateBrick();
        }
        canvas.drawColor(Color.WHITE);
        drawBrick(canvas);
    }

    public void drawBrick(Canvas canvas) {
        Paint paint;
        int color = 0;
        for (int i = 0; i < bricks.size(); i++) {
            if (i % 4 == 0 && i != 0) {
                color ++;
            }
            paint = colors.get(color);
            Rect m = bricks.get(i);
            canvas.drawRect(m, paint);
        }
    }

    public void newBrick() {
        state = 0;
        type = 0;
        Paint brickPaint = new Paint();
        form = formRand.nextInt(4);
        switch (form) {
            case 0:
                brick1 = new Rect(mWidth/10 * 3, mHeight/20,mWidth/10 * 4,mHeight/20 * 2);
                bricks.add(brick1);
                brick2 = new Rect(mWidth/10 * 4, mHeight/20,mWidth/10 * 5,mHeight/20 * 2);
                bricks.add(brick2);
                brick3 = new Rect(mWidth/10 * 5, mHeight/20,mWidth/10 * 6,mHeight/20 * 2);
                bricks.add(brick3);
                brick4 = new Rect(mWidth/10 * 6, mHeight/20,mWidth/10 * 7,mHeight/20 * 2);
                bricks.add(brick4);

                brickPaint.setColor(Color.RED);
                colors.add(brickPaint);
                break;
            case 1:
                type = typeRand.nextInt(2);
                if (type == 0) {
                    brick1 = new Rect(mWidth/10 * 3, mHeight/20, mWidth/10 * 4, mHeight/20 * 2);
                    bricks.add(brick1);
                    brick2 = new Rect(mWidth/10 * 4, mHeight/20, mWidth/10 * 5, mHeight/20 * 2);
                    bricks.add(brick2);
                    brick3 = new Rect(mWidth/10 * 5, mHeight/20, mWidth/10 * 6, mHeight/20 * 2);
                    bricks.add(brick3);
                    brick4 = new Rect(mWidth/10 * 5, mHeight/20 * 2, mWidth/10 * 6, mHeight/20 * 3);
                    bricks.add(brick4);
                } else {
                    brick1 = new Rect(mWidth/10 * 3, mHeight/20,mWidth/10 * 4,mHeight/20 * 2);
                    bricks.add(brick1);
                    brick2 = new Rect(mWidth/10 * 4, mHeight/20,mWidth/10 * 5,mHeight/20 * 2);
                    bricks.add(brick2);
                    brick3 = new Rect(mWidth/10 * 5, mHeight/20,mWidth/10 * 6,mHeight/20 * 2);
                    bricks.add(brick3);
                    brick4 = new Rect(mWidth/10 * 3, mHeight/20 * 2,mWidth/10 * 4,mHeight/20 * 3);
                    bricks.add(brick4);
                }

                brickPaint.setColor(Color.GREEN);
                colors.add(brickPaint);
                break;
            case 2:
                brick1 = new Rect(mWidth/10 * 4, mHeight/20,mWidth/10 * 5,mHeight/20 * 2);
                bricks.add(brick1);
                brick2 = new Rect(mWidth/10 * 4, mHeight/20 * 2,mWidth/10 * 5,mHeight/20 * 3);
                bricks.add(brick2);
                brick3 = new Rect(mWidth/10 * 5, mHeight/20,mWidth/10 * 6,mHeight/20 * 2);
                bricks.add(brick3);
                brick4 = new Rect(mWidth/10 * 5, mHeight/20 * 2,mWidth/10 * 6,mHeight/20 * 3);
                bricks.add(brick4);

                brickPaint.setColor(Color.BLUE);
                colors.add(brickPaint);
                break;
            case 3:
                type = typeRand.nextInt(2);
                if (type == 0) {
                    brick1 = new Rect(mWidth/10 * 4, mHeight/20, mWidth/10 * 5, mHeight/20 * 2);
                    bricks.add(brick1);
                    brick2 = new Rect(mWidth/10 * 5, mHeight/20, mWidth/10 * 6, mHeight/20 * 2);
                    bricks.add(brick2);
                    brick3 = new Rect(mWidth/10 * 5, mHeight/20 * 2, mWidth/10 * 6, mHeight/20 * 3);
                    bricks.add(brick3);
                    brick4 = new Rect(mWidth/10 * 6, mHeight/20 * 2, mWidth/10 * 7, mHeight/20 * 3);
                    bricks.add(brick4);
                } else {
                    brick1 = new Rect(mWidth/10 * 4, mHeight/20 * 2,mWidth/10 * 5,mHeight/20 * 3);
                    bricks.add(brick1);
                    brick2 = new Rect(mWidth/10 * 5, mHeight/20 * 2,mWidth/10 * 6,mHeight/20 * 3);
                    bricks.add(brick2);
                    brick3 = new Rect(mWidth/10 * 5, mHeight/20,mWidth/10 * 6,mHeight/20 * 2);
                    bricks.add(brick3);
                    brick4 = new Rect(mWidth/10 * 6, mHeight/20,mWidth/10 * 7,mHeight/20 * 2);
                    bricks.add(brick4);
                }

                brickPaint.setColor(Color.MAGENTA);
                colors.add(brickPaint);
                break;
        }
    }

    public void updateBrick () {
        if (hit) {
            newBrick();
            for (int i = 0; i < bricks.size() - 4; i++) {
                if ((brick1.bottom == bricks.get(i).top && brick1.left == bricks.get(i).left) ||
                        (brick2.bottom == bricks.get(i).top && brick2.left == bricks.get(i).left) ||
                        (brick3.bottom == bricks.get(i).top && brick3.left == bricks.get(i).left) ||
                        (brick4.bottom == bricks.get(i).top && brick4.left == bricks.get(i).left)) {
                    getThread().setPaused(true);
                }
            }
            hit = false;
        }
        if (rotation || moving) {
            rotation = false;
            moving = false;
        } else {
            brick1.offset(0, mHeight/20);
            brick2.offset(0, mHeight/20);
            brick3.offset(0, mHeight/20);
            brick4.offset(0, mHeight/20);

            if (brick1.bottom > mHeight - mHeight/20 || brick2.bottom > mHeight - mHeight/20 || brick3.bottom > mHeight - mHeight/20 || brick4.bottom > mHeight - mHeight/20) {
                hit = true;
            } else {
                for (int i = 0; i < bricks.size() - 4; i++) {
                    if ((brick1.bottom == bricks.get(i).top && brick1.left == bricks.get(i).left) ||
                            (brick2.bottom == bricks.get(i).top && brick2.left == bricks.get(i).left) ||
                            (brick3.bottom == bricks.get(i).top && brick3.left == bricks.get(i).left) ||
                            (brick4.bottom == bricks.get(i).top && brick4.left == bricks.get(i).left)) {
                        hit = true;
                    }
                }
            }
        }
    }

    public void surfaceChanged (SurfaceHolder holder,int format, int width, int height){
        mWidth = width;
        mHeight = height;
    }
    public void surfaceCreated (SurfaceHolder holder){
        mThread.setRunning(true);
        mThread.start();
    }
    public void surfaceDestroyed (SurfaceHolder holder){
        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public class Posision {
        private int xPos;
        private int yPos;

        public Posision(int x, int y) {
            xPos = x;
            yPos = y;
        }

        public Posision(Posision orig) {
            xPos = orig.xPos;
            yPos = orig.yPos;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Posision)) return false;
            Posision theOther = (Posision) other;
            return xPos == theOther.xPos && yPos == theOther.yPos;
        }
    }
}
