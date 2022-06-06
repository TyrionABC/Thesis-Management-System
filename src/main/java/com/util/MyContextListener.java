package com.util;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
@Component
public class MyContextListener implements ServletContextListener {
    private SSHConnection conexionssh;
    public MyContextListener() {
        super();
    }
    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // 建立连接
        System.out.println("Context initialized ... !\n\n\n");
        try {
            conexionssh = new SSHConnection();
            conexionssh.SSHConnection();
            System.out.println("\n\n\n成功建立SSH连接！\n\n\n");
        } catch (Throwable e) {
            System.out.println("\n\n\nSSH连接失败！\n\n\n");
            e.printStackTrace(); // error connecting SSH server
        }
    }
    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // 断开连接
        System.out.println("Context destroyed ... !\n\n\n");
        try {
            conexionssh.closeSSH(); // disconnect
            System.out.println("\n\n\n成功断开SSH连接!\n\n\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n\n\n断开SSH连接出错！\n\n\n");
        }
    }
}
