package objects;

import interfaces.ChatClientInterface;

public class Chatter {
    public String name;
    public ChatClientInterface client;

    //constructor
    public Chatter(String name, ChatClientInterface client){
        this.name = name;
        this.client = client;
    }

    //getters and setters
    public String getName(){
        return name;
    }
    public ChatClientInterface getClient(){
        return client;
    }

}
