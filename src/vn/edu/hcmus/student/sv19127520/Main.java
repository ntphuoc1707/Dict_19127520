package vn.edu.hcmus.student.sv19127520;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.Random;

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
    public static int w=200;
    public static int h=200;
    public static void createAndShowMenu(){
        w=f.getWidth();
        h=f.getHeight();
        f.dispose();
        f=new JFrame("Dictionary");
        f.setMinimumSize(new Dimension(500,500));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(w,h));
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

        JButton button2=new JButton("Add word");
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button2);
        p.add(Box.createRigidArea(new Dimension(0,5)));

        JButton button3=new JButton("Edit word");
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button3);
        p.add(Box.createRigidArea(new Dimension(0,5)));

        JButton button4=new JButton("Reset dictionary");
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button4);
        p.add(Box.createRigidArea(new Dimension(0,5)));

//        JButton button5=new JButton("Exit");
//        button5.setAlignmentX(Component.CENTER_ALIGNMENT);
//        p.add(button5);

        JButton button5=new JButton("Slang word for today");
        button5.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(button5);

        ActionListener y= e -> {
            String s=e.getActionCommand();
            if(s.equals("Search")){
                createAndShowSearch();
            }
            else if(s.equals("Add word")){
                createAndShowAddWord();
            }
            else if(s.equals("Edit word")){
                createAndShowEditWord();
            }
            else if(s.equals("Reset dictionary")){
                ResetData();
                SaveData();
                JOptionPane.showMessageDialog(f,"Reset successfully",null,JOptionPane.INFORMATION_MESSAGE);
            }
            else if(s.equals("Exit")){
                SaveData();
                f.dispose();
            }
            else if(s.equals("Slang word for today")){
                JDialog dialog=new JDialog(f,"Slang word for today",true);
                dialog.setLayout(new BorderLayout());
                dialog.setMinimumSize(new Dimension(400,200));
                dialog.setLocation(f.getWidth()/2-100,f.getHeight()/2-100);
                JPanel jPanel=new JPanel();
                jPanel.setLayout(new FlowLayout());
                Random rand=new Random();
                int pos=rand.nextInt(dict.slag.size()-1);
                JLabel label=new JLabel(dict.slag.elementAt(pos)+": ");
                label.setFont(new Font("Verdana",Font.BOLD,20));
                jPanel.add(label);
                JPanel panel=new JPanel();
                panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
                for(int i=0;i<dict.meaning.elementAt(pos).size();i++){
                    JLabel label1=new JLabel("- "+dict.meaning.elementAt(pos).elementAt(i));
                    label1.setFont(new Font("Verdana",Font.PLAIN,17));
                    panel.add(label1);
                }
                jPanel.add(panel);
                dialog.add(jPanel,BorderLayout.CENTER);
                JPanel panel1=new JPanel();
                panel1.setLayout(new FlowLayout());
                JButton button=new JButton("OK");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
                panel1.add(button);
                dialog.add(panel1,BorderLayout.PAGE_END);
                dialog.pack();
                dialog.setVisible(true);
            }
        };
        button1.addActionListener(y);
        button2.addActionListener(y);
        button3.addActionListener(y);
        button4.addActionListener(y);
        button5.addActionListener(y);
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
    public static void createResultForEdit(String input, JPanel mp){
        mp.removeAll();
        mp.repaint();
        mp.setLayout(new BorderLayout());
        input=input.toUpperCase(Locale.ROOT);
        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout());
        Vector<String> result=new Vector<>();
        boolean have=false;
        int pos=0;
        for (int i = 0; i < dict.slag.size(); i++) {
            if (dict.slag.elementAt(i).equals(input)) {
                result=dict.meaning.elementAt(i);
                pos=i;
                have=true;
                break;
            }
        }
        if(have){
            JLabel label=new JLabel(input.toUpperCase(Locale.ROOT)+":    ");
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setAlignmentY(Component.CENTER_ALIGNMENT);
            label.setFont(new Font("Verdana",Font.BOLD,20));
            panel.add(label);
            JPanel panel11=new JPanel();
            panel11.setLayout(new FlowLayout());
            JButton save=new JButton("Save");
            JButton cancel=new JButton("Cancel");
            panel11.add(save);
            panel11.add(cancel);
            JPanel panel1=new JPanel();
            panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
            JTextArea[] textFields=new JTextArea[result.size()];
            for(int i=0;i<result.size();i++){
                textFields[i]=new JTextArea();
                textFields[i].setFont(new Font("Verdana",Font.PLAIN,15));
                textFields[i].setText(result.elementAt(i));
                textFields[i].setAlignmentX(Component.CENTER_ALIGNMENT);
                textFields[i].setBounds(100,100,1000,23);
                textFields[i].setLineWrap(true);
                JScrollPane scrollPane=new JScrollPane(textFields[i]);
                if(result.elementAt(i).length()>700)
                    scrollPane.setPreferredSize(new Dimension(1000,150));
                else
                    if(result.elementAt(i).length()<=100)
                        scrollPane.setPreferredSize(new Dimension(result.elementAt(i).length()*10,23));
                    else
                        scrollPane.setPreferredSize(new Dimension(1000,23+(result.elementAt(i).length()/100)*23));
                Vector<String> finalResult = result;
                int finalI = i;
                textFields[i].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        super.keyTyped(e);
                        if (textFields[finalI].getText().length() >= 700)
                            scrollPane.setPreferredSize(new Dimension(1000, 150));
                        else{
                            scrollPane.setPreferredSize(new Dimension(1000,23+(textFields[finalI].getText().length()/100)*23 ));
                        }
                        mp.remove(panel11);
                        mp.add(panel11,BorderLayout.CENTER);
                        f.pack();
                    }
                });
                panel1.add(scrollPane);
            }
            int finalPos = pos;
            ActionListener y=new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s=e.getActionCommand();
                    int k=0;
                    if(s.equals("Save")){
                        boolean check=false;
                        for(int i = 0; i<dict.meaning.elementAt(finalPos).size(); i++){
                            if(textFields[i].getText().length()==0) {
                                check = true;
                                break;
                            }
                        }
                        if(check){
                            int r=JOptionPane.showConfirmDialog(f,"If you leave it blank, you will delete its meaning. If the word has no meaning, it will be deleted"+'\n'+"Do you wanna save?","Message",JOptionPane.YES_NO_OPTION);

                            if(r==JOptionPane.NO_OPTION){
                                return;
                            }
                        }
                        for(int i = 0; i<dict.meaning.elementAt(finalPos).size(); i++){
                            if(textFields[i].getText().length()==0) {
                                dict.meaning.elementAt(finalPos).remove(k);
                            }
                            else {
                                dict.meaning.elementAt(finalPos).setElementAt(textFields[i].getText(), k);
                                k++;
                            }
                        }
                        if(dict.meaning.elementAt(finalPos).size()==0) {
                            dict.slag.remove(finalPos);
                            dict.meaning.remove(finalPos);
                        }
                        SaveData();
                        createAndShowEditWord();
                    }
                    else if(s.equals("Cancel")){
                        mp.removeAll();
                        mp.repaint();
                        f.pack();
                    }
                }
            };
            save.addActionListener(y);
            cancel.addActionListener(y);
            panel.add(panel1);
            mp.add(panel,BorderLayout.PAGE_START);
            f.pack();
            return;
        }
        JLabel label=new JLabel("Don't have");
        label.setFont(new Font("Verdana",Font.ITALIC,13));
        mp.setLayout(new FlowLayout());
        mp.add(label);
    }
    public static void createAndShowEditWord(){
        w=f.getWidth();
        h=f.getHeight();
        f.dispose();
        f=new JFrame("Edit word");
        f.setPreferredSize(new Dimension(w,h));
        f.setMinimumSize(new Dimension(500,100));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        JPanel p=new JPanel();
        p.setLayout(new FlowLayout());
        p.add(new JLabel("Slang word: "));
        JTextField textField=new JTextField("",15);
        JButton button=new JButton("Find");
        p.add(textField);
        p.add(button);
        JButton button1=new JButton("Return");
        p.add(button1);
        JPanel k=new JPanel();
        ActionListener y=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=e.getActionCommand();
                if(s.equals("Find")){
                    createResultForEdit(textField.getText(),k);
                    textField.setText("");
                    f.pack();
                }
                else if(s.equals("Return")){
                    createAndShowMenu();
                }
            }
        };
        button.addActionListener(y);
        button1.addActionListener(y);
        f.add(p,BorderLayout.PAGE_START);
        f.add(k,BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);

    }
    public static void createAndShowAddWord(){
        w=f.getWidth();
        h=f.getHeight();
        f.dispose();
        f=new JFrame("Add slang word");
        f.setPreferredSize(new Dimension(w,h));
        JFrame.setDefaultLookAndFeelDecorated(true);
        f.setMinimumSize(new Dimension(850,250));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        JPanel panel=new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel slang=new JPanel();
        slang.setLayout(new FlowLayout());
        JLabel l=new JLabel("Slang word:");
        l.setFont(new Font("Verdana",Font.PLAIN,20));
        slang.add(l);
        JTextArea _slang=new JTextArea();
        _slang.setFont(new Font("Verdana", Font.PLAIN,15));
        _slang.setAlignmentX(Component.CENTER_ALIGNMENT);
        _slang.setBounds(100,100,250,200);
        _slang.setLineWrap(true);
        //_slang.setMinimumSize(new Dimension(200,200));
        JScrollPane scrollPane=new JScrollPane(_slang);
        scrollPane.setPreferredSize(new Dimension(200,50));
        slang.add(scrollPane);

        JPanel defi=new JPanel();
        defi.setLayout(new FlowLayout());
        JLabel l1=new JLabel("Definition:");
        l1.setFont(new Font("Verdana",Font.PLAIN,20));
        defi.add(l1);
        JTextArea _defi=new JTextArea();
        _defi.setFont(new Font("Verdana", Font.PLAIN,15));
        _defi.setAlignmentX(Component.CENTER_ALIGNMENT);
        _defi.setBounds(100,100,250,200);
        _defi.setLineWrap(true);
        //_defi.setMinimumSize(new Dimension(100,100));
        JScrollPane pane=new JScrollPane(_defi);
        pane.setPreferredSize(new Dimension(300,150));
        defi.add(pane);

        JButton button=new JButton("ADD");

        panel.add(slang);
        panel.add(defi);
        panel.add(button);

        ActionListener y=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=e.getActionCommand();
                if(s.equals("ADD")){
                    boolean check=false;
                    String input=_slang.getText().toUpperCase(Locale.ROOT);
                    for (int i = 0; i < dict.slag.size(); i++) {
                        if (dict.slag.elementAt(i).equals(input)) {
                            JButton dup=new JButton("Duplicate");
                            JButton ovw=new JButton("Overwrite");
                            JButton cancel=new JButton("Cancel");
                            check=true;
                            JDialog dialog=new JDialog(f,"Message",true);
                            dialog.setLayout(new BorderLayout());
                            dialog.setSize(new Dimension(200,200));
                            dialog.setLocation((f.getWidth()-dialog.getWidth())/2,(f.getHeight()-dialog.getHeight())/2);
                            JPanel p1=new JPanel(new FlowLayout());
                            JLabel label=new JLabel("Slang word already exists");
                            label.setFont(new Font("Verdana",Font.BOLD,15));
                            label.setAlignmentX(Component.CENTER_ALIGNMENT);
                            p1.add(label);
                            dialog.add(p1,BorderLayout.PAGE_START);
                            JLabel label1=new JLabel("What do you want?");
                            label1.setFont(new Font("Verdana",Font.PLAIN,10));
                            label1.setAlignmentX(Component.CENTER_ALIGNMENT);
                            JPanel p2=new JPanel();
                            p2.setLayout(new FlowLayout());
                            p2.add(label1);
                            dialog.add(p2,BorderLayout.CENTER);
                            JPanel panel1=new JPanel();
                            panel1.setLayout(new FlowLayout());
                            panel1.add(dup);
                            panel1.add(ovw);
                            panel1.add(cancel);
                            dialog.add(panel1,BorderLayout.PAGE_END);
                            int finalI = i;
                            ActionListener x=new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String str=e.getActionCommand();
                                    if(str.equals("Duplicate")){
                                        dict.meaning.elementAt(finalI).add(_defi.getText());
                                        SaveData();
                                        dialog.dispose();
                                        JOptionPane.showMessageDialog(f,"Successful",null,JOptionPane.INFORMATION_MESSAGE);
                                        _slang.setText("");
                                        _defi.setText("");
                                    }
                                    if(str.equals("Overwrite")){
                                        Vector<String> t=new Vector<>();
                                        t.add(_defi.getText());
                                        dict.meaning.setElementAt(t, finalI);
                                        SaveData();
                                        dialog.dispose();
                                        JOptionPane.showMessageDialog(f,"Successful",null,JOptionPane.INFORMATION_MESSAGE);
                                        _slang.setText("");
                                        _defi.setText("");
                                    }
                                    if(str.equals("Cancel")){
                                        dialog.dispose();
                                    }
                                }
                            };
                            dup.addActionListener(x);
                            ovw.addActionListener(x);
                            cancel.addActionListener(x);
                            dialog.pack();
                            dialog.setVisible(true);
                        }
                    }
                    if(!check) {
                        dict.slag.add(_slang.getText());
                        Vector<String> vector = new Vector<>();
                        vector.add(_defi.getText());
                        dict.meaning.add(vector);
                        SaveData();
                        JOptionPane.showMessageDialog(f, "Add word successfully", null, JOptionPane.INFORMATION_MESSAGE);
                        _slang.setText("");
                        _defi.setText("");
                    }
                }
                if(s.equals("Return")){
                    createAndShowMenu();
                }
            }
        };
        JPanel middle=new JPanel();
        SpringLayout layout=new SpringLayout();
        middle.setLayout(layout);
        middle.add(panel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER,panel,0,SpringLayout.HORIZONTAL_CENTER,middle);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER,panel,0,SpringLayout.VERTICAL_CENTER,middle);
        f.add(middle,BorderLayout.CENTER);
        JButton button1=new JButton("Return");
        JPanel panel1=new JPanel();
        panel1.setLayout(new FlowLayout());
        panel1.add(button1);

        button.addActionListener(y);
        button1.addActionListener(y);
        f.add(panel1,BorderLayout.PAGE_END);
        f.pack();
        f.setVisible(true);
    }
    public static void createResultForSearch(String input, JPanel panel){
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
                if(words>40) {
                    panel.setPreferredSize(new Dimension(700, 700));
                    if(f.getWidth()<1300||f.getHeight()<800)
                        f.setPreferredSize(new Dimension(1300,800));
                    else{
                        f.setPreferredSize(new Dimension(f.getWidth(),f.getHeight()));
                    }
                }
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
        w=f.getWidth();
        h=f.getHeight();
        f.dispose();
        f=new JFrame("Search");
        f.setPreferredSize(new Dimension(w,h));
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
                        createResultForSearch(textField.getText().replace("\n",""),defi);
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
                    createResultForSearch(textField.getText(),defi);
                }
            }
            if(s.equals("History")){
                JList<String> list=new JList(history.toArray());
                JScrollPane pane1=new JScrollPane(list);
                JDialog dialog=new JDialog(f,"Searched Slang Word History",true);
                dialog.setLayout(new BorderLayout());
                dialog.setMinimumSize(new Dimension(400,200));
                dialog.setLocation(f.getWidth()/2-100,f.getHeight()/2-100);
                dialog.add(pane1,BorderLayout.CENTER);
                JPanel panel=new JPanel();
                panel.setLayout(new FlowLayout());
                panel.add(button);
                JButton button1=new JButton("Clear history");
                button1.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(button1);
                dialog.add(panel,BorderLayout.PAGE_END);
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        history=new Vector<>();
                        try {
                            File file=new File("history.txt");
                            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
                            bw.write("");
                            bw.close();
                        }
                        catch (Exception exception){
                            exception.printStackTrace();
                        }
                        dialog.dispose();
                    }
                });
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
    public static void SaveData(){
        try{
            File file=new File("slang_current.txt");
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            for(int i=0;i<dict.slag.size();i++){
                String str=dict.slag.elementAt(i)+"`";
                for(int j=0;j<dict.meaning.elementAt(i).size();j++) {
                    if(j==dict.meaning.elementAt(i).size()-1){
                        str+=dict.meaning.elementAt(i).elementAt(j);
                    }
                    else{
                        str+=dict.meaning.elementAt(i).elementAt(j)+"|";
                    }
                }
                bw.write(str+"\n");
            }
            file=new File("history.txt");
            bw=new BufferedWriter(new FileWriter(file));
            for(int i=0;i<history.size();i++){
                bw.write(history.elementAt(i)+"\n");
            }
            bw.close();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static void ResetData(){
        try{
            dict=new SDict();
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
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static void LoadData(){
        try{
            dict=new SDict();
            File file = new File("slang_current.txt");
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
    }
}
