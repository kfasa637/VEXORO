package com.vexora.client;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OverlayService extends Service {
    private WindowManager wm; private View bubble, panel; private WindowManager.LayoutParams bubbleParams;
    @Override public IBinder onBind(Intent i){return null;}
    @Override public void onCreate(){ super.onCreate(); wm=(WindowManager)getSystemService(WINDOW_SERVICE); int type=android.os.Build.VERSION.SDK_INT>=26?WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY:WindowManager.LayoutParams.TYPE_PHONE;
        bubbleParams=new WindowManager.LayoutParams(56,56,type,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT); bubbleParams.gravity=Gravity.TOP|Gravity.START; bubbleParams.x=24; bubbleParams.y=220;
        TextView v=new TextView(this); v.setText("V"); v.setTextColor(Color.WHITE); v.setTextSize(22); v.setTypeface(null,1); v.setGravity(Gravity.CENTER); v.setBackgroundColor(Color.rgb(35,35,39)); bubble=v;
        bubble.setOnTouchListener(new View.OnTouchListener(){int sx,sy;float dx,dy;boolean moved; public boolean onTouch(View view,MotionEvent e){ if(e.getAction()==MotionEvent.ACTION_DOWN){sx=bubbleParams.x;sy=bubbleParams.y;dx=e.getRawX();dy=e.getRawY();moved=false;return true;} if(e.getAction()==MotionEvent.ACTION_MOVE){int x=(int)(e.getRawX()-dx),y=(int)(e.getRawY()-dy);if(Math.abs(x)>5||Math.abs(y)>5)moved=true;bubbleParams.x=sx+x;bubbleParams.y=sy+y;wm.updateViewLayout(bubble,bubbleParams);return true;} if(e.getAction()==MotionEvent.ACTION_UP){if(!moved)togglePanel();return true;}return false;}}); wm.addView(bubble,bubbleParams);
    }
    private void togglePanel(){ if(panel!=null){try{wm.removeView(panel);}catch(Exception ignored){}panel=null;return;}
        LinearLayout box=new LinearLayout(this);box.setOrientation(LinearLayout.VERTICAL);box.setPadding(16,12,16,12);box.setBackgroundColor(Color.rgb(20,20,23)); TextView title=new TextView(this);title.setText("VEXORA  •  1.26.32");title.setTextColor(Color.WHITE);title.setTextSize(16);title.setPadding(0,0,0,8);box.addView(title);
        String[] labels={"Combat Practice","HUD","Utilities","Performance","Close"}; for(String label:labels){Button b=new Button(this);b.setText(label);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(38,38,42)));box.addView(b);b.setOnClickListener(v->{if(label.equals("Close")){togglePanel();return;}TextView detail=new TextView(this);detail.setText(label+"\nDemo panel only. Open VEXORA app for details.");detail.setTextColor(Color.LTGRAY);detail.setPadding(0,10,0,10);box.removeAllViews();box.addView(detail);Button close=new Button(this);close.setText("Close panel");close.setOnClickListener(x->togglePanel());box.addView(close);});}
        int type=android.os.Build.VERSION.SDK_INT>=26?WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY:WindowManager.LayoutParams.TYPE_PHONE;WindowManager.LayoutParams p=new WindowManager.LayoutParams(260,-2,type,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT);p.gravity=Gravity.TOP|Gravity.START;p.x=bubbleParams.x+64;p.y=bubbleParams.y;panel=box;wm.addView(panel,p);
    }
    @Override public void onDestroy(){if(wm!=null){if(panel!=null)try{wm.removeView(panel);}catch(Exception ignored){}if(bubble!=null)try{wm.removeView(bubble);}catch(Exception ignored){}}super.onDestroy();}
}
