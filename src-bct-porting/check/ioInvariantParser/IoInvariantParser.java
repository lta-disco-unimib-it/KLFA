package check.ioInvariantParser;

import sjm.parse.Alternation;
import sjm.parse.Assembler;
import sjm.parse.Assembly;
import sjm.parse.Empty;
import sjm.parse.Parser;
import sjm.parse.Repetition;
import sjm.parse.Sequence;
import sjm.parse.tokens.Literal;
import sjm.parse.tokens.Num;
import sjm.parse.tokens.QuotedString;
import sjm.parse.tokens.Symbol;
import sjm.parse.tokens.TokenAssembly;
import sjm.parse.tokens.Tokenizer;
import sjm.parse.tokens.Word;
import sjm.parse.tokens.WordState;

public class IoInvariantParser  {

  private static Parser invariantParser;
  private static String currentExpression=null;
  private static Alternation expr;
  private static Sequence basicExpression;
  private static Sequence implication;

  
  public static String getExpressionUnderEvaluation()  {
	  return currentExpression;
  }
  
  private static Parser fieldLiteral() {
	  return new Word().
      setAssembler(new FieldLiteralAssembler());
  }
  private static Parser methodLiteral() {
    Parser p = new Sequence()
    	.add( new Word() )
      .add(new Symbol('(').discard())
      .add(new Symbol(')').discard());
    p.setAssembler(new MethodLiteralAssembler());
    
    return p;
  }
  private static Parser parameter() {
/*    Parser p = new Sequence()
      .add(new Literal("parameter").discard())
      .add(new Symbol('[').discard())
      .add(new Num())
      .add(new Symbol(']').discard())  
      .add(new Repetition(
         new Sequence()
          .add(new Symbol("[").discard())
          .add(new Num().setAssembler(new ArrayIndexAssembler()))
          .add(new Symbol("]").discard())
         )
       );
       
    p.setAssembler(new ParameterAssembler());
    return p;
*/

    Parser p = new Sequence()
    .add(new Literal("parameter").discard())
    .add(new Symbol('[').discard())
    .add(new Num())
    .add(new Symbol(']').discard()  
    .setAssembler(new ParameterAssembler()))
	.add(new Repetition(
       new Sequence()
        .add(new Symbol("[").discard())
        .add(new Num().setAssembler(new ArrayIndexAssembler()))
        .add(new Symbol("]").discard())
       )
     );
     

  return p;

  }
  private static Parser returnValue() {
    return new Sequence()
      .add(new Literal("returnValue").discard()
        .setAssembler(new ReturnValueAssembler()))
      .add(new Repetition(
         new Sequence()
          .add(new Symbol("[").discard())
          .add(new Num().setAssembler(new ArrayIndexAssembler()))
          .add(new Symbol("]").discard())
         )
       );
      
  }
  private static Parser identifier() {
      return new Sequence()
      .add(new Alternation()
        .add(parameter())
        .add(returnValue())
      )   
      .add(new Repetition(
        new Sequence()
          .add(new Symbol('.').discard())
          .add(new Alternation()
            .add(methodLiteral())	//methods must be before fields because field is identified as Word
            						//so every method signature can be seen as field
            .add(fieldLiteral())
          )
          .add(new Repetition(
             new Sequence()
              .add(new Symbol("[").discard())
              .add(new Num().setAssembler(new ArrayIndexAssembler()))
              .add(new Symbol("]").discard())
             )
           )
        )
      )
      .add(new Alternation()
        .add(new Sequence()
          .add(new Symbol("[").discard())
          .add(new Symbol("]").discard())
        )
        .add(new Empty())
      );
  }
  private static Parser literal() {
    return new Alternation()
      .add(new Num().setAssembler(new NumberAssembler()))
      .add(new QuotedString().setAssembler(new StringAssembler()))
      .add(new Literal("null").setAssembler(new NullAssembler()));
  }
  private static Parser arrayDeclaration() {
    return new Sequence()
      .add(new Symbol('{').discard().setAssembler(new CreateArrayAssembler()))
      .add(literal().setAssembler(new AddArrayElementAssembler()))
      .add(new Repetition(
        new Sequence()
          .add(new Symbol(',').discard())
          .add(literal().setAssembler(new AddArrayElementAssembler()))
      ))
      .add(new Symbol('}').discard().setAssembler(new ArrayCompletedAssembler()));
  }
  private static Parser term() {
    return new Alternation()
      .add(identifier())
      .add(literal())
      .add(arrayDeclaration());
  }  
  private static Parser sizeOperator() {
    return new Sequence()
      .add(new Literal("size").discard())
      .add(new Symbol('(').discard())
      .add(term())
      .add(new Symbol(')').discard())
        .setAssembler(new SizeAssembler());
  }
  private static Parser origOperator() {
    return new Sequence()
      .add(new Literal("orig").discard())
      .add(new Symbol('(').discard())
      .add(term())
      .add(new Symbol(')').discard())
        .setAssembler(new OrigAssembler());
  }
  private static Parser unaryOperator() {
    return new Alternation()
      .add(sizeOperator())
      .add(origOperator());
  }  
  private static Parser operand() {
    return new Alternation()
      .add(unaryOperator())
      .add(term());
      //.add(parenthesis());
  }
  
  
  private static Parser parenthesis() {
	return new Sequence()
	.add(new Symbol("(").discard())
	.add( expression() )
	.add(new Symbol(")").discard());
  }

  private static Parser lexicalClause() {
    return new Repetition(
      new Sequence()
        .add(new Symbol("(").discard())
        .add(new Literal("lexically").discard())
        .add(new Symbol(")").discard())
      );
  }
  private static Parser equalsExpression() {
    Parser p = new Sequence()
      .add(new Symbol("==").discard())
      .add(operand())
      .add(lexicalClause());
    p.setAssembler(new EqualsAssembler());
    return p;
  }  
  private static Parser nonEqualsExpression() {
    Parser p =  new Sequence()
      .add(new Symbol("!=").discard())
      .add(operand())
      .add(lexicalClause())
        .setAssembler(new NonEqualsAssembler());
    
    return p;
  }
  private static Parser lessThanExpression() {
     return new Sequence()
      .add(new Symbol('<').discard())
      .add(operand())
      .add(lexicalClause())
        .setAssembler(new LessThanAssembler());
  }    
  private static Parser greaterThanExpression() {
    return new Sequence()
      .add(new Symbol('>').discard())
      .add(operand())
      .add(lexicalClause())
        .setAssembler(new GreaterThanAssembler());
  }
  private static Parser lessOrEqualThanExpression() {
    return new Sequence()
      .add(new Symbol("<=").discard())
      .add(operand())
      .add(lexicalClause())
        .setAssembler(new LessOrEqualThanAssembler());
  }
  private static Parser greaterOrEqualThanExpression() {
    return new Sequence()
      .add(new Symbol(">=").discard())
      .add(operand())
      .add(lexicalClause())
        .setAssembler(new GreaterOrEqualThanAssembler());
  }
  private static Parser seqFloatEqual() {
    return new Sequence()  
      .add(new Symbol("==").discard())
      .add(operand())
      .setAssembler(new SeqFloatEqualAssembler());    
  }
  private static Parser seqFloatNonEqual() {
    return new Sequence()  
      .add(new Symbol("!=").discard())
      .add(operand())
      .setAssembler(new SeqFloatNonEqualAssembler());    
  }  
  private static Parser seqFloatGreaterThan() {
    return new Sequence()  
      .add(new Symbol('>').discard())
      .add(operand())
      .setAssembler(new SeqFloatGreaterThanAssembler());    
  }  
  private static Parser seqFloatGreaterOrEqualThan() {
    return new Sequence()  
      .add(new Symbol(">=").discard())
      .add(operand())
      .setAssembler(new SeqFloatGreaterOrEqualThanAssembler());    
  }    
  private static Parser seqFloatLessOrEqualThan() {
    return new Sequence()  
      .add(new Symbol("<=").discard())
      .add(operand())
      .setAssembler(new SeqFloatLessOrEqualThanAssembler());    
  }
  private static Parser seqFloatLessThan() {
    return new Sequence()  
      .add(new Symbol('<').discard())
      .add(operand())
      .setAssembler(new SeqFloatLessThanAssembler());    
  }      
  private static Parser elementComparisonExpressions() {
    return new Sequence()
      .add(new Literal("elements").discard())
      .add(new Alternation()
        .add(seqFloatEqual())
        .add(seqFloatNonEqual())
        .add(seqFloatGreaterThan())
        .add(seqFloatGreaterOrEqualThan())
        .add(seqFloatLessOrEqualThan())
        .add(seqFloatLessThan())
      );
  }
  private static Parser modulusLongExpression() {
    return new Sequence()
      .add(new Literal("Mod").discard())
      .add(new Symbol('(').discard())
      .add(operand())
      .add(new Symbol(',').discard())
      .add(operand())
      .add(new Symbol(')').discard())
        .setAssembler(new ModulusLongExpressionAssembler());
  }
  private static Parser powerLongExpression() {
    return new Sequence() 
      .add(new Literal("Power").discard())
      .add(new Symbol('(').discard())
      .add(operand())
      .add(new Symbol(',').discard())
      .add(operand())
      .add(new Symbol(')').discard())
        .setAssembler(new PowerLongExpressionAssembler());
  }  
  private static Parser divisionDoubleExpression(){
    return new Sequence() 
      .add(new Literal("Division").discard())
      .add(new Symbol('(').discard())
      .add(operand())
      .add(new Symbol(',').discard())
      .add(operand())
      .add(new Symbol(')').discard())
        .setAssembler(new DivisionDoubleExpressionAssembler());
  } 
  private static Parser maximumDoubleExpression(){
    return new Sequence() 
      .add(new Literal("Maximum").discard())
      .add(new Symbol('(').discard())
      .add(operand())
      .add(new Symbol(',').discard())
      .add(operand())
      .add(new Symbol(')').discard())
        .setAssembler(new MaximumDoubleExpressionAssembler());
  }    
  private static Parser minimumDoubleExpression(){
    return new Sequence() 
      .add(new Literal("Minimum").discard())
      .add(new Symbol('(').discard())
      .add(operand())
      .add(new Symbol(',').discard())
      .add(operand())
      .add(new Symbol(')').discard())
        .setAssembler(new MinimumDoubleExpressionAssembler());
  }   
  private static Parser multiplyDoubleExpression(){
    return new Sequence() 
      .add(new Literal("Multiply").discard())
      .add(new Symbol('(').discard())
      .add(operand())
      .add(new Symbol(',').discard())
      .add(operand())
      .add(new Symbol(')').discard())
        .setAssembler(new MultiplyDoubleExpressionAssembler());
  } 
  private static Parser numericFloatSquareExpression(){
    return new Sequence() 
      .add(operand())
      .add(new Symbol("**").discard())
      .add(new Num().discard())
        .setAssembler(new NumericFloatSquareExpressionAssembler());
  }
  private static Parser numericFloatZeroTrackExpression(){
    return new Sequence() 
      .add(new Num().discard())
      .add(new Symbol("==>").discard())
      .add(operand())
      .add(new Symbol('=').discard())
      .add(new Num().discard())
        .setAssembler(new NumericFloatZeroTrackAssembler());
  }  
  private static Parser pairwiseLinearBinaryFloat() {
    return new Sequence()
      .add(new Num().setAssembler(new NumberAssembler()))
      .add(new Symbol('*'))
      .add(operand())
      .add(new Symbol('+'))
      .add(new Num().setAssembler(new NumberAssembler()))
        .setAssembler(new PairwiseLinearBinaryFloatAssembler());
  }
  private static Parser function() {
    return new Sequence()
      .add(new Symbol('=').discard())
      .add(new Alternation()
        .add(modulusLongExpression())
        .add(powerLongExpression())
        .add(divisionDoubleExpression())
        .add(maximumDoubleExpression())
        .add(minimumDoubleExpression())
        .add(multiplyDoubleExpression())
        .add(numericFloatSquareExpression())
        .add(numericFloatZeroTrackExpression())
        .add(pairwiseLinearBinaryFloat())
      );
  }
  private static Parser numericFloatDivides() {
    return new Sequence() 
      .add(new Symbol('%').discard())
      .add(operand())
      .add(new Symbol("==").discard())
      .add(new Num().discard())
        .setAssembler(new NumericFloatDividesAssembler());  
  }
  /**
   * ATTENZIONE: a causa di un'idiosincrasia del parser nel riconoscere
   * i caratteri tra virgolette, questa produzione
   * e' stata implementata in maniera funzionante ma del tutto inelegante
   */
  private static Parser eltwiseOrderingExpressions() {
    return new Sequence()
      .add(new Literal("sorted").discard())
      .add(new Literal("by").discard())
      .add(new QuotedString()).setAssembler(new EltwiseOrderingAssembler());
  }  
  private static Parser noDuplicatesFloatExpresson() {
    return new Sequence()
      .add(new Literal("contains").discard())
      .add(new Literal("no").discard()) 
      .add(new Literal("duplicates").discard())
        .setAssembler(new NoDuplicatesFloatAssembler());
  }   
  private static Parser memberExpression() {
    return new Sequence()
      .add(new Literal("in").discard())
      .add(operand())
        .setAssembler(new MemberAssembler());
  }  
  private static Parser linearTernaryFloat() {
    return new Sequence()
      .add(operand())
      .add(new Symbol('+').discard())
      .add(new Num().setAssembler(new NumberAssembler()))
      .add(new Symbol('=').discard())
      .add(new Num().setAssembler(new NumberAssembler()) )
        .setAssembler(new LinearTernaryFloatAssembler());
  }  
  private static Parser linearBinaryFloat() {
    return new Sequence()
      .add(new Num().setAssembler(new NumberAssembler()))
      .add(operand())
      .add(new Symbol('+').discard())
      .add(new Num().setAssembler(new NumberAssembler()))
      .add(operand())
      .add(new Symbol('+').discard())
      .add(new Num().setAssembler(new NumberAssembler()))      
      .add(new Alternation()
        .add(linearTernaryFloat())
        .add(new Sequence()
          .add(new Symbol('=').discard())
          .add(new Num().setAssembler(new NumberAssembler()))
            .setAssembler(new LinearBinaryFloatAssembler())
        )
      );
  }
  private static Parser subSequenceFloat() {
    return new Sequence()
      .add(new Literal("is").discard())
      .add(new Literal("a").discard())
      .add(new Literal("subsequence").discard())
      .add(new Literal("of").discard())
      .add(operand())
        .setAssembler(new SubSequenceFloatAssembler());
  }    
  private static Parser subSetFloat() {
    return new Sequence()
      .add(new Literal("is").discard())
      .add(new Literal("a").discard())
      .add(new Literal("subset").discard())
      .add(new Literal("of").discard())
      .add(operand())
        .setAssembler(new SubSetFloatAssembler());
  }      
  private static Parser superSetFloat() {
    return new Sequence()
      .add(new Literal("is").discard())
      .add(new Literal("a").discard())
      .add(new Literal("superset").discard())
      .add(new Literal("of").discard())
      .add(operand())
        .setAssembler(new SuperSetFloatAssembler());
  }    
  private static Parser oneOf() {
    return new Sequence()
    .add(new Literal("one").discard())
    .add(new Literal("of").discard())
    .add(operand())
      .setAssembler(new OneOfAssembler());
  }
  private static Parser subSet() {
    return new Sequence()
      .add(new Literal("subset").discard())
      .add(new Literal("of").discard())
      .add(operand())
        .setAssembler(new SubSetFloatAssembler());
  }
  private static Parser storeValue() {
    return new Sequence()
      .add(new Literal("store").discard())
      .add(new Symbol('(').discard())
      .add(operand())
      .add(new Symbol(')').discard())
        .setAssembler(new StoreValueAssembler());
  }
  private static Parser removeValue() {
    return new Sequence()
      .add(new Literal("remove").discard())
      .add(new Symbol('(').discard())
      .add(operand())
      .add(new Symbol(')').discard())
        .setAssembler(new RemoveValueAssembler());
  }  
  
  private static Parser expression(){
	  if ( expr == null ){
		  expr = new Alternation();
		  
		  Sequence basicSequence = new Sequence();
		  basicSequence.add(basicExpression());
		  basicSequence.add(new Repetition(boolSequence()));
		  expr.add( basicSequence );
		  
		  
		  Sequence parSequence = new Sequence();
		  parSequence.add(parenthesis());
		  parSequence.add(new Repetition(boolSequence()));
		  expr.add( parSequence );
		  //expr.setAssembler(new ExpressionAssembler());
	  }
	  return expr;
  }
  
  private static Parser boolSequence(){
	  return new Alternation()
	  	 .add( andSequence() );
  }
  
  private static Parser andSequence() {
	  	Sequence andSeq = new Sequence();
	  	andSeq.add( new Literal("and").setAssembler( new LeftAndAssembler() ) );
		andSeq.add( expression() ); 
		andSeq.setAssembler( new AndAssembler() );
	  	return andSeq;
  }

private static Parser basicExpression(){
	  if( basicExpression == null ){
		  basicExpression = new Sequence();
		  basicExpression.add(operand());
		  basicExpression.add(new Alternation()
				  .add(equalsExpression())
				  .add(nonEqualsExpression())
				  .add(lessOrEqualThanExpression())
				  .add(lessThanExpression())
				  .add(greaterOrEqualThanExpression())
				  .add(greaterThanExpression())
				  .add(numericFloatDivides())
				  .add(elementComparisonExpressions())
				  .add(eltwiseOrderingExpressions())
				  .add(function())
				  .add(noDuplicatesFloatExpresson())
				  .add(memberExpression())
				  .add(subSequenceFloat())
				  .add(subSetFloat())
				  .add(superSetFloat())
				  .add(oneOf())
				  .add(subSet())
		  );
	  }
	  return basicExpression;
  }
  
	private static Parser implication(){
		if ( implication == null ){
			implication = new Sequence();
			implication.add( expression() );
			implication.add( new Symbol("==>").setAssembler( new ImplicationLeftSideAssembler() ) );
			implication.add( expression() );
			implication.setAssembler(new ImplicationAssembler());
		}
		return implication;
	}

  private static Parser invariantParser() {
    if (invariantParser == null){
      invariantParser = new Alternation()
      .add(linearBinaryFloat())
      .add(storeValue())
      .add(removeValue())
      .add(expression())
      .add(implication());
      /*
      .add(new Sequence()
        .add(operand())
        .add(new Alternation()
          .add(equalsExpression())
          .add(nonEqualsExpression())
          .add(lessOrEqualThanExpression())
          .add(lessThanExpression())
          .add(greaterOrEqualThanExpression())
          .add(greaterThanExpression())
          .add(numericFloatDivides())
          .add(elementComparisonExpressions())
          .add(eltwiseOrderingExpressions())
          .add(function())
          .add(noDuplicatesFloatExpresson())
          .add(memberExpression())
          .add(subSequenceFloat())
          .add(subSetFloat())
          .add(superSetFloat())
          .add(oneOf())
          .add(subSet())
        )
      );*/
    }
    return invariantParser;
   }
  
   private static Assembly createTokenAssembly(String expression,Object[] parameters,Object returnValue) {
     Tokenizer t = new Tokenizer(expression);
     
     //WordsOrIdentifierState wfls = new WordsOrIdentifierState();
     //WordsOrFieldInspectorState wfis = new WordsOrFieldInspectorState();
     
     //t.setCharacterState(0x0,0xff,wfis);
     /*
     t.setCharacterState('0','9',wfis);
     t.setCharacterState('a','z',wfis);
     t.setCharacterState('A','Z',wfis);
     t.setCharacterState(0xc0,0xff,wfis);
     */
     t.symbolState().add("==");
     t.symbolState().add("**");
     t.symbolState().add("==>");
     TokenAssembly a = new TokenAssembly(t);
     a.setTarget(new Target(parameters,returnValue));
     
     return a;
   }

   public static boolean evaluateExpression(String expression,Object[] parameters,Object returnValue) throws InvariantParseException {
	 //System.out.println("evaluateExpression: "+expression);  
	 currentExpression = expression; 
	 //System.out.println(expression);
	 
    Parser p = invariantParser();
    Assembly a = createTokenAssembly(expression,parameters,returnValue);      

    
    Assembly result = p.completeMatch(a);
    
    if(result==null)
      throw new InvariantParseException("Parsing error on expression "+expression);
    else {
      Target resultingTarget = (Target)result.getTarget();
      
      //Boolean expressionTruth = (Boolean)resultingTarget.pop();
      Boolean expressionTruth =  null;
      try{
      	expressionTruth = (Boolean)resultingTarget.pop();
      	if ( resultingTarget.isBlocked() ){
      	  //EvaluationRuntimeErrors.log( expression + "  BLOCKED!!!");
      	  
      	  return true;
      	}
      }catch(ArrayIndexOutOfBoundsException e) {
        	EvaluationRuntimeErrors.emptyStack();
        	return true;
      }catch(Exception e){
    	  EvaluationRuntimeErrors.evaluationError();
      	  return true;
      }
      
      
      return expressionTruth.booleanValue();
    }
  }
  public static void main(String argv[]) throws Exception {
    //Object[] parameters = {new Double(0.7480314960629921)};
    //String exp = "0.7480314960 one of { 0.5, 0.7480314960, 1.1811023622047 }";
    //String exp = "7480314960.629921 one of { 5.9, 7480314960.629921, 1811023622047245.3 }";
    //System.out.println(exp + " = " + evaluateExpression(exp,parameters,""));
  }  
}