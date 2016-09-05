package com.lorgen.calculator.ui;

import java.io.IOException;

import com.lorgen.calculator.Calculator;
import jline.console.ConsoleReader;
import lombok.Getter;
import org.fusesource.jansi.Ansi;

public class ConsoleHandler {
    private ConsoleReader reader;

    public ConsoleHandler() {
        try {
            this.reader = new ConsoleReader();
            Thread thread = new Thread(() -> {
                try {
                    String line;
                    while ((line = this.reader.readLine()) != null)
                        if (!Calculator.getCommandManager().invokeCommand(line))
                            Calculator.getConsole().err("Command not found! Type \"help\" for help.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            thread.setName("Console input thread");
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(String text) {
        this.println(TextColor.GREEN + "[INFO] " + TextColor.RESET + text + TextColor.RESET);
    }

    public void err(String text) {
        this.println(TextColor.RED + "[ERROR] " + TextColor.RESET + text + TextColor.RESET);
    }

    public void warn(String text) {
        this.println(TextColor.PURPLE + "[WARNING] " + TextColor.RESET + text + TextColor.RESET);
    }

    public void println(String str) {
        try {
            this.reader.println(Ansi.ansi().eraseLine(Ansi.Erase.ALL).toString() + ConsoleReader.RESET_LINE + str + TextColor.RESET);
            this.reader.drawLine();
            this.reader.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
