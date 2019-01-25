package com.ray.tech.service.strategy;

import com.ray.tech.service.strategy.character.King;
import com.ray.tech.service.strategy.character.Knight;
import com.ray.tech.service.strategy.character.Queen;
import com.ray.tech.service.strategy.character.Troll;
import org.springframework.stereotype.Service;

@Service
public class AStory {
    public void start() {
        Character king = new King();
        Character knight = new Knight();
        Character queen = new Queen();
        Character troll = new Troll();
        troll.fight();
        System.out.println("to the king");
        queen.fight();
        System.out.println("help the king");
        king.fight();
        System.out.println("with queen");
        knight.fight();
        System.out.println("troll dead");
    }
}
