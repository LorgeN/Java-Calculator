package com.lorgen.calculator.manager;

import com.lorgen.calculator.objects.Delimiters;
import com.lorgen.calculator.objects.Delimiters.Delimiter;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ComponentManager {
    @Getter private List<String> delimiters;
    @Getter private Pattern pattern;

    public ComponentManager() {
        this.delimiters = Arrays.stream(Delimiters.values()).map(Delimiter::getRegex).collect(Collectors.toList());
        StringBuilder patternString = new StringBuilder();
        this.delimiters.forEach(str -> patternString.append("|" + str));
        this.pattern = Pattern.compile(patternString.toString().substring(1));
    }
}
