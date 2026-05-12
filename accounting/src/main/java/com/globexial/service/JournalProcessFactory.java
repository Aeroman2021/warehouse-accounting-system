package com.globexial.service;

import com.globexial.model.enums.MovementType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JournalProcessFactory {
    private final Map<MovementType,JournalProcessor> processors;

    public JournalProcessFactory(List<JournalProcessor> processorList) {
        this.processors = processorList.stream()
                .collect(Collectors.toMap(
                        JournalProcessor::getType, Function.identity()
                ));
    }

    public JournalProcessor getProcessor(MovementType type){
        return processors.get(type);
    }
}
