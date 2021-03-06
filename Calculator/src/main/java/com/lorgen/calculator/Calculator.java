package com.lorgen.calculator;

import com.lorgen.calculator.manager.AssumptionManager;
import com.lorgen.calculator.manager.ComponentManager;
import com.lorgen.calculator.evaluators.Evaluator;
import com.lorgen.calculator.manager.FunctionManager;
import com.lorgen.calculator.ui.CommandManager;
import com.lorgen.calculator.ui.ConsoleHandler;
import lombok.Getter;

public class Calculator {
    @Getter private static CommandManager commandManager = new CommandManager();
    @Getter private static ConsoleHandler console = new ConsoleHandler();
    @Getter private static Evaluator evaluator = new Evaluator();
    @Getter private static ComponentManager componentManager = new ComponentManager();
    @Getter private static FunctionManager functionManager = new FunctionManager();
    @Getter private static AssumptionManager assumptionManager = new AssumptionManager();
}
