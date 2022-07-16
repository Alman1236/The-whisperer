package gameElements;
import java.util.ArrayList;

import types.Commands;

public class Command {
	
	private Commands identifier;
    private ArrayList<String> aliases;
    
    public ArrayList<String> GetAliases() {
    	return aliases;
    }
    
    public Commands getIdentifier() {
		return identifier;
	}
    
    public Command(Commands identifier ,ArrayList<String> aliases) {
    	this.identifier = identifier;
    	this.aliases = aliases;
    } 
}