package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChatMain extends JFrame implements ActionListener, KeyListener
{

    public static void main(String[] args) throws Exception
    {
        new ServerChatMain();
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
        BufferedWriter bw = null;
        // /行为
    public ServerChatMain()  //这个地方不能加void，构造函数前面不能加任何返回符类型。
    {
        jta = new JTextArea();
        jsp = new JScrollPane(jta);
        jp = new JPanel();
        jtf = new JTextField(20);
        jb = new JButton("发送");
        jta.setEditable(false);
        jp.add(jtf);
        jp.add(jb);
        this.add(jsp, BorderLayout.CENTER);
        this.add(jp,BorderLayout.SOUTH);
        this.setTitle("QQ聊天 服务端");
        this.setSize(500,500);
        this.setLocation(300,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //窗口关闭时程序停止
        this.setVisible(true);

        //TCP通信
        jb.addActionListener(this);
        jtf.addKeyListener(this);
        try {
            ServerSocket ss = new ServerSocket(20000);
            Socket s = ss.accept();
            InputStream is = s.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); //BufferedReader需要在学习一下
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                jta.append(line + System.lineSeparator());
            }
            ss.close();
        }catch (Exception e1){
            System.out.println("报错了");
        }
        }


    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        SendMessage();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode()==KeyEvent.VK_ENTER){
            SendMessage();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
    private void SendMessage(){
        String text = jtf.getText();
        text = "服务端对客户端说："+text;
        jta.append(text+"\n");
        try {
            bw.write(text);
            bw.newLine();
            bw.flush();
            jtf.setText("");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("报错了");
        }
    }
}
