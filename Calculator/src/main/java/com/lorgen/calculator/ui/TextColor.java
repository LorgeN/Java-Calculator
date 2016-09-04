package com.lorgen.calculator.ui;

import org.fusesource.jansi.Ansi;

public class TextColor {
    public static final String RESET = Ansi.ansi().a(Ansi.Attribute.RESET).toString();
    public static final String ITALIC = Ansi.ansi().a(Ansi.Attribute.ITALIC).toString();
    public static final String UNDERLINE = Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString();
    public static final String STRIKETHROUGH = Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString();
    public static final String BOLD = Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString();
    public static final String MAGIC = Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString();
    public static final String BLACK = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString();
    public static final String RED = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString();
    public static final String GREEN = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString();
    public static final String YELLOW = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString();
    public static final String BLUE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString();
    public static final String LIGHT_PURPLE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString();
    public static final String PURPLE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString();
    public static final String CYAN = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString();
    public static final String WHITE = Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString();
}
