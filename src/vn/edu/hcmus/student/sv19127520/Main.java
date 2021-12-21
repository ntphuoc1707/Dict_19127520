package vn.edu.hcmus.student.sv19127520;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

class SDict{
    Vector<String> slag;
    Vector<Vector<String>> meaning;
    SDict(){
        slag=new Vector<>();
        meaning=new Vector<>();
    }
    public void add(String s,String m){
        for(int i=0;i<slag.size();i++){
            if(slag.elementAt(i).equals(s)){
                for(int j=0;j<meaning.elementAt(i).size();j++)
                    if(meaning.elementAt(i).elementAt(j).equals(m))
                        return;
                meaning.elementAt(i).add(m);
                return;
            }
        }
        slag.add(s);
        Vector<String> t=new Vector<>();
        t.add(m);
        meaning.add(t);
    }
    public void show(){
        for(int i=0;i<slag.size();i++) {
            System.out.print(slag.elementAt(i) + ": ");
            for (int j = 0; j < meaning.elementAt(i).size(); j++)
                System.out.print(meaning.elementAt(i).elementAt(j) + ", ");
            System.out.println();
        }
    }
}
public class Main {
    public static SDict dict=new SDict();
    static JFrame f=new JFrame("Dictionary");
    public static void createAndShowMenu(){
        f.dispose();
        f=new JFrame("Dictionary");
        f.setMinimumSize(new Dimension(500,500));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel p=new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setAlignmentY(Component.CENTER_ALIGNMENT);
        JLabel j=new JLabel("SLANG DICTIONARY");
        j.setFont(new Font("Verdana",Font.BOLD,30));
        j.setForeground(Color.red);
        j.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(j);
        p.add(Box.createRigidArea(new Dimension(0,100)));
       // p.setBackground(Color.getHSBColor(0.5f,0.591f,0.922f));


        JButton button1=new JButton("Search");
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button1);
        p.add(Box.createRigidArea(new Dimension(0,5)));

//        JButton button2=new JButton("Add word");
//        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
//        p.add(button2);
//        p.add(Box.createRigidArea(new Dimension(0,5)));
//
//        JButton button3=new JButton("Edit word");
//        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
//        p.add(button3);
//        p.add(Box.createRigidArea(new Dimension(0,5)));
//
//        JButton button4=new JButton("Delete word");
//        button4.setAlignmentX(Component.CENTER_ALIGNMENT);
//        p.add(button4);
//        p.add(Box.createRigidArea(new Dimension(0,5)));

        JButton button5=new JButton("Exit");
        button5.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button5);

        ActionListener y= e -> {
            String s=e.getActionCommand();
            if(s.equals("Search")){
                createAndShowSearch();
            }
            else if(s.equals("Add Word")){

            }
        };

        button1.addActionListener(y);
//        button2.addActionListener(y);
//        button3.addActionListener(y);
//        button4.addActionListener(y);
//        button5.addActionListener(y);
        SpringLayout layout=new SpringLayout();
        JPanel middle=new JPanel();
        middle.setLayout(layout);
        middle.add(p);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,p,0,SpringLayout.HORIZONTAL_CENTER,middle);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,p,0,SpringLayout.VERTICAL_CENTER,middle);
        f.add(middle);
        f.pack();
        f.setVisible(true);

    }

    public static void createResult(String input, JPanel panel){
        panel.removeAll();
        panel.repaint();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setMinimumSize(new Dimension(200,200));
        panel.setBackground(Color.getHSBColor(0f,0.05f,0.859f));
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(Color.getHSBColor(0f,0.05f,0.859f));
        boolean have=false;
        int max=0;
        int words=0;
        Set<String> x=new HashSet<>();
        Vector<String> s=new Vector<>();
        if(status_search.equals("slang")) {
            input=input.toUpperCase(Locale.ROOT);
            for (int i = 0; i < dict.slag.size(); i++) {
                if (dict.slag.elementAt(i).equals(input)) {
                    have = true;
                    for (int j = 0; j < dict.meaning.elementAt(i).size(); j++) {
                        panel.add(new JLabel(dict.meaning.elementAt(i).elementAt(j)));
                        words++;
                        if(max<dict.meaning.elementAt(i).elementAt(j).length())
                            max=dict.meaning.elementAt(i).elementAt(j).length();
                    }
                }
            }
        }
        else{
            input=input.toLowerCase(Locale.ROOT);
            String[] t=input.split(" ");
            for(int j=0;j<dict.meaning.size();j++){
                for(int k=0;k<dict.meaning.elementAt(j).size();k++){
                    if(dict.meaning.elementAt(j).elementAt(k).toLowerCase(Locale.ROOT).equals(input)) {
                        x.add(dict.slag.elementAt(j) + "  :  " + dict.meaning.elementAt(j).elementAt(k).toUpperCase(Locale.ROOT));
                        have = true;
                        words++;
                        if(max<dict.meaning.elementAt(j).elementAt(k).length())
                            max=dict.meaning.elementAt(j).elementAt(k).length();
                    }
                    else {
                        String[] p=dict.meaning.elementAt(j).elementAt(k).toLowerCase(Locale.ROOT).split(" ");
                        Vector<String> vector= new Vector<>(Arrays.asList(p));
                        String temp=dict.meaning.elementAt(j).elementAt(k).toLowerCase(Locale.ROOT);
                        boolean have1=false;
                        for (String value : t) {
                            if (vector.contains(value)) {
                                temp = temp.replace(value, value.toUpperCase(Locale.ROOT));
                                have1 = true;
                                have = true;
                                if (max < dict.meaning.elementAt(j).elementAt(k).length())
                                    max = dict.meaning.elementAt(j).elementAt(k).length();
                            }
                        }
                        if(have1) {
                            words++;
                            s.add(dict.slag.elementAt(j) + "  :  " + temp);
                        }
                    }
                }
            }
            if(!have) {
                panel.setPreferredSize(new Dimension(200,200));
                panel.add(new JLabel("!!! No result !!!"));
            }
            else {
                Vector<String> str = new Vector<>(x);
                for (String i : s) {
                    System.out.println(i);
                    str.add(i);
//                panel1.add(new JLabel(i));
                }
                JList l = new JList(str.toArray());
                JScrollPane pane = new JScrollPane(l);
                System.out.println("Max: "+max);
                if(words>40)
                    panel.setPreferredSize(new Dimension(max*10,700));
                else{
                    panel.setPreferredSize(new Dimension(max*10,words*20));
                }
                panel.add(pane);
            }
        }
        if(!have) {
            panel.setPreferredSize(new Dimension(200,200));
            panel.add(new JLabel("!!! No result !!!"));
        }

    }
    static Vector<String> history=new Vector<>();
    public static String status_search="slang";
    public static void createAndShowSearch(){

        f.dispose();
        f=new JFrame("Search");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(700,300));
        f.setLayout(new FlowLayout());


        JPanel left=new JPanel();
        left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        JLabel _left=new JLabel("Slang word");
        _left.setFont(new Font("Verdana",Font.ITALIC,20));
        _left.setAlignmentX(Component.CENTER_ALIGNMENT);
        _left.setPreferredSize(new Dimension(200,50));
        left.add(_left);
        JTextArea textField=new JTextArea();
        textField.setFont(new Font("Verdana", Font.PLAIN,15));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setBounds(100,100,250,200);
        textField.setLineWrap(true);
        textField.setMinimumSize(new Dimension(100,100));
        JScrollPane pane=new JScrollPane(textField);
        pane.setPreferredSize(new Dimension(200,200));
        left.add(pane);

        JPanel center=new JPanel();
        center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
        JButton swtch=new JButton("Switch");
        swtch.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton find=new JButton("Find");
        find.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton his=new JButton("History");
        his.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton retrn=new JButton("Return");
        retrn.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(Box.createRigidArea(new Dimension(0,50)));
        center.add(swtch);
        center.add(Box.createRigidArea(new Dimension(0,50)));
        center.add(find);
        center.add(Box.createRigidArea(new Dimension(0,50)));
        center.add(his);
        center.add(Box.createRigidArea(new Dimension(0,50)));
        center.add(retrn);

        JPanel right=new JPanel();
        right.setLayout(new BorderLayout());
        JLabel _right=new JLabel("Definition");
        _right.setAlignmentX(Component.CENTER_ALIGNMENT);
        _right.setFont(new Font("Verdana",Font.ITALIC,20));
        _right.setPreferredSize(new Dimension(200,50));
        right.add(_right,BorderLayout.NORTH);
        JPanel defi=new JPanel();
        defi.setAlignmentX(Component.CENTER_ALIGNMENT);
        defi.setPreferredSize(new Dimension(400,200));
        defi.setMinimumSize(new Dimension(200,200));
        defi.setBackground(Color.getHSBColor(0f,0.05f,0.859f));
        right.add(defi,BorderLayout.CENTER);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if (textField.getText().length() > 0) {
                        try{
                            history.add(textField.getText());
                            File file=new File("history.txt");
                            FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
                            BufferedWriter bw=new BufferedWriter(fw);
                            bw.write(textField.getText()+"\n");
                            bw.close();
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                        createResult(textField.getText().replace("\n",""),defi);
                        textField.setText("");
                        f.pack();
                    }
                }
            }
        });


        JButton button=new JButton("Close");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        ActionListener y= e -> {
            String s=e.getActionCommand();
            if(s.equals("Switch")){
                textField.setText("");
                defi.removeAll();
                defi.repaint();
                String t=_left.getText();
                if(t.equals("Slang word")){
                    pane.setPreferredSize(new Dimension(400,200));
                    defi.setPreferredSize(new Dimension(200,200));
                    status_search="defi";
                    _left.setText("Definition");
                    _right.setText("Slang word");
                }
                else{
                    status_search="slang";
                    pane.setPreferredSize(new Dimension(200,200));
                    defi.setPreferredSize(new Dimension(400,200));
                    _left.setText("Slang word");
                    _right.setText("Definition");
                }
            }
            if(s.equals("Find")){
                if(textField.getText().length()>0) {
                    try{
                        if(status_search.equals("slang")){
                            history.add(textField.getText());
                            File file=new File("history.txt");
                            FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
                            BufferedWriter bw=new BufferedWriter(fw);
                            bw.write(textField.getText()+"\n");
                            bw.close();
                        }
                    }
                    catch(Exception exception){
                        exception.printStackTrace();
                    }
                    createResult(textField.getText(),defi);
                }
            }
            if(s.equals("History")){
                JList<String> list=new JList(history.toArray());
                JScrollPane pane1=new JScrollPane(list);

                JDialog dialog=new JDialog(f,"History",true);
                dialog.setLayout(new BorderLayout());
                Dimension Size = Toolkit.getDefaultToolkit().getScreenSize();
                System.out.println("X: "+f.getWidth()+", Y: "+f.getHeight());
                dialog.setLocation(f.getWidth()/2-100,f.getHeight()/2-100);
                dialog.add(pane1,BorderLayout.CENTER);
                JPanel panel=new JPanel();
                panel.setLayout(new FlowLayout());
                panel.add(button);
                dialog.add(panel,BorderLayout.PAGE_END);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
                dialog.setSize(new Dimension(200,200));
                dialog.setVisible(true);
            }

            if(s.equals("Return")){
                createAndShowMenu();
            }
            f.pack();
        };

        swtch.addActionListener(y);
        find.addActionListener(y);
        retrn.addActionListener(y);
        his.addActionListener(y);
        f.add(left);
        f.add(center);
        f.add(right);

        f.pack();
        f.setVisible(true);
    }

    public static void LoadData(){
        try{
            File file = new File("slang.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String current="";
            String line = reader.readLine();
            line = reader.readLine();
            while (line != null) {
                String[] t=line.split("`");
                if(t.length==2){
                    String[] o=t[1].split("\\| ");
                    if(o.length==1) o=t[1].split(" \\|");
                    if(o.length==1) o=t[1].split("\\|");
                    for (String s : o) dict.add(t[0], s);
                }
                else{
                    String[] o=line.split("\\| ");
                    if(o.length==1) o=line.split(" \\|");
                    if(o.length==1) o=line.split("\\|");
                    for (String s : o) dict.add(current, s);
                }
                current=t[0];
                line = reader.readLine();
            }
            file=new File("history.txt");
            reader=new BufferedReader(new FileReader(file));
            line=reader.readLine();
            while(line!=null){
                if(line.length()>0)
                    history.add(line);
                line=reader.readLine();
            }
            dict.show();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static void main(String[] args) {
	LoadData();
    JFrame.setDefaultLookAndFeelDecorated(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    createAndShowMenu();
    try{


    }catch (Exception exception){
        exception.printStackTrace();
    }
    }
}
