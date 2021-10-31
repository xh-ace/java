package com.company;

import com.sun.source.tree.CatchTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author xuhao
 * @creat 2021-10-30-9:22
 */
public class ClientChatMain extends JFrame implements ActionListener, KeyListener {

    public static void main(String[] args) throws  Exception
    {
        new ClientChatMain();
    }

    // 属性
    //文本域
    private JTextArea jta;
    //jta.setEditable(false);
    //滚动条
    private JScrollPane jsp;
    //面板
    private JPanel jp;
    //文本框
    private JTextField jtf;
    //按钮
    private JButton jb;
    private BufferedWriter bw ;
    // /行为
    public ClientChatMain() throws Exception
    {  //这个地方加个void就没有图形界面了，
        jta = new JTextArea();
        jsp = new JScrollPane(jta);
        jp = new JPanel();
        jtf = new JTextField(20);
        jb = new JButton("发送");
        jp.add(jtf);
        jp.add(jb); //文本域和滚动条不需要添加到面板中吗？
        this.add(jsp, BorderLayout.CENTER);
        this.add(jp,BorderLayout.SOUTH);
        this.setTitle("QQ聊天 客户端");
        this.setSize(500,500);
        this.setLocation(400,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //窗口关闭时程序停止
        this.setVisible(true);

        //TCP通信
            jb.addActionListener(this);
            jtf.addKeyListener(this);
            Socket socket = new Socket("127.0.0.1",20000);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String line =null;
            while((line = br.readLine()) !=null){
    //            System.out.println(line);
                jta.append(line + "\n");
            }


        socket.close();


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        SendMessage();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        SendMessage();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
    private void SendMessage() {
        String text = jtf.getText();
        text = "服务端对客户端说：" + text;
        jta.append(text + "\n");
        try {
            bw.write(text);
            bw.newLine();
            bw.flush();
            jtf.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("报错了");
        }
    }
}
