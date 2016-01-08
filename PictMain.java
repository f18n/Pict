/*
 *
 * Created on December 26, 2015, 9:12 AM
 */

package com;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author  f18n
 * @version  0.0.1
 * 
 * 
 *  the problem
 *     I not not know how this app make donwload picture online.
 *     this example I make situation Offline
 * 
 */
public class PictMain extends MIDlet implements CommandListener{
    
    private Display dis;
    private Form form;
    private List list;
    private Command exit,go,select,back;
    private Image img1,img2,img3,img4,img5;
    
    private Image im;
    private ImageItem it;
    private Command down;
    private Gauge gg;
    
    public PictMain(){
    
        dis = Display.getDisplay(this);
        situasi = true;
        go = new Command("Ok",Command.OK,2);
        exit = new Command("Exit",Command.EXIT,2);
        try{
            img1 = Image.createImage("/res/1.png");
            img2 = Image.createImage("/res/2.png");
            img3 = Image.createImage("/res/3.png");
            img4 = Image.createImage("/res/4.png");
            img5 = Image.createImage("/res/b.png");
            
        }catch(Exception e){
            System.out.println("Eror at Image "+e);
        }
        Image element[] = {img1,img2,img3,img4,img5};
        String[] ss = {"Poto1","poto2","poto3","poto4","poto5"};
        select = new Command("Open",Command.ITEM,1);
        list = new List("List Picture",List.IMPLICIT,ss,element);
        list.addCommand(exit);list.addCommand(go);
        list.setSelectCommand(select);
        list.setCommandListener(this);
        //-----------
        back = new Command("Back",Command.BACK,2);
        down = new Command("Download",Command.SCREEN,1);
        form = new Form("Pict");
        form.addCommand(back);
        form.addCommand(down);
        form.setCommandListener(this);
    }
    public void startApp() {
        dis.setCurrent(list);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if(c == exit){
            destroyApp(false);
            notifyDestroyed();
        }else if(c == back){
            form.deleteAll();
            dis.setCurrent(list);
        }else if(c == select){
            int i = list.getSelectedIndex();
            im = list.getImage(i);
            it = new ImageItem(null,im,Item.LAYOUT_CENTER,null,Item.PLAIN);
            form.append(it);
            dis.setCurrent(form);
        }else if(c == down){
            //command donwload
            gg = new Gauge("Download...",false,100,0);
            new Thread(new Download()).start();
            batal = new Command("Batal",Command.CANCEL,2);
            form.removeCommand(down);
            form.append(gg);
        }
    }
    
    class Download implements Runnable{
        
        public void run() {
            FileConnection fc = null;
            
            try{
                
                while(gg.getValue() < gg.getMaxValue()){
                    Thread.sleep(500);
                    gg.setValue(gg.getValue() + 1);
                }
                situasi = true;
                StringItem si = new StringItem(null,"Completed");
                form.append(si);
                
            }catch(Exception e){}
        }
        
    }
}
