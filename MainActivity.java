package com.vexora.client;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(15,15,17), card = Color.rgb(29,29,33), white = Color.WHITE;
    private LinearLayout root;
    @Override public void onCreate(Bundle b) { super.onCreate(b); getWindow().setStatusBarColor(bg); getWindow().setNavigationBarColor(bg); home(); }
    private TextView text(String s, int size, int color) { TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); t.setGravity(Gravity.CENTER_VERTICAL); return t; }
    private Button button(String s) { Button b=new Button(this); b.setText(s); b.setTextColor(white); b.setAllCaps(false); b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(card)); return b; }
    private void setup(String title, String subtitle) {
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(22,22,22,22); root.setBackgroundColor(bg);
        ScrollView scroll=new ScrollView(this); scroll.addView(root); setContentView(scroll);
        TextView logo=text(title,29,white); logo.setTypeface(Typeface.create("sans-serif-black",Typeface.BOLD)); logo.setGravity(Gravity.CENTER); root.addView(logo,new LinearLayout.LayoutParams(-1,54));
        TextView sub=text(subtitle,12,Color.LTGRAY); sub.setGravity(Gravity.CENTER); root.addView(sub,new LinearLayout.LayoutParams(-1,34));
    }
    private LinearLayout.LayoutParams margin() { LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2); p.setMargins(0,8,0,8); return p; }
    private void addInfo(String title,String body) { LinearLayout box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL); box.setPadding(15,12,15,12); box.setBackgroundColor(card); TextView h=text(title,14,white); h.setTypeface(null,Typeface.BOLD); TextView d=text(body,12,Color.LTGRAY); d.setPadding(0,5,0,0); box.addView(h); box.addView(d); root.addView(box,margin()); }
    private void home() {
        setup("VEXORA","BEDROCK EDITION  •  1.26.32");
        addInfo("ANDROID OVERLAY SHELL","Floating menu + interface demo. It does not modify Minecraft.");
        addSection("COMBAT PRACTICE","Mace Helper · timing practice · training notes"); addSection("HUD","Compact HUD mockup · crosshair preview"); addSection("UTILITIES","Session timer · tap counter · notes"); addSection("PERFORMANCE","Safe optimization checklist"); addSection("SETTINGS","Theme · overlay button · about");
        Button overlay=button("Enable floating V button"); overlay.setOnClickListener(v->{ if(Build.VERSION.SDK_INT>=23&&!Settings.canDrawOverlays(this)){ Intent i=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:"+getPackageName())); startActivity(i); } else { startService(new Intent(this,OverlayService.class)); addInfo("Overlay started","Look for the draggable V button over other apps."); }}); root.addView(overlay,margin());
        addInfo("Important","This starter project is a UI/demo shell. KillAura, game memory access and in-game FPS changes are not implemented.");
    }
    private void addSection(String title,String desc) { Button b=button(title+"     ›"); b.setGravity(Gravity.CENTER_VERTICAL); b.setOnClickListener(v->section(title,desc)); root.addView(b,new LinearLayout.LayoutParams(-1,56)); root.addView(new View(this),new LinearLayout.LayoutParams(1,7)); }
    private void section(String title,String desc) {
        setup("VEXORA","BEDROCK EDITION  •  1.26.32"); Button back=button("‹  Back to menu"); back.setOnClickListener(v->home()); root.addView(back,margin()); addInfo(title,desc);
        if(title.equals("COMBAT PRACTICE")){ addInfo("Mace Helper","Practice reminder: watch your fall timing and aim in a private world. No automated attacks."); addToggle("Practice reminder",false); addToggle("Show timing hint (demo)",false); }
        else if(title.equals("HUD")){ addToggle("Show FPS label (demo)",false); addToggle("Show crosshair preview",true); addToggle("Compact layout",true); }
        else if(title.equals("UTILITIES")){ addToggle("Session timer (demo)",false); addToggle("Tap counter (demo)",false); addInfo("Notes","This starter has no persistent notes storage."); }
        else if(title.equals("PERFORMANCE")){ addInfo("Safe checklist","Close unused apps · keep free storage available · lower Minecraft render distance · disable heavy resource packs · keep the phone cool."); addToggle("Checklist completed (local UI only)",false); }
        else { addInfo("Appearance","Black / white / graphite minimalist interface. No purple accent on the main screen."); addInfo("About","VEXORA starter shell • Android 6+ • Minecraft Bedrock 1.26.32 UI concept."); }
    }
    private void addToggle(String label,boolean initial){ android.widget.Switch sw=new android.widget.Switch(this); sw.setText(label); sw.setTextColor(white); sw.setChecked(initial); sw.setPadding(12,10,12,10); sw.setBackgroundColor(card); root.addView(sw,margin()); }
}
