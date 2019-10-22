/* -- JFLAP 4.0 --
 *
 * Copyright information:
 *
 * Susan H. Rodger, Thomas Finley
 * Computer Science Department
 * Duke University
 * April 24, 2003
 * Supported by National Science Foundation DUE-9752583.
 *
 * Copyright (c) 2003
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms are permitted
 * provided that the above copyright notice and this paragraph are
 * duplicated in all such forms and that any documentation,
 * advertising materials, and other materials related to such
 * distribution and use acknowledge that the software was developed
 * by the author.  The name of the author may not be used to
 * endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
 
package grammar.parse;

import grammar.Grammar;
import grammar.Production;

import java.util.Stack;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 * This class does LR parsing.  It is a test class only.
 * 
 * @author Thomas Finley
 */

public abstract class LRParser {
    /**
     * This action will perform parsing of a string.
     * @param string the string to parse
     * @param grammar the augmented grammar
     * @param table the parse table
     */
    public static void parse(String string, Grammar grammar,
			     LRParseTable table) {
	string = string+"$";
	int p = 0;
	IntStack stack = new IntStack();
	stack.push(0);
	Production[] productions = grammar.getProductions();
	while (true) {
	    int state = stack.peekInt();
	    String read = ""+string.charAt(p);
	    String entry = table.getValueAt(state, read);	    
	    System.out.println("Action: "+entry);
	    System.out.println("Stack : "+stack);
	    System.out.println("Input : "+string.substring(p));
	    if (entry.length() == 0) {
		// Error!  No derivation.
		System.out.println("Parse error");
		return;
	    } else if (entry.charAt(0) == 's') {
		// Shift!
		stack.push(read); // Push the symbol.
		stack.push(Integer.parseInt(entry.substring(1)));
		p++; // Move to next input symbol.
	    } else if (entry.charAt(0) == 'r') {
		// Reduce!
		int prodNumber = Integer.parseInt(entry.substring(1));
		Production red = productions[prodNumber];
		for (int i=0; i<2*red.getRHS().length(); i++)
		    stack.pop();
		state = stack.peekInt();
		stack.push(red.getLHS());
		stack.push(Integer.parseInt(table.getValueAt
					    (state, red.getLHS())));
		System.out.println(red);
	    } else if (entry.charAt(0) == 'a') {
		System.out.println("Acceptance!");
		return;
	    }
	}
    }

    /**
     * This action will perform parsing of a string.
     * @param string the string to parse
     * @param grammar the augmented grammar
     * @param table the parse table
     * @return the parse tree
     */
    public static TreeModel parseTree(String string, Grammar grammar,
				      LRParseTable table) {
	string = string+"$";
	int p = 0;
	IntStack stack = new IntStack();
	stack.push(0);
	Production[] productions = grammar.getProductions();
	int nodeNum = 0;
	while (true) {
	    int state = stack.peekInt();
	    String read = ""+string.charAt(p);
	    String entry = table.getValueAt(state, read);
	    if (entry.length() == 0) {
		// Error!  No derivation.
		System.out.println("Parse error");
		DefaultMutableTreeNode node = new DefaultMutableTreeNode();
		Object[] elements = stack.toArray();
		for (int i=0; i<elements.length; i++)
		    if (elements[i] instanceof MutableTreeNode)
			node.add((MutableTreeNode)elements[i]);
		return new DefaultTreeModel(node);
	    } else if (entry.charAt(0) == 's') {
		// Shift!
		TreeNode node = new DefaultMutableTreeNode
		    (read, false);
		stack.push(node); // Push the symbol.
		stack.push(Integer.parseInt(entry.substring(1)));
		p++; // Move to next input symbol.
	    } else if (entry.charAt(0) == 'r') {
		// Reduce!
		int prodNumber = Integer.parseInt(entry.substring(1));
		Production red = productions[prodNumber];
		DefaultMutableTreeNode node =
		    new DefaultMutableTreeNode(red.getLHS());
		for (int i=0; i<red.getRHS().length(); i++) {
		    stack.pop(); // Pops the state.
		    MutableTreeNode c = (MutableTreeNode) stack.pop(); 
		    node.insert(c,0);
		    // Pops the symbol.
		}
		state = stack.peekInt();
		stack.push(node);
		stack.push(Integer.parseInt(table.getValueAt
					    (state, red.getLHS())));
		System.out.println(red);
	    } else if (entry.charAt(0) == 'a') {
		System.out.println("Acceptance!");
		stack.pop();
		return new DefaultTreeModel((TreeNode)stack.pop());
	    }
	}
    }

    private static class IntStack extends Stack {
	int push(int item) {
	    push(new Integer(item));
	    return item;
	}
	int popInt() {
	    return ((Integer)pop()).intValue();
	}
	int peekInt() {
	    return ((Integer)peek()).intValue();
	}
    }
}
