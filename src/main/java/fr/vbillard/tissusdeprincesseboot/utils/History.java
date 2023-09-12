package fr.vbillard.tissusdeprincesseboot.utils;

import fr.vbillard.tissusdeprincesseboot.controller.utils.FxData;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class History {

    private static LinkedList<FxData> full;
    private static final int MAX_ENTRIES = 20;
    private static int step = 0;

    private LinkedList getFull(){
        if (full == null){
            full = new LinkedList<>();
        }
        return full;
    }

    public FxData getPrevious(){
        int index = step;
        if (step < full.size()) step++;
        return full.get(index);
    }

    public FxData getNext(){
        int index = step;
        if (step > 0) step--;
        return full.get(index);
    }

    public void add(FxData data){
        getFull().addFirst(data.getCopy());
        if (full.size() > MAX_ENTRIES){
            full.removeLast();
        }
    }

    public boolean hasNoPrevious (){
        return step == 0;
    }

    public boolean hasNoNext (){
        return step == full.size();
    }

}
