package com.aibibang.learn.design.patterns.command;

/**
 * Created by td20 on 2018/6/12.
 */
public class AddCommand implements Command {

    private Receiver receiver;

    public AddCommand(Receiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        System.out.println("执行Add");
    }
}