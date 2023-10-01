package br.com.btg.desafioengenharia.utils;

import lombok.Getter;

import java.io.PrintStream;

@Getter
public class LoggerUtils {

    public static final String ERROR = "error";
    public static final String INFO = "info";

    public static void log(String level, LoggerResponse loggerResponse) {
        loggerResponse.setLevel(level);
        PrintStream printStream = getTargetPrintStream();
        printStream.println(loggerResponse.toJson());
        printStream.flush();
    }

    private static PrintStream getTargetPrintStream() {
        return System.out;
    }

}
