package com.aibibang.learn.design.patterns.command;

/**
 * Created by td20 on 2018/6/12.
 * 命令模式的目的就是达到命令的发出者和执行者之间解耦，实现请求和执行分开
 * 详细见https://www.oodesign.com/command-pattern.html
 */
public class TestApp {

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command addCommand = new AddCommand(receiver);
        Invoke invoke = new Invoke();
        invoke.action(addCommand);
    }
}
